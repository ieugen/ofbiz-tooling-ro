(ns dev.schema
  {:clj-kondo/config '{:lint-as {com.rpl.proxy-plus/proxy+ clojure.core/proxy}
                       :linters {:unused-binding {:exclude [this]}}}}
  (:require [clojure.string :as str]
            [com.rpl.proxy-plus :refer [proxy+]]
            [dev.entity-parse :as ep]
            [next.jdbc :as jdbc]
            [next.jdbc.result-set :as rs]
            [riveted.core :as vtd])
  (:import (java.util List
                      Map
                      Map$Entry)
           (java.util.concurrent.atomic AtomicBoolean)
           (org.apache.calcite DataContext
                               DataContext$Variable)
           (org.apache.calcite.adapter.java JavaTypeFactory)
           (org.apache.calcite.linq4j AbstractEnumerable)
           (org.apache.calcite.rel.type RelDataType
                                        RelDataTypeFactory)
           (org.apache.calcite.schema Schema
                                      SchemaPlus
                                      ScannableTable
                                      Table)
           (org.apache.calcite.schema.impl AbstractSchema
                                           AbstractTable)
           (org.apache.calcite.sql.type SqlTypeName)))

(set! *warn-on-reflection* true)

(def default-ofbiz-entitydefs
  ["data/ofbiz-entitydef/framework/common/entitydef/entitymodel.xml"
   "data/ofbiz-entitydef/framework/entity/entitydef/entitymodel.xml"
   "data/ofbiz-entitydef/framework/entityext/entitydef/entitymodel.xml"
   "data/ofbiz-entitydef/framework/catalina/entitydef/entitymodel.xml"
   "data/ofbiz-entitydef/framework/security/entitydef/entitymodel.xml"
   "data/ofbiz-entitydef/framework/service/entitydef/entitymodel.xml"
   "data/ofbiz-entitydef/framework/webapp/entitydef/entitymodel.xml"])

; Implement ofbiz-entity-xml parsing

(def sql-types
  "OFBIz sql types (from PostgreSQL field type model) -> Calcite sql type names"
  {:bytea SqlTypeName/VARBINARY
   :timestamptz SqlTypeName/TIMESTAMP_WITH_LOCAL_TIME_ZONE
   :date SqlTypeName/DATE
   :time SqlTypeName/TIME
   :numeric SqlTypeName/DECIMAL
   :float8 SqlTypeName/DOUBLE
   :integer SqlTypeName/INTEGER
   :varchar SqlTypeName/VARCHAR
   :char SqlTypeName/CHAR
   :text SqlTypeName/VARCHAR})

(defn parse-sql-type
  "Regex parse string definitions of SQL types like VARCHAR(20,3) extracting
  :type :precision and :scale "
  [^String sql-type]
  (let [res (re-matches #"(\w+)(\((\d+)(,(\d+))?\))?" sql-type)
        [_ type _ precision _ scale] res]
    (try
      (cond-> {:type (keyword (str/lower-case type))}
        (some? precision) (assoc :precision (Integer/parseInt precision))
        (some? scale) (assoc :scale (Integer/parseInt scale)))
      (catch NumberFormatException e
        (throw (IllegalArgumentException.
                (str "Exception parsing " sql-type " " res)
                e))))))

(defn sql-type->calcite-sql-name
  "Convert OFBiz sql-type from field type model to Calcite org.apache.calcite.sql.type.SqlTypeName"
  [^RelDataTypeFactory type-factory ^String sql-type]
  (let [type-info (parse-sql-type sql-type)
        {:keys [type precision scale]} type-info
        sql-type-name (get sql-types type)]
    (if (and precision scale)
      (.createSqlType type-factory sql-type-name precision scale)
      (if precision
        (.createSqlType type-factory sql-type-name precision)
        (.createSqlType type-factory sql-type-name)))))

(defn to-nullable-rel-data-type
  "Helper to create rel-data-type"
  ^RelDataType [^JavaTypeFactory type-factory ^SqlTypeName sql-type-name]
  (let [sql-type (.createSqlType type-factory sql-type-name)]
    (.createTypeWithNullability type-factory sql-type true)))

(defn row->rel-type
  ^Map$Entry [^JavaTypeFactory type-factory [col-name col-type]]

  (clojure.lang.MapEntry/create col-name (sql-type->calcite-sql-name type-factory col-type)))

(defn row-type
  "Build a calcite row type (reltype)from a clojure structure"
  ^RelDataType [^JavaTypeFactory type-factory rowdef]
  (.createStructType type-factory ^List (map (partial row->rel-type type-factory) rowdef)))

(defn entity->Table
  "Table based on a clojure data structure.
   It implements the {@link ScannableTable} interface, so Calcite gets
   data by calling the {@link #scan(DataContext)} method."
  ^Table [table-def field-types-idx xml-nav]
  (let [{:keys [fields entity-name]} table-def
        n+t (ep/fields->name+sql-type-vec field-types-idx fields)
        ftypes (ep/fields->name+java-type-vec field-types-idx fields)]
    (proxy+
     []
     AbstractTable
     (getRowType
      [this ^RelDataTypeFactory type-factory]
      (row-type type-factory n+t))
     ScannableTable
     (scan
      [this ^DataContext root]
      (let [^AtomicBoolean cancel-flag (.get DataContext$Variable/CANCEL_FLAG root)]
        (proxy+
         []
         AbstractEnumerable
         (enumerator
          [this]
          ;; return VTD's and convert row and column on demand
          ;; avoid doing conversion if we don't use the row because it is filtered out ?!
          (let [i (atom -1)
                elems (into [] (vtd/select xml-nav entity-name))
                len (- (count elems) 1)]
            (reify org.apache.calcite.linq4j.Enumerator
              (current [_]
                (let [e (get elems @i)]
                  (into-array Object (ep/ofbiz-xml-row->entity2 ftypes e))))
              (moveNext
                [_]
                (let [res (< @i len)]
                  (if (.get cancel-flag)
                    false
                    (do
                      (when res (swap! i inc))
                      res))))
              (reset [_] (reset! i -1))
              (close [_] nil))))))))))

(defn create-tables
  ^Map [entities field-type-defs xml-file]
  (let [xml-nav (vtd/navigator (slurp xml-file))
        field-types-idx (ep/field-type-def-index field-type-defs)
        e->table (fn e->table [e]
                   (clojure.lang.MapEntry/create (:table-name e)
                                                 (entity->Table e field-types-idx xml-nav)))]
    (into {} (map e->table entities))))

(defn ofbiz-schema
  "Create a Calcite Schema and return it for use."
  ^Schema [^SchemaPlus parent-schema ^String name ^Map operand]
  (let [ftd-str (slurp "data/ofbiz-fieldtypes/framework/entity/fieldtype/fieldtypepostgres.xml")
        ftd (ep/load-field-type-defs ftd-str)
        entities (ep/load-many-entities default-ofbiz-entitydefs)
        xml-file "../ofbiz-framework/framework/common/data/UnitData.xml"
        tables (create-tables entities ftd xml-file)]
    (proxy+
     []
     AbstractSchema
     (getTableMap [this] tables))))

(comment

  (require '[clojure.tools.trace :as trace])
  (trace/trace-ns 'calcite-clj.simple)

  (let [types ["TEXT"
               "BYTEA"
               "NUMERIC(18,2)"
               "TIMESTAMPTZ"
               "FLOAT8"
               "NUMERIC(20,0)"
               "VARCHAR(20)"
               "VARCHAR(100)"]
        type-factory (org.apache.calcite.sql.type.SqlTypeFactoryImpl. org.apache.calcite.rel.type.RelDataTypeSystem/DEFAULT)]
    (map parse-sql-type types)
    (map (partial sql-type->calcite-sql-name type-factory) types))

  (let [db {:jdbcUrl "jdbc:calcite:model=resources/model.json;caseSensitive=false"
            :user "admin"
            :password "admin"}
        ds (jdbc/get-datasource db)]
    (jdbc/execute! ds ["select * from uom where uom_type_id='DATA_MEASURE' "])
    (jdbc/execute! ds ["select * from uom_conversion"]))

  (tap> (ep/load-many-entities default-ofbiz-entitydefs))

  ;; display table metadata
  (let [db {:jdbcUrl "jdbc:calcite:model=resources/model.json;caseSensitive=false"
            :user "admin"
            :password "admin"}
        ds (jdbc/get-datasource db)]
    (with-open [connection (jdbc/get-connection ds)]
      (let [metadata (.getMetaData connection)
            rs (.getTables metadata nil nil nil (into-array ["TABLE" "VIEW"]))]
        (tap> (map :TABLE_NAME (rs/datafiable-result-set rs ds))))))

  (let [db {:jdbcUrl "jdbc:calcite:model=resources/model.json;caseSensitive=false"
            :user "admin"
            :password "admin"}
        ds (jdbc/get-datasource db)]
    (with-open [connection (jdbc/get-connection ds)]
      (let [metadata (.getMetaData connection)
            rs (.getColumns metadata nil nil "uom" nil)]
        (tap> (rs/datafiable-result-set rs ds))))))
