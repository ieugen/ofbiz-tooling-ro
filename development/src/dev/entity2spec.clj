(ns dev.entity2spec
  "Convert OFBiz entities to clojure specs."
  (:require [camel-snake-kebab.core :as csk]
            [clojure.string :as str]
            [clojure.java.io :as io]
            [clojure.pprint :as pp]
            [hyperfiddle.rcf :refer [tests ! %]]
            [riveted.core :as vtd])
  (:import (riveted.core Navigator)))

(defn clean-nils
  "TODO: Migrate to https://ask.clojure.org/index.php/8387/how-to-avoid-nil-values-in-maps ?
   Or better implement fetch all attributes from xml element."
  [m]
  (into {} (filter (comp some? val) m)))

(def default-ofbiz-entitydefs
  ["data/ofbiz-entitydef/framework/common/entitydef/entitymodel.xml"
   "data/ofbiz-entitydef/framework/entity/entitydef/entitymodel.xml"
   "data/ofbiz-entitydef/framework/entityext/entitydef/entitymodel.xml"
   "data/ofbiz-entitydef/framework/catalina/entitydef/entitymodel.xml"
   "data/ofbiz-entitydef/framework/security/entitydef/entitymodel.xml"
   "data/ofbiz-entitydef/framework/service/entitydef/entitymodel.xml"
   "data/ofbiz-entitydef/framework/webapp/entitydef/entitymodel.xml"])

(defn entity-field->map
  "Map entity field to clojure."
  [^Navigator e]
  (let [name (vtd/attr e :name)
        type (vtd/attr e :type)
        spec-attr-type (keyword (str "udm.field-types/" type))]
    (clean-nils
     {:name name
      :sql-name (csk/->snake_case name)
      :clj-name (csk/->kebab-case name)
      :type type
      :spec-attr-type spec-attr-type
      :description (-> e (vtd/first-child :description) vtd/text)})))

(defn entity-prim-key->map
  "Map entity prim-key"
  [^Navigator e]
  {:field (vtd/attr e :field)})

(defn entity-relation-keymap->map
  [^Navigator e]
  (clean-nils
   {:field-name (vtd/attr e :field-name)
    :rel-field-name (vtd/attr e :rel-field-name)}))

(defn entity-relation->map
  "Map entity relation"
  [^Navigator e]
  (let [key-maps (vtd/children e :key-map)]
    (clean-nils
     {:type (vtd/attr e :type)
      :fk-name (vtd/attr e :fk-name)
      :rel-entity-name (vtd/attr e :rel-entity-name)
      :keymap (into [] (map entity-relation-keymap->map key-maps))})))

(defn entity->map
  "Map entity fields to clojure."
  ([^Navigator e]
   (let [name (vtd/attr e :entity-name)
         fields (vtd/children e :field)
         prim-key (vtd/children e :prim-key)
         relation (vtd/children e :relation)]
     (clean-nils
      {:entity-name name
       :table-name (csk/->snake_case name)
       :clojure-name (csk/->kebab-case name)
       :package-name (vtd/attr e :package-name)
       :title (vtd/attr e :title)
       :fields (into [] (map entity-field->map fields))
       :prim-keys (into [] (map entity-prim-key->map prim-key))
       :relation (into [] (map entity-relation->map relation))}))))

(defn load-entities
  "Load entity from entitymodel files. e.g. framework/entity/entitydef/entitymodel.xml"
  ([^String xml]
   (let [nav (vtd/navigator xml)
         entities (vtd/search nav "/entitymodel/entity")]
     (map entity->map entities))))

(defn load-many-entities
  "Load multiple entity files from a list of paths"
  [entity-paths]
  (mapcat #(load-entities (slurp %)) entity-paths))

(defn field-spec-name 
  [table-name field-name]
  (keyword (str table-name "/" field-name)))

(defn field->spec-map
  [table-name field]
  (let [{:keys [clj-name spec-attr-type]} field
        spec-attr-name (field-spec-name table-name clj-name)]
    {:s/def-str (str "(s/def " spec-attr-name " " spec-attr-type ")")
     :spec-attr-name spec-attr-name
     :spec-attr-type spec-attr-type}))

(defn spec-entity-name 
  "Generate name for spec entity."
  [prefix clojure-name]
  (keyword (str prefix "/" clojure-name)))

(defn entity-spec-str
  [name fields]
  (let [fields-str (with-out-str (pp/pprint fields))]
    (str "(s/def " name
         " (s/keys :opt " fields-str "))\n")))

(defn entity->spec-str
  [entity]
  (let [{:keys [clojure-name fields]} entity
        field-defs (map (partial field->spec-map clojure-name) fields)
        field-specs (into [] (map :spec-attr-name field-defs))
        entity-spec-name (spec-entity-name "udm.party-entities" clojure-name) 
        entity-def-str (entity-spec-str entity-spec-name field-specs)
        field-def-str (str/join "\n" (map :s/def-str field-defs))]
    (str field-def-str
         "\n"
         entity-def-str
         "\n\n")))

(tests
 "spec code from field"

 (field->spec-map "my-table" {:name "addendumId",
                              :sql-name "addendum_id",
                              :clj-name "addendum-id",
                              :type "id",
                              :spec-attr-type :udm.field-types/id})
 := {:s/def-str "(s/def :my-table/addendum-id :udm.field-types/id)"
     :spec-attr-name :my-table/addendum-id
     :spec-attr-type :udm.field-types/id}
 
 "spec code from entity"

 (spec-entity-name "aaa.bbb" "my-table") := :aaa.bbb/my-table

 (spec-entity-name "udm.party-entities" "my-table") := :udm.party-entities/my-table

 (entity-spec-str :my-table [:my-table/addendum-id :my-table/other-id]) 
 := "(s/def :my-table (s/keys :opt [:my-table/addendum-id :my-table/other-id]\n))\n"
 
 0)


(defn generate-entities
  [entities] 
  (with-open [w (io/writer  "development/src-generated/udm/party_entities.clj")]
    (.write w (str "(ns udm.party-entities
                      (:require [clojure.spec.alpha :as s]))\n\n\n"))
    (doseq [e entities]
      (.write w (entity->spec-str e)))))

(comment

  (load-entities (slurp "data/ofbiz-entitydef/framework/entity/entitydef/entitymodel.xml"))

  (def party-entities (load-entities (slurp "data/ofbiz-entitydef/applications/datamodel/entitydef/party-entitymodel.xml")))

  (map :entity-name party-entities)
  
  (generate-entities party-entities)

  (spit "party-entitymodel.edn" (with-out-str (pp/pprint party-entities)))

  (pp/pprint (first party-entities))

  (def entities (load-many-entities default-ofbiz-entitydefs))

  (filter #(str/starts-with? (str/lower-case (:entity-name %)) "party") entities)

  (map :entity-name entities)


  0
  )