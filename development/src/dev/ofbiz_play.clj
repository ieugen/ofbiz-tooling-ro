(ns dev.ofbiz-play
  "OFBiz related code - exploratory."
  (:require [next.jdbc :as jdbc]
            [next.jdbc.result-set :as rs]
            [clojure.data.csv :as csv]
            [clojure.java.io :as io]))

(comment

  ;; open jdbc connection to OFBiz derby embedded database loaded with ./gradle cleanAll loadAll
  (let [db {:jdbcUrl "jdbc:derby:/home/ieugen/proiecte/ofbiz/ofbiz-framework/runtime/data/derby/ofbiz"}
        ds (jdbc/get-datasource db)]
    (with-open [connection (jdbc/get-connection ds)]
      ;; read jdbc metadata for all tables
      (let [metadata (.getMetaData connection)
            rs (.getTables metadata nil nil nil (into-array ["TABLE" "VIEW"]))
            ;; get the table names from the metadata response 
            table-names (map :SYSTABLES/TABLE_NAME
                             (filter #(= "OFBIZ" (:SYSSCHEMAS/TABLE_SCHEM %))
                                     (rs/datafiable-result-set rs ds)))
            ;; build the data we need by counting all fields from each tables
            results (map (fn [t]
                           (let [query (str "select count(*) as COUNT from OFBIZ." t)
                                 data (:COUNT (first (jdbc/execute! ds [query])))]
                             {:table-name t
                              :sql-query query
                              :data data})) table-names)
            ;; get only values from map as a vector
            report-data (map (juxt :table-name :data :sql-query) results)
            report-header ["table_name" "data" "sql-query"]]
        (with-open [writer (io/writer "ofbiz-demo-data-count-by-tables.csv")]
          (csv/write-csv writer
                         (concat [report-header] report-data))))))


  0)