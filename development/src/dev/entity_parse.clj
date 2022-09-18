(ns dev.entity-parse
  "Functions to parse OFBiz entities to data structures.
   This will be input for Calcite Schema."
  (:require [camel-snake-kebab.core :as csk]
            [riveted.core :as vtd])
  (:import (riveted.core Navigator)))

(defn clean-nils
  "TODO: Migrate to https://ask.clojure.org/index.php/8387/how-to-avoid-nil-values-in-maps ?
   Or better implement fetch all attributes from xml element."
  [m]
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
  (let [name (vtd/attr e :name)]
    (clean-nils
     {:name name
      :sql-name (csk/->snake_case name)
      :type (vtd/attr e :type)
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

(defn load-xml-data
  "Load OFBiz XML data https://cwiki.apache.org/confluence/display/OFBIZ/Handling+of+External+data"
  [^String xml]
  (let [nav (vtd/navigator xml)]
    nav))

(defmulti xml-attr->clj (fn [java-type _] java-type))

(defmethod xml-attr->clj "String" [_ value] value)
(defmethod xml-attr->clj "Integer" [_ value] (Integer/parseInt value))
(defmethod xml-attr->clj "Long" [_ value] (Long/parseLong value))
(defmethod xml-attr->clj "Double" [_ value] (Double/parseDouble value))
(defmethod xml-attr->clj "java.math.BigDecimal" [_ value] (bigdec value))

(defmethod xml-attr->clj :default [java-type _]
  (throw (UnsupportedOperationException. (str "xml-attr->clj not implemented for " java-type))))

;; (defmethod xml-attr->clj "java.sql.Time" [java-type value] (xx value))
;; (defmethod xml-attr->clj "java.sql.Date" [java-type value] (xx value))
;; (defmethod xml-attr->clj "java.sql.Timestamp" [java-type value] (xx value))
;; (defmethod xml-attr->clj "Object" [java-type value] (xx value))
;; (defmethod xml-attr->clj "byte[]" [java-type value] (xx value))
;; (defmethod xml-attr->clj "java.sql.Blob" [java-type value] (xx value))

(defn field-type-def-index
  "Create an index (map) of field type definitions."
  ;; {:email {:type "email", :java-type "String"}
  ;;  :date {:type "date", :java-type "java.sql.Date"} }
  [field-type-def]
  (apply merge (map (fn [e] {(keyword (:type e)) e}) field-type-def)))

(defn field-type->name+java-type
  "Return the :java-type of a field, given field type definitions.
   Returns a vector of [ name java-type ].
   Use with (partial field-type->java-type field-type-index)"
  [field-type-def-idx field]
  (let [type (keyword (:type field))
        name (:name field)]
    [name (:java-type (get field-type-def-idx type))]))

(defn fields->name+java-type-vec
  "Build java type array from field types.
   Maintain field type order."
  [field-type-def-idx fields]
  (into [] (map (partial field-type->name+java-type field-type-def-idx) fields)))

(defn field-type->name+sql-type
  "Return the :sql-type of a field, given field type definitions.
   Returns a vector of [ sql-name sql-type ].
   Use with (partial field-type->sql-type field-type-index)"
  [field-type-def-idx field]
  (let [type (keyword (:type field))
        name (:sql-name field)]
    [name (:sql-type (get field-type-def-idx type))]))

(defn fields->name+sql-type-vec
  "Build java type array from field types.
   Maintain field type order."
  [field-type-def-idx fields]
  (into [] (map (partial field-type->name+sql-type field-type-def-idx) fields)))

(defn row-attr->clj
  "Extract an attribute from row VTD and convert to clojure type."
  [row-vtd [name java-type]]
  (let [val (vtd/attr row-vtd name)]
    (if (some? val)
      (xml-attr->clj java-type val)
      val)))

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

(defn ofbiz-xml-row->entity
  "Convert entity row from xml file to array of columns.
   We need the vtd for the entity, the entity fields in the right order.

   Entity as xml row looks like this:

   <Uom abbreviation=\"b\" description=\"Bit of Data\"
        uomId=\"DATA_b\" uomTypeId=\"DATA_MEASURE\"/>

   * tag is entity type
   * attributes are the entity fields. All are strings ?!."
  [entity-def fdefs row-vtd]
  (let [fields (:fields entity-def)
        ftype-def-idx (field-type-def-index fdefs)
        ftypes (fields->name+java-type-vec ftype-def-idx fields)]
    ;; extract attrs from row and convert
    (map (partial row-attr->clj row-vtd) ftypes)))

(defn ofbiz-xml-row->entity2
  "Convert entity row from xml file to collection of columns.
   We need the vtd for the entity, the entity fields in the right order.

   Entity as xml row looks like this:

   <Uom abbreviation=\"b\" description=\"Bit of Data\"
        uomId=\"DATA_b\" uomTypeId=\"DATA_MEASURE\"/>

   * XML tag is entity type
   * XML tag attributes are the entity fields. All are strings ?!."
  [field-types row-vtd]
  (map (partial row-attr->clj row-vtd) field-types))


(comment

  (xml-attr->clj "Integer" "3")

  (tap> (xml/parse "data/ofbiz-entitydef/framework/entity/entitydef/entitymodel.xml"))

  (let [fdefs (load-field-type-defs (slurp "data/ofbiz-fieldtypes/framework/entity/fieldtype/fieldtypepostgres.xml"))
        fdefs-idx (field-type-def-index fdefs)
        fields [{:name "tenantId", :type "integer"}
                {:name "entityGroupName", :type "name"}
                {:name "jdbcUri", :type "long-varchar"}
                {:name "jdbcUsername", :type "long-varchar"}
                {:name "jdbcPassword", :type "long-varchar"}]]
    (field-type->name+java-type fdefs-idx {:name "tenantId", :type "id"})
    (fields->name+java-type-vec fdefs-idx fields))

  (load-entities (slurp "data/ofbiz-entitydef/framework/entity/entitydef/entitymodel.xml"))

  (let [nav (vtd/navigator (slurp "data/ofbiz-entitydef/framework/entity/entitydef/entitymodel.xml"))
        entities (vtd/search nav "/entitymodel/entity")
        e-fields (map (fn [e] {:name (vtd/attr e :entity-name)
                               :fields (vtd/search e "/entitymodel/entity/field")}) entities)]
    e-fields)

  (let [nav (vtd/navigator (slurp "../ofbiz-framework/framework/common/data/UnitData.xml"))
        default-ofbiz-entitydefs
        ["data/ofbiz-entitydef/framework/common/entitydef/entitymodel.xml"
         "data/ofbiz-entitydef/framework/entity/entitydef/entitymodel.xml"
         "data/ofbiz-entitydef/framework/entityext/entitydef/entitymodel.xml"
         "data/ofbiz-entitydef/framework/catalina/entitydef/entitymodel.xml"
         "data/ofbiz-entitydef/framework/security/entitydef/entitymodel.xml"
         "data/ofbiz-entitydef/framework/service/entitydef/entitymodel.xml"
         "data/ofbiz-entitydef/framework/webapp/entitydef/entitymodel.xml"]
        entities (load-many-entities default-ofbiz-entitydefs)
        fdefs (load-field-type-defs (slurp "data/ofbiz-fieldtypes/framework/entity/fieldtype/fieldtypepostgres.xml"))
        uom-def (first (filter #(= (:entity-name %) "Uom") entities))
        uoms (vtd/select nav "Uom")
        u1 (first uoms)
        res (ofbiz-xml-row->entity uom-def fdefs u1)]
    (map (partial ofbiz-xml-row->entity uom-def fdefs) uoms)))