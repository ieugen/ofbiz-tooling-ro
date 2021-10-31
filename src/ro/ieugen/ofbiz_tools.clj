(ns ro.ieugen.ofbiz-tools
  (:require [clojure.data.csv :as csv]
            [clojure.data.xml :as xml]
            [clojure.java.io :as io]))

;; 
;; Convert data in different formats to OFBiz xml
;; 

(defn read-csv
  "Read csv file as edn vector.
   Pass :drop-rows to drop some rows."
  ([file]
   (read-csv file {:drop-rows 0}))
  ([file opts]
   (let [{:keys [drop-rows] :or {drop-rows 1}} opts]
     (with-open [reader (io/reader file)]
       (doall
        (let [data (csv/read-csv reader)]
          (when drop-rows
            (drop drop-rows data))))))))

(defn spit-entities
  "Write OFBiz entities as xml."
  [file sexp-data]
  (let [xml (xml/sexp-as-element
             [:entity-engine-xml sexp-data])]
    (with-open [out-file (java.io.OutputStreamWriter.
                          (java.io.FileOutputStream. file) "UTF-8")]
      (xml/emit xml out-file))))

(defn ofbiz-zona-id [id] (str "RO-Z" id))

(defn zone->ofbiz-geo
  "Convert one siruta-zones.csv to ofbiz geo entity."
  [zone]
  (let [[zone siruta name] zone
        id (ofbiz-zona-id zone)
        abbrev (str "Zona " zone)]
    [:Geo {:geoId id
           :abbreviation abbrev
           :geoName name
           :geoCode zone
           :geoSecCode siruta
           :geoTypeId "REGION"}]))

(defn ofbiz-judet-id [id] (str "RO-" id))

(defn jud->ofbiz-geo
  [jud]
  (let [[jud name fsj mnemonic zone] jud
        id (ofbiz-judet-id jud)]
    [:Geo {:geoId id
           :abbreviation mnemonic
           :geoName name
           :geoCode jud
           :geoTypeId "COUNTY"}]))

(defn jud->ofbiz-geo-assoc
  [jud]
  (let [[jud name fsj mnemonic zone] jud
        id (ofbiz-zona-id jud)]
    [:GeoAssoc {:geoAssocTypeId "REGIONS"
                :geoId id
                :geoIdTo "ROU"}]))

(defn siruta-zone->ofbiz-geo-data!
  [zones-file geo-zones-file]
  (let [data (read-csv zones-file {:drop-rows 1})
        geo-zones (map zone->ofbiz-geo data)
        geo-zones-assoc (map jud->ofbiz-geo-assoc data)]
    (spit-entities geo-zones-file (conj geo-zones-assoc geo-zones))))

(defn jud-zona->ofbiz-geo-assoc
  [jud]
  (let [[jud name fsj mnemonic zona] jud
        id (ofbiz-judet-id jud)
        zona-id (ofbiz-zona-id zona)]
    [:GeoAssoc {:geoAssocTypeId "REGIONS"
                :geoId id
                :geoIdTo zona-id}]))

(defn siruta-jud->ofbiz-geo-data!
  [jud-files geo-jud-file]
  (let [data (read-csv jud-files {:drop-rows 1})
        geo-judete (map jud->ofbiz-geo data)
        geo-judete-zone (map jud-zona->ofbiz-geo-assoc data)]
    (spit-entities geo-jud-file (conj geo-judete-zone geo-judete))))


;; (defn siruta-)

(defn counties->geo-entities!
  []
  (let [counties (read-csv "data/judete-Romania.csv" {:drop-rows 1})]
    counties))



(comment

  (read-csv "data/judete-Romania.csv")

  (read-csv "data/dbfbl.mdrap.ro/machete_cu_cod_siruta_si_uat_pe_judete.csv" {:drop-rows 5})

  (counties->geo-entities!)
  
  (siruta-zone->ofbiz-geo-data! "data/data.gov.ro/siruta-zone.csv" "data/ofbiz/GeoData_RO_zones.xml")
  (siruta-jud->ofbiz-geo-data! "data/data.gov.ro/siruta-jud.csv" "data/ofbiz/GeoData_RO_counties.xml")

  )