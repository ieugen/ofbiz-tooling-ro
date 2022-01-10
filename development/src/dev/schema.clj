(ns dev.schema
  (:require [com.rpl.proxy-plus :refer [proxy+]]
            [next.jdbc :as jdbc]
            [next.jdbc.result-set :as rs]
            [dev.entity-parse :as ep])
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

;; (defn entity-fields->calcite-row
;;   [fields field-type-defs]
;;   ())

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
  ;; (println "row-type" rowdef)
  (.createStructType type-factory ^List (map (partial row->rel-type type-factory) rowdef)))

(defn entity->Table
  "Table based on a clojure data structure.
   It implements the {@link ScannableTable} interface, so Calcite gets
   data by calling the {@link #scan(DataContext)} method."
  ^Table [{:keys [data rowdef name] :as table-def}]
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
  ^Map [entities]
  (map entity->Table entities))

(defn ofbiz-schema
  "Create a Calcite Schema and return it for use."
  ^Schema [^SchemaPlus parent-schema ^String name ^Map operand]
  (let [ftd (ep/load-field-type-defs (slurp "data/ofbiz-fieldtypes/framework/entity/fieldtype/fieldtypepostgres.xml"))
        entities (ep/load-entities (slurp "data/ofbiz-entitydef/framework/entity/entitydef/entitymodel.xml"))
        tables (create-tables entities)]
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

  ;; display table metadata
  (let [db {:jdbcUrl "jdbc:calcite:model=resources/model.json"
            :user "admin"
            :password "admin"}
        ds (jdbc/get-datasource db)]
    (with-open [connection (jdbc/get-connection ds)]
      (let [metadata (.getMetaData connection)
            rs (.getTables metadata nil nil nil (into-array ["TABLE" "VIEW"]))]
        (rs/datafiable-result-set rs ds))))
  )
