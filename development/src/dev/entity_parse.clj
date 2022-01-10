(ns dev.entity-parse
  "Functions to parse OFBiz entities to data structures.
   This will be input for Calcite Schema."
  (:require [clojure.xml :as xml]
            [riveted.core :as vtd])
(:import (riveted.core Navigator)))

(set! *warn-on-reflection* true)

(defn clean-nils [m]
  "TODO: Migrate to https://ask.clojure.org/index.php/8387/how-to-avoid-nil-values-in-maps"
  (into {} (filter (comp some? val) m)))

(defn field-type-def->map
  "Map fieldtypemodel to clojure."
  [^Navigator ftype-dev-nav]
  {:type (vtd/attr ftype-dev-nav :type)
   :sql-type (vtd/attr ftype-dev-nav :sql-type)
   :java-type (vtd/attr ftype-dev-nav :java-type)})

(defn load-field-type-defs
  "Load field types for entities from specific DB file (framework/entity/fieldtype/fieldtypepostgres.xml)."
  [^String xml]
  (let [nav (vtd/navigator xml)
        ftypes (vtd/search nav "/fieldtypemodel/field-type-def")]
    (map field-type-def->map ftypes)))


(defn entity-field->map
  "Map entity field to clojure."
  [^Navigator e]
  (clean-nils
   {:name (vtd/attr e :name)
    :type (vtd/attr e :type)
    :description (-> e (vtd/first-child :description) vtd/text)}))

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
  [^Navigator e]
  (let [fields (vtd/children e :field)
        prim-key (vtd/children e :prim-key)
        relation (vtd/children e :relation)]
    (clean-nils
     {:entity-name (vtd/attr e :entity-name)
      :package-name (vtd/attr e :package-name)
      :title (vtd/attr e :title)
      :fields (into [] (map entity-field->map fields))
      :prim-keys (into [] (map entity-prim-key->map prim-key))
      :relation (into [] (map entity-relation->map relation))})))

(defn load-entities
  "Load entity from entitymodel files. e.g. framework/entity/entitydef/entitymodel.xml"
  [^String xml]
  (let [nav (vtd/navigator xml)
        entities (vtd/search nav "/entitymodel/entity")]
    (map entity->map entities)))


(comment

  (tap> (xml/parse "data/ofbiz-entitydef/framework/entity/entitydef/entitymodel.xml"))


  (load-field-type-defs (slurp "data/ofbiz-fieldtypes/framework/entity/fieldtype/fieldtypepostgres.xml"))

  (load-entities (slurp "data/ofbiz-entitydef/framework/entity/entitydef/entitymodel.xml"))

  (let [nav (vtd/navigator (slurp "data/ofbiz-entitydef/framework/entity/entitydef/entitymodel.xml"))
        entities (vtd/search nav "/entitymodel/entity")
        e-fields (map (fn [e] {:name (vtd/attr e :entity-name)
                               :fields (vtd/search e "/entitymodel/entity/field")}) entities)]
    e-fields)
  )