(ns dev.schema
  (:require [com.rpl.proxy-plus :refer [proxy+]]
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

(defn to-nullable-rel-data-type
  "Helper to create rel-data-type"
  ^RelDataType [^JavaTypeFactory type-factory ^SqlTypeName sql-type-name]
  (let [sql-type (.createSqlType type-factory sql-type-name)]
    (.createTypeWithNullability type-factory sql-type true)))

(defn row->rel-type
  ^Map$Entry [^JavaTypeFactory type-factory {:keys [name type] :as row}]
  (clojure.lang.MapEntry/create name (to-nullable-rel-data-type type-factory type)))

(defn row-type
  "Build a calcite row type (reltype)from a clojure structure"
  ^RelDataType [^JavaTypeFactory type-factory rowdef]
  (.createStructType type-factory ^List (map (partial row->rel-type type-factory) rowdef)))


; Implement ofbiz-entity-xml parsing


(defn entity->Table
  "Table based on a clojure data structure.
   It implements the {@link ScannableTable} interface, so Calcite gets
   data by calling the {@link #scan(DataContext)} method."
  ^Table [{:keys [entity-name rowdef data] :as table-def} field-type-defs xml-nav]
  (let [row-type-memo (memoize row-type)]
    (proxy+
     []
     AbstractTable
     (getRowType
      [this ^RelDataTypeFactory type-factory]
      (row-type-memo type-factory rowdef))

     ScannableTable
     (scan
      [this ^DataContext root]
      (let [type-factory (.getTypeFactory root)
            ^AtomicBoolean cancel-flag (.get DataContext$Variable/CANCEL_FLAG root)]
        (proxy+
         []
         AbstractEnumerable
         (enumerator
          [this]
          ;; return VTD's and convert row and column on demand 
          ;; avoid doing conversion if we don't use the row because it is filtered out ?!
          (let [i (atom -1)
                len (- (count data) 1)]
            (reify org.apache.calcite.linq4j.Enumerator
              (current [this] (into-array Object (get data @i)))
              (moveNext
                [this]
                (let [res (< @i len)]
                  (if (.get cancel-flag)
                    false
                    (do
                      (when res (swap! i inc))
                      res))))
              (reset [this] (reset! i -1))
              (close [this] nil))))))))))

(defn create-tables
  ^Map [entities field-type-defs xml-file]
  (let [xml-nav (vtd/navigator (slurp xml-file))
        e->table (fn e->table [e]
                   (clojure.lang.MapEntry/create (:entity-name e)
                                                 (entity->Table e field-type-defs xml-nav)))]
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

  (let [db {:jdbcUrl "jdbc:calcite:model=resources/model.json"
            :user "admin"
            :password "admin"}
        ds (jdbc/get-datasource db)]
    (jdbc/execute! ds ["select * from emps where age is null or age >= 40"]))

  (tap> (ep/load-many-entities default-ofbiz-entitydefs))

  ;; display table metadata
  (let [db {:jdbcUrl "jdbc:calcite:model=resources/model.json"
            :user "admin"
            :password "admin"}
        ds (jdbc/get-datasource db)]
    (with-open [connection (jdbc/get-connection ds)]
      (let [metadata (.getMetaData connection)
            rs (.getTables metadata nil nil nil (into-array ["TABLE" "VIEW"]))]
        (rs/datafiable-result-set rs ds)))))
