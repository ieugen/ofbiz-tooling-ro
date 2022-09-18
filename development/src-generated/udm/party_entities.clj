(ns udm.party-entities
  (:require [clojure.spec.alpha :as s]
            [udm.field-types]))

(s/def :addendum/addendum-id :udm.field-types/id)
(s/def :addendum/agreement-id :udm.field-types/id)
(s/def :addendum/agreement-item-seq-id :udm.field-types/id)
(s/def :addendum/addendum-creation-date :udm.field-types/date-time)
(s/def :addendum/addendum-effective-date :udm.field-types/date-time)
(s/def :addendum/addendum-text :udm.field-types/long-varchar)
(s/def :udm.party-entities/addendum (s/keys :opt [:addendum/addendum-id
                                                  :addendum/agreement-id
                                                  :addendum/agreement-item-seq-id
                                                  :addendum/addendum-creation-date
                                                  :addendum/addendum-effective-date
                                                  :addendum/addendum-text]))


(s/def :agreement/agreement-id :udm.field-types/id)
(s/def :agreement/product-id :udm.field-types/id)
(s/def :agreement/party-id-from :udm.field-types/id)
(s/def :agreement/party-id-to :udm.field-types/id)
(s/def :agreement/role-type-id-from :udm.field-types/id)
(s/def :agreement/role-type-id-to :udm.field-types/id)
(s/def :agreement/agreement-type-id :udm.field-types/id)
(s/def :agreement/agreement-date :udm.field-types/date-time)
(s/def :agreement/from-date :udm.field-types/date-time)
(s/def :agreement/thru-date :udm.field-types/date-time)
(s/def :agreement/description :udm.field-types/description)
(s/def :agreement/text-data :udm.field-types/very-long)
(s/def :agreement/status-id :udm.field-types/id)
(s/def :udm.party-entities/agreement (s/keys :opt [:agreement/agreement-id
                                                   :agreement/product-id
                                                   :agreement/party-id-from
                                                   :agreement/party-id-to
                                                   :agreement/role-type-id-from
                                                   :agreement/role-type-id-to
                                                   :agreement/agreement-type-id
                                                   :agreement/agreement-date
                                                   :agreement/from-date
                                                   :agreement/thru-date
                                                   :agreement/description
                                                   :agreement/text-data
                                                   :agreement/status-id]))


(s/def :agreement-attribute/agreement-id :udm.field-types/id)
(s/def :agreement-attribute/attr-name :udm.field-types/id-long)
(s/def :agreement-attribute/attr-value :udm.field-types/value)
(s/def :agreement-attribute/attr-description :udm.field-types/description)
(s/def :udm.party-entities/agreement-attribute (s/keys :opt [:agreement-attribute/agreement-id
                                                             :agreement-attribute/attr-name
                                                             :agreement-attribute/attr-value
                                                             :agreement-attribute/attr-description]))


(s/def :agreement-geographical-applic/agreement-id :udm.field-types/id)
(s/def :agreement-geographical-applic/agreement-item-seq-id :udm.field-types/id)
(s/def :agreement-geographical-applic/geo-id :udm.field-types/id)
(s/def :udm.party-entities/agreement-geographical-applic (s/keys :opt [:agreement-geographical-applic/agreement-id
                                                                       :agreement-geographical-applic/agreement-item-seq-id
                                                                       :agreement-geographical-applic/geo-id]))


(s/def :agreement-item/agreement-id :udm.field-types/id)
(s/def :agreement-item/agreement-item-seq-id :udm.field-types/id)
(s/def :agreement-item/agreement-item-type-id :udm.field-types/id)
(s/def :agreement-item/currency-uom-id :udm.field-types/id)
(s/def :agreement-item/agreement-text :udm.field-types/very-long)
(s/def :agreement-item/agreement-image :udm.field-types/object)
(s/def :udm.party-entities/agreement-item (s/keys :opt [:agreement-item/agreement-id
                                                        :agreement-item/agreement-item-seq-id
                                                        :agreement-item/agreement-item-type-id
                                                        :agreement-item/currency-uom-id
                                                        :agreement-item/agreement-text
                                                        :agreement-item/agreement-image]))


(s/def :agreement-item-attribute/agreement-id :udm.field-types/id)
(s/def :agreement-item-attribute/agreement-item-seq-id :udm.field-types/id)
(s/def :agreement-item-attribute/attr-name :udm.field-types/id-long)
(s/def :agreement-item-attribute/attr-value :udm.field-types/value)
(s/def :agreement-item-attribute/attr-description :udm.field-types/description)
(s/def :udm.party-entities/agreement-item-attribute (s/keys :opt [:agreement-item-attribute/agreement-id
                                                                  :agreement-item-attribute/agreement-item-seq-id
                                                                  :agreement-item-attribute/attr-name
                                                                  :agreement-item-attribute/attr-value
                                                                  :agreement-item-attribute/attr-description]))


(s/def :agreement-item-type/agreement-item-type-id :udm.field-types/id)
(s/def :agreement-item-type/parent-type-id :udm.field-types/id)
(s/def :agreement-item-type/has-table :udm.field-types/indicator)
(s/def :agreement-item-type/description :udm.field-types/description)
(s/def :udm.party-entities/agreement-item-type (s/keys :opt [:agreement-item-type/agreement-item-type-id
                                                             :agreement-item-type/parent-type-id
                                                             :agreement-item-type/has-table
                                                             :agreement-item-type/description]))


(s/def :agreement-item-type-attr/agreement-item-type-id :udm.field-types/id)
(s/def :agreement-item-type-attr/attr-name :udm.field-types/id-long)
(s/def :agreement-item-type-attr/description :udm.field-types/description)
(s/def :udm.party-entities/agreement-item-type-attr (s/keys :opt [:agreement-item-type-attr/agreement-item-type-id
                                                                  :agreement-item-type-attr/attr-name
                                                                  :agreement-item-type-attr/description]))


(s/def :agreement-content/agreement-id :udm.field-types/id)
(s/def :agreement-content/agreement-item-seq-id :udm.field-types/id)
(s/def :agreement-content/agreement-content-type-id :udm.field-types/id)
(s/def :agreement-content/content-id :udm.field-types/id)
(s/def :agreement-content/from-date :udm.field-types/date-time)
(s/def :agreement-content/thru-date :udm.field-types/date-time)
(s/def :udm.party-entities/agreement-content (s/keys :opt [:agreement-content/agreement-id
                                                           :agreement-content/agreement-item-seq-id
                                                           :agreement-content/agreement-content-type-id
                                                           :agreement-content/content-id
                                                           :agreement-content/from-date
                                                           :agreement-content/thru-date]))


(s/def :agreement-content-type/agreement-content-type-id :udm.field-types/id)
(s/def :agreement-content-type/parent-type-id :udm.field-types/id)
(s/def :agreement-content-type/has-table :udm.field-types/indicator)
(s/def :agreement-content-type/description :udm.field-types/description)
(s/def :udm.party-entities/agreement-content-type (s/keys :opt [:agreement-content-type/agreement-content-type-id
                                                                :agreement-content-type/parent-type-id
                                                                :agreement-content-type/has-table
                                                                :agreement-content-type/description]))


(s/def :agreement-party-applic/agreement-id :udm.field-types/id)
(s/def :agreement-party-applic/agreement-item-seq-id :udm.field-types/id)
(s/def :agreement-party-applic/party-id :udm.field-types/id)
(s/def :udm.party-entities/agreement-party-applic (s/keys :opt [:agreement-party-applic/agreement-id
                                                                :agreement-party-applic/agreement-item-seq-id
                                                                :agreement-party-applic/party-id]))


(s/def :agreement-product-appl/agreement-id :udm.field-types/id)
(s/def :agreement-product-appl/agreement-item-seq-id :udm.field-types/id)
(s/def :agreement-product-appl/product-id :udm.field-types/id)
(s/def :agreement-product-appl/price :udm.field-types/currency-precise)
(s/def :udm.party-entities/agreement-product-appl (s/keys :opt [:agreement-product-appl/agreement-id
                                                                :agreement-product-appl/agreement-item-seq-id
                                                                :agreement-product-appl/product-id
                                                                :agreement-product-appl/price]))


(s/def :agreement-promo-appl/agreement-id :udm.field-types/id)
(s/def :agreement-promo-appl/agreement-item-seq-id :udm.field-types/id)
(s/def :agreement-promo-appl/product-promo-id :udm.field-types/id)
(s/def :agreement-promo-appl/from-date :udm.field-types/date-time)
(s/def :agreement-promo-appl/thru-date :udm.field-types/date-time)
(s/def :agreement-promo-appl/sequence-num :udm.field-types/numeric)
(s/def :udm.party-entities/agreement-promo-appl (s/keys :opt [:agreement-promo-appl/agreement-id
                                                              :agreement-promo-appl/agreement-item-seq-id
                                                              :agreement-promo-appl/product-promo-id
                                                              :agreement-promo-appl/from-date
                                                              :agreement-promo-appl/thru-date
                                                              :agreement-promo-appl/sequence-num]))


(s/def :agreement-facility-appl/agreement-id :udm.field-types/id)
(s/def :agreement-facility-appl/agreement-item-seq-id :udm.field-types/id)
(s/def :agreement-facility-appl/facility-id :udm.field-types/id)
(s/def :udm.party-entities/agreement-facility-appl (s/keys :opt [:agreement-facility-appl/agreement-id
                                                                 :agreement-facility-appl/agreement-item-seq-id
                                                                 :agreement-facility-appl/facility-id]))


(s/def :agreement-role/agreement-id :udm.field-types/id)
(s/def :agreement-role/party-id :udm.field-types/id)
(s/def :agreement-role/role-type-id :udm.field-types/id)
(s/def :udm.party-entities/agreement-role (s/keys :opt [:agreement-role/agreement-id
                                                        :agreement-role/party-id
                                                        :agreement-role/role-type-id]))


(s/def :agreement-status/agreement-id :udm.field-types/id)
(s/def :agreement-status/status-id :udm.field-types/id)
(s/def :agreement-status/status-date :udm.field-types/date-time)
(s/def :agreement-status/comments :udm.field-types/comment)
(s/def :agreement-status/change-by-user-login-id :udm.field-types/id-vlong)
(s/def :udm.party-entities/agreement-status (s/keys :opt [:agreement-status/agreement-id
                                                          :agreement-status/status-id
                                                          :agreement-status/status-date
                                                          :agreement-status/comments
                                                          :agreement-status/change-by-user-login-id]))


(s/def :agreement-term/agreement-term-id :udm.field-types/id)
(s/def :agreement-term/term-type-id :udm.field-types/id)
(s/def :agreement-term/agreement-id :udm.field-types/id)
(s/def :agreement-term/agreement-item-seq-id :udm.field-types/id)
(s/def :agreement-term/invoice-item-type-id :udm.field-types/id)
(s/def :agreement-term/from-date :udm.field-types/date-time)
(s/def :agreement-term/thru-date :udm.field-types/date-time)
(s/def :agreement-term/term-value :udm.field-types/currency-precise)
(s/def :agreement-term/term-days :udm.field-types/numeric)
(s/def :agreement-term/text-value :udm.field-types/description)
(s/def :agreement-term/min-quantity :udm.field-types/floating-point)
(s/def :agreement-term/max-quantity :udm.field-types/floating-point)
(s/def :agreement-term/description :udm.field-types/description)
(s/def :udm.party-entities/agreement-term (s/keys :opt [:agreement-term/agreement-term-id
                                                        :agreement-term/term-type-id
                                                        :agreement-term/agreement-id
                                                        :agreement-term/agreement-item-seq-id
                                                        :agreement-term/invoice-item-type-id
                                                        :agreement-term/from-date
                                                        :agreement-term/thru-date
                                                        :agreement-term/term-value
                                                        :agreement-term/term-days
                                                        :agreement-term/text-value
                                                        :agreement-term/min-quantity
                                                        :agreement-term/max-quantity
                                                        :agreement-term/description]))


(s/def :agreement-term-attribute/agreement-term-id :udm.field-types/id)
(s/def :agreement-term-attribute/attr-name :udm.field-types/id-long)
(s/def :agreement-term-attribute/attr-value :udm.field-types/value)
(s/def :agreement-term-attribute/attr-description :udm.field-types/description)
(s/def :udm.party-entities/agreement-term-attribute (s/keys :opt [:agreement-term-attribute/agreement-term-id
                                                                  :agreement-term-attribute/attr-name
                                                                  :agreement-term-attribute/attr-value
                                                                  :agreement-term-attribute/attr-description]))


(s/def :agreement-type/agreement-type-id :udm.field-types/id)
(s/def :agreement-type/parent-type-id :udm.field-types/id)
(s/def :agreement-type/has-table :udm.field-types/indicator)
(s/def :agreement-type/description :udm.field-types/description)
(s/def :udm.party-entities/agreement-type (s/keys :opt [:agreement-type/agreement-type-id
                                                        :agreement-type/parent-type-id
                                                        :agreement-type/has-table
                                                        :agreement-type/description]))


(s/def :agreement-type-attr/agreement-type-id :udm.field-types/id)
(s/def :agreement-type-attr/attr-name :udm.field-types/id-long)
(s/def :agreement-type-attr/description :udm.field-types/description)
(s/def :udm.party-entities/agreement-type-attr (s/keys :opt [:agreement-type-attr/agreement-type-id
                                                             :agreement-type-attr/attr-name
                                                             :agreement-type-attr/description]))


(s/def :agreement-work-effort-applic/agreement-id :udm.field-types/id)
(s/def :agreement-work-effort-applic/agreement-item-seq-id :udm.field-types/id)
(s/def :agreement-work-effort-applic/work-effort-id :udm.field-types/id)
(s/def :udm.party-entities/agreement-work-effort-applic (s/keys :opt [:agreement-work-effort-applic/agreement-id
                                                                      :agreement-work-effort-applic/agreement-item-seq-id
                                                                      :agreement-work-effort-applic/work-effort-id]))


(s/def :term-type/term-type-id :udm.field-types/id)
(s/def :term-type/parent-type-id :udm.field-types/id)
(s/def :term-type/has-table :udm.field-types/indicator)
(s/def :term-type/description :udm.field-types/description)
(s/def :udm.party-entities/term-type (s/keys :opt [:term-type/term-type-id
                                                   :term-type/parent-type-id
                                                   :term-type/has-table
                                                   :term-type/description]))


(s/def :term-type-attr/term-type-id :udm.field-types/id)
(s/def :term-type-attr/attr-name :udm.field-types/id-long)
(s/def :term-type-attr/description :udm.field-types/description)
(s/def :udm.party-entities/term-type-attr (s/keys :opt [:term-type-attr/term-type-id
                                                        :term-type-attr/attr-name
                                                        :term-type-attr/description]))


(s/def :agreement-employment-appl/agreement-id :udm.field-types/id)
(s/def :agreement-employment-appl/agreement-item-seq-id :udm.field-types/id)
(s/def :agreement-employment-appl/party-id-from :udm.field-types/id)
(s/def :agreement-employment-appl/party-id-to :udm.field-types/id)
(s/def :agreement-employment-appl/role-type-id-from :udm.field-types/id)
(s/def :agreement-employment-appl/role-type-id-to :udm.field-types/id)
(s/def :agreement-employment-appl/from-date :udm.field-types/date-time)
(s/def :agreement-employment-appl/agreement-date :udm.field-types/date-time)
(s/def :agreement-employment-appl/thru-date :udm.field-types/date-time)
(s/def :udm.party-entities/agreement-employment-appl (s/keys :opt [:agreement-employment-appl/agreement-id
                                                                   :agreement-employment-appl/agreement-item-seq-id
                                                                   :agreement-employment-appl/party-id-from
                                                                   :agreement-employment-appl/party-id-to
                                                                   :agreement-employment-appl/role-type-id-from
                                                                   :agreement-employment-appl/role-type-id-to
                                                                   :agreement-employment-appl/from-date
                                                                   :agreement-employment-appl/agreement-date
                                                                   :agreement-employment-appl/thru-date]))


(s/def :comm-content-assoc-type/comm-content-assoc-type-id :udm.field-types/id)
(s/def :comm-content-assoc-type/description :udm.field-types/description)
(s/def :udm.party-entities/comm-content-assoc-type (s/keys :opt [:comm-content-assoc-type/comm-content-assoc-type-id
                                                                 :comm-content-assoc-type/description]))


(s/def :comm-event-content-assoc/content-id :udm.field-types/id)
(s/def :comm-event-content-assoc/communication-event-id :udm.field-types/id)
(s/def :comm-event-content-assoc/comm-content-assoc-type-id :udm.field-types/id)
(s/def :comm-event-content-assoc/from-date :udm.field-types/date-time)
(s/def :comm-event-content-assoc/thru-date :udm.field-types/date-time)
(s/def :comm-event-content-assoc/sequence-num :udm.field-types/numeric)
(s/def :udm.party-entities/comm-event-content-assoc (s/keys :opt [:comm-event-content-assoc/content-id
                                                                  :comm-event-content-assoc/communication-event-id
                                                                  :comm-event-content-assoc/comm-content-assoc-type-id
                                                                  :comm-event-content-assoc/from-date
                                                                  :comm-event-content-assoc/thru-date
                                                                  :comm-event-content-assoc/sequence-num]))


(s/def :communication-event/communication-event-id :udm.field-types/id)
(s/def :communication-event/communication-event-type-id :udm.field-types/id)
(s/def :communication-event/orig-comm-event-id :udm.field-types/id)
(s/def :communication-event/parent-comm-event-id :udm.field-types/id)
(s/def :communication-event/status-id :udm.field-types/id)
(s/def :communication-event/contact-mech-type-id :udm.field-types/id)
(s/def :communication-event/contact-mech-id-from :udm.field-types/id)
(s/def :communication-event/contact-mech-id-to :udm.field-types/id)
(s/def :communication-event/role-type-id-from :udm.field-types/id)
(s/def :communication-event/role-type-id-to :udm.field-types/id)
(s/def :communication-event/party-id-from :udm.field-types/id)
(s/def :communication-event/party-id-to :udm.field-types/id)
(s/def :communication-event/entry-date :udm.field-types/date-time)
(s/def :communication-event/datetime-started :udm.field-types/date-time)
(s/def :communication-event/datetime-ended :udm.field-types/date-time)
(s/def :communication-event/subject :udm.field-types/long-varchar)
(s/def :communication-event/content-mime-type-id :udm.field-types/id-vlong)
(s/def :communication-event/content :udm.field-types/very-long)
(s/def :communication-event/note :udm.field-types/comment)
(s/def :communication-event/reason-enum-id :udm.field-types/id)
(s/def :communication-event/contact-list-id :udm.field-types/id)
(s/def :communication-event/header-string :udm.field-types/very-long)
(s/def :communication-event/from-string :udm.field-types/very-long)
(s/def :communication-event/to-string :udm.field-types/very-long)
(s/def :communication-event/cc-string :udm.field-types/very-long)
(s/def :communication-event/bcc-string :udm.field-types/very-long)
(s/def :communication-event/message-id :udm.field-types/value)
(s/def :udm.party-entities/communication-event (s/keys :opt [:communication-event/communication-event-id
                                                             :communication-event/communication-event-type-id
                                                             :communication-event/orig-comm-event-id
                                                             :communication-event/parent-comm-event-id
                                                             :communication-event/status-id
                                                             :communication-event/contact-mech-type-id
                                                             :communication-event/contact-mech-id-from
                                                             :communication-event/contact-mech-id-to
                                                             :communication-event/role-type-id-from
                                                             :communication-event/role-type-id-to
                                                             :communication-event/party-id-from
                                                             :communication-event/party-id-to
                                                             :communication-event/entry-date
                                                             :communication-event/datetime-started
                                                             :communication-event/datetime-ended
                                                             :communication-event/subject
                                                             :communication-event/content-mime-type-id
                                                             :communication-event/content
                                                             :communication-event/note
                                                             :communication-event/reason-enum-id
                                                             :communication-event/contact-list-id
                                                             :communication-event/header-string
                                                             :communication-event/from-string
                                                             :communication-event/to-string
                                                             :communication-event/cc-string
                                                             :communication-event/bcc-string
                                                             :communication-event/message-id]))


(s/def :communication-event-product/product-id :udm.field-types/id)
(s/def :communication-event-product/communication-event-id :udm.field-types/id)
(s/def :udm.party-entities/communication-event-product (s/keys :opt [:communication-event-product/product-id
                                                                     :communication-event-product/communication-event-id]))


(s/def :communication-event-prp-typ/communication-event-prp-typ-id :udm.field-types/id)
(s/def :communication-event-prp-typ/parent-type-id :udm.field-types/id)
(s/def :communication-event-prp-typ/has-table :udm.field-types/indicator)
(s/def :communication-event-prp-typ/description :udm.field-types/description)
(s/def :udm.party-entities/communication-event-prp-typ (s/keys :opt [:communication-event-prp-typ/communication-event-prp-typ-id
                                                                     :communication-event-prp-typ/parent-type-id
                                                                     :communication-event-prp-typ/has-table
                                                                     :communication-event-prp-typ/description]))


(s/def :communication-event-purpose/communication-event-prp-typ-id :udm.field-types/id)
(s/def :communication-event-purpose/communication-event-id :udm.field-types/id)
(s/def :communication-event-purpose/description :udm.field-types/description)
(s/def :udm.party-entities/communication-event-purpose (s/keys :opt [:communication-event-purpose/communication-event-prp-typ-id
                                                                     :communication-event-purpose/communication-event-id
                                                                     :communication-event-purpose/description]))


(s/def :communication-event-role/communication-event-id :udm.field-types/id)
(s/def :communication-event-role/party-id :udm.field-types/id)
(s/def :communication-event-role/role-type-id :udm.field-types/id)
(s/def :communication-event-role/contact-mech-id :udm.field-types/id)
(s/def :communication-event-role/status-id :udm.field-types/id)
(s/def :udm.party-entities/communication-event-role (s/keys :opt [:communication-event-role/communication-event-id
                                                                  :communication-event-role/party-id
                                                                  :communication-event-role/role-type-id
                                                                  :communication-event-role/contact-mech-id
                                                                  :communication-event-role/status-id]))


(s/def :communication-event-type/communication-event-type-id :udm.field-types/id)
(s/def :communication-event-type/parent-type-id :udm.field-types/id)
(s/def :communication-event-type/has-table :udm.field-types/indicator)
(s/def :communication-event-type/description :udm.field-types/description)
(s/def :communication-event-type/contact-mech-type-id :udm.field-types/id)
(s/def :udm.party-entities/communication-event-type (s/keys :opt [:communication-event-type/communication-event-type-id
                                                                  :communication-event-type/parent-type-id
                                                                  :communication-event-type/has-table
                                                                  :communication-event-type/description
                                                                  :communication-event-type/contact-mech-type-id]))


(s/def :contact-mech/contact-mech-id :udm.field-types/id)
(s/def :contact-mech/contact-mech-type-id :udm.field-types/id)
(s/def :contact-mech/info-string :udm.field-types/long-varchar)
(s/def :udm.party-entities/contact-mech (s/keys :opt [:contact-mech/contact-mech-id
                                                      :contact-mech/contact-mech-type-id
                                                      :contact-mech/info-string]))


(s/def :contact-mech-attribute/contact-mech-id :udm.field-types/id)
(s/def :contact-mech-attribute/attr-name :udm.field-types/id-long)
(s/def :contact-mech-attribute/attr-value :udm.field-types/value)
(s/def :contact-mech-attribute/attr-description :udm.field-types/description)
(s/def :udm.party-entities/contact-mech-attribute (s/keys :opt [:contact-mech-attribute/contact-mech-id
                                                                :contact-mech-attribute/attr-name
                                                                :contact-mech-attribute/attr-value
                                                                :contact-mech-attribute/attr-description]))


(s/def :contact-mech-link/contact-mech-id-from :udm.field-types/id)
(s/def :contact-mech-link/contact-mech-id-to :udm.field-types/id)
(s/def :udm.party-entities/contact-mech-link (s/keys :opt [:contact-mech-link/contact-mech-id-from
                                                           :contact-mech-link/contact-mech-id-to]))


(s/def :contact-mech-purpose-type/contact-mech-purpose-type-id :udm.field-types/id)
(s/def :contact-mech-purpose-type/parent-type-id :udm.field-types/id)
(s/def :contact-mech-purpose-type/has-table :udm.field-types/indicator)
(s/def :contact-mech-purpose-type/description :udm.field-types/description)
(s/def :udm.party-entities/contact-mech-purpose-type (s/keys :opt [:contact-mech-purpose-type/contact-mech-purpose-type-id
                                                                   :contact-mech-purpose-type/parent-type-id
                                                                   :contact-mech-purpose-type/has-table
                                                                   :contact-mech-purpose-type/description]))


(s/def :contact-mech-type/contact-mech-type-id :udm.field-types/id)
(s/def :contact-mech-type/parent-type-id :udm.field-types/id)
(s/def :contact-mech-type/has-table :udm.field-types/indicator)
(s/def :contact-mech-type/description :udm.field-types/description)
(s/def :udm.party-entities/contact-mech-type (s/keys :opt [:contact-mech-type/contact-mech-type-id
                                                           :contact-mech-type/parent-type-id
                                                           :contact-mech-type/has-table
                                                           :contact-mech-type/description]))


(s/def :contact-mech-type-attr/contact-mech-type-id :udm.field-types/id)
(s/def :contact-mech-type-attr/attr-name :udm.field-types/id-long)
(s/def :contact-mech-type-attr/description :udm.field-types/description)
(s/def :udm.party-entities/contact-mech-type-attr (s/keys :opt [:contact-mech-type-attr/contact-mech-type-id
                                                                :contact-mech-type-attr/attr-name
                                                                :contact-mech-type-attr/description]))


(s/def :contact-mech-type-purpose/contact-mech-type-id :udm.field-types/id)
(s/def :contact-mech-type-purpose/contact-mech-purpose-type-id :udm.field-types/id)
(s/def :udm.party-entities/contact-mech-type-purpose (s/keys :opt [:contact-mech-type-purpose/contact-mech-type-id
                                                                   :contact-mech-type-purpose/contact-mech-purpose-type-id]))


(s/def :email-address-verification/email-address :udm.field-types/id-vlong)
(s/def :email-address-verification/verify-hash :udm.field-types/value)
(s/def :email-address-verification/expire-date :udm.field-types/date-time)
(s/def :udm.party-entities/email-address-verification (s/keys :opt [:email-address-verification/email-address
                                                                    :email-address-verification/verify-hash
                                                                    :email-address-verification/expire-date]))


(s/def :party-contact-mech/party-id :udm.field-types/id)
(s/def :party-contact-mech/contact-mech-id :udm.field-types/id)
(s/def :party-contact-mech/from-date :udm.field-types/date-time)
(s/def :party-contact-mech/thru-date :udm.field-types/date-time)
(s/def :party-contact-mech/role-type-id :udm.field-types/id)
(s/def :party-contact-mech/allow-solicitation :udm.field-types/indicator)
(s/def :party-contact-mech/extension :udm.field-types/long-varchar)
(s/def :party-contact-mech/verified :udm.field-types/indicator)
(s/def :party-contact-mech/comments :udm.field-types/comment)
(s/def :party-contact-mech/years-with-contact-mech :udm.field-types/numeric)
(s/def :party-contact-mech/months-with-contact-mech :udm.field-types/numeric)
(s/def :udm.party-entities/party-contact-mech (s/keys :opt [:party-contact-mech/party-id
                                                            :party-contact-mech/contact-mech-id
                                                            :party-contact-mech/from-date
                                                            :party-contact-mech/thru-date
                                                            :party-contact-mech/role-type-id
                                                            :party-contact-mech/allow-solicitation
                                                            :party-contact-mech/extension
                                                            :party-contact-mech/verified
                                                            :party-contact-mech/comments
                                                            :party-contact-mech/years-with-contact-mech
                                                            :party-contact-mech/months-with-contact-mech]))


(s/def :party-contact-mech-purpose/party-id :udm.field-types/id)
(s/def :party-contact-mech-purpose/contact-mech-id :udm.field-types/id)
(s/def :party-contact-mech-purpose/contact-mech-purpose-type-id :udm.field-types/id)
(s/def :party-contact-mech-purpose/from-date :udm.field-types/date-time)
(s/def :party-contact-mech-purpose/thru-date :udm.field-types/date-time)
(s/def :udm.party-entities/party-contact-mech-purpose (s/keys :opt [:party-contact-mech-purpose/party-id
                                                                    :party-contact-mech-purpose/contact-mech-id
                                                                    :party-contact-mech-purpose/contact-mech-purpose-type-id
                                                                    :party-contact-mech-purpose/from-date
                                                                    :party-contact-mech-purpose/thru-date]))


(s/def :postal-address/contact-mech-id :udm.field-types/id)
(s/def :postal-address/to-name :udm.field-types/name)
(s/def :postal-address/attn-name :udm.field-types/name)
(s/def :postal-address/address-1 :udm.field-types/long-varchar)
(s/def :postal-address/address-2 :udm.field-types/long-varchar)
(s/def :postal-address/house-number :udm.field-types/numeric)
(s/def :postal-address/house-number-ext :udm.field-types/short-varchar)
(s/def :postal-address/directions :udm.field-types/long-varchar)
(s/def :postal-address/city :udm.field-types/name)
(s/def :postal-address/city-geo-id :udm.field-types/id)
(s/def :postal-address/postal-code :udm.field-types/short-varchar)
(s/def :postal-address/postal-code-ext :udm.field-types/short-varchar)
(s/def :postal-address/country-geo-id :udm.field-types/id)
(s/def :postal-address/state-province-geo-id :udm.field-types/id)
(s/def :postal-address/county-geo-id :udm.field-types/id)
(s/def :postal-address/municipality-geo-id :udm.field-types/id)
(s/def :postal-address/postal-code-geo-id :udm.field-types/id)
(s/def :postal-address/geo-point-id :udm.field-types/id)
(s/def :udm.party-entities/postal-address (s/keys :opt [:postal-address/contact-mech-id
                                                        :postal-address/to-name
                                                        :postal-address/attn-name
                                                        :postal-address/address-1
                                                        :postal-address/address-2
                                                        :postal-address/house-number
                                                        :postal-address/house-number-ext
                                                        :postal-address/directions
                                                        :postal-address/city
                                                        :postal-address/city-geo-id
                                                        :postal-address/postal-code
                                                        :postal-address/postal-code-ext
                                                        :postal-address/country-geo-id
                                                        :postal-address/state-province-geo-id
                                                        :postal-address/county-geo-id
                                                        :postal-address/municipality-geo-id
                                                        :postal-address/postal-code-geo-id
                                                        :postal-address/geo-point-id]))


(s/def :postal-address-boundary/contact-mech-id :udm.field-types/id)
(s/def :postal-address-boundary/geo-id :udm.field-types/id)
(s/def :udm.party-entities/postal-address-boundary (s/keys :opt [:postal-address-boundary/contact-mech-id
                                                                 :postal-address-boundary/geo-id]))


(s/def :telecom-number/contact-mech-id :udm.field-types/id)
(s/def :telecom-number/country-code :udm.field-types/very-short)
(s/def :telecom-number/area-code :udm.field-types/very-short)
(s/def :telecom-number/contact-number :udm.field-types/short-varchar)
(s/def :telecom-number/ask-for-name :udm.field-types/name)
(s/def :udm.party-entities/telecom-number (s/keys :opt [:telecom-number/contact-mech-id
                                                        :telecom-number/country-code
                                                        :telecom-number/area-code
                                                        :telecom-number/contact-number
                                                        :telecom-number/ask-for-name]))


(s/def :ftp-address/contact-mech-id :udm.field-types/id)
(s/def :ftp-address/hostname :udm.field-types/long-varchar)
(s/def :ftp-address/port :udm.field-types/numeric)
(s/def :ftp-address/username :udm.field-types/long-varchar)
(s/def :ftp-address/ftp-password :udm.field-types/long-varchar)
(s/def :ftp-address/binary-transfer :udm.field-types/indicator)
(s/def :ftp-address/file-path :udm.field-types/long-varchar)
(s/def :ftp-address/zip-file :udm.field-types/indicator)
(s/def :ftp-address/passive-mode :udm.field-types/indicator)
(s/def :ftp-address/default-timeout :udm.field-types/numeric)
(s/def :udm.party-entities/ftp-address (s/keys :opt [:ftp-address/contact-mech-id
                                                     :ftp-address/hostname
                                                     :ftp-address/port
                                                     :ftp-address/username
                                                     :ftp-address/ftp-password
                                                     :ftp-address/binary-transfer
                                                     :ftp-address/file-path
                                                     :ftp-address/zip-file
                                                     :ftp-address/passive-mode
                                                     :ftp-address/default-timeout]))


(s/def :valid-contact-mech-role/role-type-id :udm.field-types/id)
(s/def :valid-contact-mech-role/contact-mech-type-id :udm.field-types/id)
(s/def :udm.party-entities/valid-contact-mech-role (s/keys :opt [:valid-contact-mech-role/role-type-id
                                                                 :valid-contact-mech-role/contact-mech-type-id]))


(s/def :need-type/need-type-id :udm.field-types/id)
(s/def :need-type/description :udm.field-types/description)
(s/def :udm.party-entities/need-type (s/keys :opt [:need-type/need-type-id :need-type/description]))


(s/def :party-need/party-need-id :udm.field-types/id)
(s/def :party-need/party-id :udm.field-types/id)
(s/def :party-need/role-type-id :udm.field-types/id)
(s/def :party-need/party-type-id :udm.field-types/id)
(s/def :party-need/need-type-id :udm.field-types/id)
(s/def :party-need/communication-event-id :udm.field-types/id)
(s/def :party-need/product-id :udm.field-types/id)
(s/def :party-need/product-category-id :udm.field-types/id)
(s/def :party-need/visit-id :udm.field-types/id)
(s/def :party-need/datetime-recorded :udm.field-types/date-time)
(s/def :party-need/description :udm.field-types/description)
(s/def :udm.party-entities/party-need (s/keys :opt [:party-need/party-need-id
                                                    :party-need/party-id
                                                    :party-need/role-type-id
                                                    :party-need/party-type-id
                                                    :party-need/need-type-id
                                                    :party-need/communication-event-id
                                                    :party-need/product-id
                                                    :party-need/product-category-id
                                                    :party-need/visit-id
                                                    :party-need/datetime-recorded
                                                    :party-need/description]))


(s/def :address-match-map/map-key :udm.field-types/id-vlong)
(s/def :address-match-map/map-value :udm.field-types/id-vlong)
(s/def :address-match-map/sequence-num :udm.field-types/numeric)
(s/def :udm.party-entities/address-match-map (s/keys :opt [:address-match-map/map-key
                                                           :address-match-map/map-value
                                                           :address-match-map/sequence-num]))


(s/def :affiliate/party-id :udm.field-types/id)
(s/def :affiliate/affiliate-name :udm.field-types/name)
(s/def :affiliate/affiliate-description :udm.field-types/description)
(s/def :affiliate/year-established :udm.field-types/very-short)
(s/def :affiliate/site-type :udm.field-types/comment)
(s/def :affiliate/site-page-views :udm.field-types/comment)
(s/def :affiliate/site-visitors :udm.field-types/comment)
(s/def :affiliate/date-time-created :udm.field-types/date-time)
(s/def :affiliate/date-time-approved :udm.field-types/date-time)
(s/def :udm.party-entities/affiliate (s/keys :opt [:affiliate/party-id
                                                   :affiliate/affiliate-name
                                                   :affiliate/affiliate-description
                                                   :affiliate/year-established
                                                   :affiliate/site-type
                                                   :affiliate/site-page-views
                                                   :affiliate/site-visitors
                                                   :affiliate/date-time-created
                                                   :affiliate/date-time-approved]))


(s/def :party/party-id :udm.field-types/id)
(s/def :party/party-type-id :udm.field-types/id)
(s/def :party/external-id :udm.field-types/id)
(s/def :party/preferred-currency-uom-id :udm.field-types/id)
(s/def :party/description :udm.field-types/very-long)
(s/def :party/status-id :udm.field-types/id)
(s/def :party/created-date :udm.field-types/date-time)
(s/def :party/created-by-user-login :udm.field-types/id-vlong)
(s/def :party/last-modified-date :udm.field-types/date-time)
(s/def :party/last-modified-by-user-login :udm.field-types/id-vlong)
(s/def :party/data-source-id :udm.field-types/id)
(s/def :party/is-unread :udm.field-types/indicator)
(s/def :udm.party-entities/party (s/keys :opt [:party/party-id
                                               :party/party-type-id
                                               :party/external-id
                                               :party/preferred-currency-uom-id
                                               :party/description
                                               :party/status-id
                                               :party/created-date
                                               :party/created-by-user-login
                                               :party/last-modified-date
                                               :party/last-modified-by-user-login
                                               :party/data-source-id
                                               :party/is-unread]))


(s/def :party-identification/party-id :udm.field-types/id)
(s/def :party-identification/party-identification-type-id :udm.field-types/id)
(s/def :party-identification/id-value :udm.field-types/id-long)
(s/def :udm.party-entities/party-identification (s/keys :opt [:party-identification/party-id
                                                              :party-identification/party-identification-type-id
                                                              :party-identification/id-value]))


(s/def :party-identification-type/party-identification-type-id :udm.field-types/id)
(s/def :party-identification-type/parent-type-id :udm.field-types/id)
(s/def :party-identification-type/has-table :udm.field-types/indicator)
(s/def :party-identification-type/description :udm.field-types/description)
(s/def :udm.party-entities/party-identification-type (s/keys :opt [:party-identification-type/party-identification-type-id
                                                                   :party-identification-type/parent-type-id
                                                                   :party-identification-type/has-table
                                                                   :party-identification-type/description]))


(s/def :party-geo-point/party-id :udm.field-types/id)
(s/def :party-geo-point/geo-point-id :udm.field-types/id)
(s/def :party-geo-point/from-date :udm.field-types/date-time)
(s/def :party-geo-point/thru-date :udm.field-types/date-time)
(s/def :udm.party-entities/party-geo-point (s/keys :opt [:party-geo-point/party-id
                                                         :party-geo-point/geo-point-id
                                                         :party-geo-point/from-date
                                                         :party-geo-point/thru-date]))


(s/def :party-attribute/party-id :udm.field-types/id)
(s/def :party-attribute/attr-name :udm.field-types/id-long)
(s/def :party-attribute/attr-value :udm.field-types/value)
(s/def :party-attribute/attr-description :udm.field-types/description)
(s/def :udm.party-entities/party-attribute (s/keys :opt [:party-attribute/party-id
                                                         :party-attribute/attr-name
                                                         :party-attribute/attr-value
                                                         :party-attribute/attr-description]))


(s/def :party-carrier-account/party-id :udm.field-types/id)
(s/def :party-carrier-account/carrier-party-id :udm.field-types/id)
(s/def :party-carrier-account/from-date :udm.field-types/date-time)
(s/def :party-carrier-account/thru-date :udm.field-types/date-time)
(s/def :party-carrier-account/account-number :udm.field-types/id)
(s/def :udm.party-entities/party-carrier-account (s/keys :opt [:party-carrier-account/party-id
                                                               :party-carrier-account/carrier-party-id
                                                               :party-carrier-account/from-date
                                                               :party-carrier-account/thru-date
                                                               :party-carrier-account/account-number]))


(s/def :party-classification/party-id :udm.field-types/id)
(s/def :party-classification/party-classification-group-id :udm.field-types/id)
(s/def :party-classification/from-date :udm.field-types/date-time)
(s/def :party-classification/thru-date :udm.field-types/date-time)
(s/def :udm.party-entities/party-classification (s/keys :opt [:party-classification/party-id
                                                              :party-classification/party-classification-group-id
                                                              :party-classification/from-date
                                                              :party-classification/thru-date]))


(s/def :party-classification-group/party-classification-group-id :udm.field-types/id)
(s/def :party-classification-group/party-classification-type-id :udm.field-types/id)
(s/def :party-classification-group/parent-group-id :udm.field-types/id)
(s/def :party-classification-group/description :udm.field-types/description)
(s/def :udm.party-entities/party-classification-group (s/keys :opt [:party-classification-group/party-classification-group-id
                                                                    :party-classification-group/party-classification-type-id
                                                                    :party-classification-group/parent-group-id
                                                                    :party-classification-group/description]))


(s/def :party-classification-type/party-classification-type-id :udm.field-types/id)
(s/def :party-classification-type/parent-type-id :udm.field-types/id)
(s/def :party-classification-type/has-table :udm.field-types/indicator)
(s/def :party-classification-type/description :udm.field-types/description)
(s/def :udm.party-entities/party-classification-type (s/keys :opt [:party-classification-type/party-classification-type-id
                                                                   :party-classification-type/parent-type-id
                                                                   :party-classification-type/has-table
                                                                   :party-classification-type/description]))


(s/def :party-content/party-id :udm.field-types/id)
(s/def :party-content/content-id :udm.field-types/id)
(s/def :party-content/party-content-type-id :udm.field-types/id)
(s/def :party-content/from-date :udm.field-types/date-time)
(s/def :party-content/thru-date :udm.field-types/date-time)
(s/def :udm.party-entities/party-content (s/keys :opt [:party-content/party-id
                                                       :party-content/content-id
                                                       :party-content/party-content-type-id
                                                       :party-content/from-date
                                                       :party-content/thru-date]))


(s/def :party-content-type/party-content-type-id :udm.field-types/id)
(s/def :party-content-type/parent-type-id :udm.field-types/id)
(s/def :party-content-type/description :udm.field-types/description)
(s/def :udm.party-entities/party-content-type (s/keys :opt [:party-content-type/party-content-type-id
                                                            :party-content-type/parent-type-id
                                                            :party-content-type/description]))


(s/def :party-data-source/party-id :udm.field-types/id)
(s/def :party-data-source/data-source-id :udm.field-types/id)
(s/def :party-data-source/from-date :udm.field-types/date-time)
(s/def :party-data-source/visit-id :udm.field-types/id)
(s/def :party-data-source/comments :udm.field-types/comment)
(s/def :party-data-source/is-create :udm.field-types/indicator)
(s/def :udm.party-entities/party-data-source (s/keys :opt [:party-data-source/party-id
                                                           :party-data-source/data-source-id
                                                           :party-data-source/from-date
                                                           :party-data-source/visit-id
                                                           :party-data-source/comments
                                                           :party-data-source/is-create]))


(s/def :party-group/party-id :udm.field-types/id)
(s/def :party-group/group-name :udm.field-types/name)
(s/def :party-group/group-name-local :udm.field-types/name)
(s/def :party-group/office-site-name :udm.field-types/name)
(s/def :party-group/annual-revenue :udm.field-types/currency-amount)
(s/def :party-group/num-employees :udm.field-types/numeric)
(s/def :party-group/ticker-symbol :udm.field-types/very-short)
(s/def :party-group/comments :udm.field-types/comment)
(s/def :party-group/logo-image-url :udm.field-types/url)
(s/def :udm.party-entities/party-group (s/keys :opt [:party-group/party-id
                                                     :party-group/group-name
                                                     :party-group/group-name-local
                                                     :party-group/office-site-name
                                                     :party-group/annual-revenue
                                                     :party-group/num-employees
                                                     :party-group/ticker-symbol
                                                     :party-group/comments
                                                     :party-group/logo-image-url]))


(s/def :party-ics-avs-override/party-id :udm.field-types/id)
(s/def :party-ics-avs-override/avs-decline-string :udm.field-types/long-varchar)
(s/def :udm.party-entities/party-ics-avs-override (s/keys :opt [:party-ics-avs-override/party-id
                                                                :party-ics-avs-override/avs-decline-string]))


(s/def :party-invitation/party-invitation-id :udm.field-types/id)
(s/def :party-invitation/party-id-from :udm.field-types/id)
(s/def :party-invitation/party-id :udm.field-types/id)
(s/def :party-invitation/to-name :udm.field-types/name)
(s/def :party-invitation/email-address :udm.field-types/long-varchar)
(s/def :party-invitation/status-id :udm.field-types/id)
(s/def :party-invitation/last-invite-date :udm.field-types/date-time)
(s/def :udm.party-entities/party-invitation (s/keys :opt [:party-invitation/party-invitation-id
                                                          :party-invitation/party-id-from
                                                          :party-invitation/party-id
                                                          :party-invitation/to-name
                                                          :party-invitation/email-address
                                                          :party-invitation/status-id
                                                          :party-invitation/last-invite-date]))


(s/def :party-invitation-group-assoc/party-invitation-id :udm.field-types/id)
(s/def :party-invitation-group-assoc/party-id-to :udm.field-types/id)
(s/def :udm.party-entities/party-invitation-group-assoc (s/keys :opt [:party-invitation-group-assoc/party-invitation-id
                                                                      :party-invitation-group-assoc/party-id-to]))


(s/def :party-invitation-role-assoc/party-invitation-id :udm.field-types/id)
(s/def :party-invitation-role-assoc/role-type-id :udm.field-types/id)
(s/def :udm.party-entities/party-invitation-role-assoc (s/keys :opt [:party-invitation-role-assoc/party-invitation-id
                                                                     :party-invitation-role-assoc/role-type-id]))


(s/def :party-name-history/party-id :udm.field-types/id)
(s/def :party-name-history/change-date :udm.field-types/date-time)
(s/def :party-name-history/group-name :udm.field-types/name)
(s/def :party-name-history/first-name :udm.field-types/name)
(s/def :party-name-history/middle-name :udm.field-types/name)
(s/def :party-name-history/last-name :udm.field-types/name)
(s/def :party-name-history/personal-title :udm.field-types/name)
(s/def :party-name-history/suffix :udm.field-types/name)
(s/def :udm.party-entities/party-name-history (s/keys :opt [:party-name-history/party-id
                                                            :party-name-history/change-date
                                                            :party-name-history/group-name
                                                            :party-name-history/first-name
                                                            :party-name-history/middle-name
                                                            :party-name-history/last-name
                                                            :party-name-history/personal-title
                                                            :party-name-history/suffix]))


(s/def :party-note/party-id :udm.field-types/id)
(s/def :party-note/note-id :udm.field-types/id)
(s/def :udm.party-entities/party-note (s/keys :opt [:party-note/party-id :party-note/note-id]))


(s/def :party-profile-default/party-id :udm.field-types/id)
(s/def :party-profile-default/product-store-id :udm.field-types/id)
(s/def :party-profile-default/default-ship-addr :udm.field-types/id)
(s/def :party-profile-default/default-bill-addr :udm.field-types/id)
(s/def :party-profile-default/default-pay-meth :udm.field-types/id)
(s/def :party-profile-default/default-ship-meth :udm.field-types/id)
(s/def :udm.party-entities/party-profile-default (s/keys :opt [:party-profile-default/party-id
                                                               :party-profile-default/product-store-id
                                                               :party-profile-default/default-ship-addr
                                                               :party-profile-default/default-bill-addr
                                                               :party-profile-default/default-pay-meth
                                                               :party-profile-default/default-ship-meth]))


(s/def :party-relationship/party-id-from :udm.field-types/id)
(s/def :party-relationship/party-id-to :udm.field-types/id)
(s/def :party-relationship/role-type-id-from :udm.field-types/id)
(s/def :party-relationship/role-type-id-to :udm.field-types/id)
(s/def :party-relationship/from-date :udm.field-types/date-time)
(s/def :party-relationship/thru-date :udm.field-types/date-time)
(s/def :party-relationship/status-id :udm.field-types/id)
(s/def :party-relationship/relationship-name :udm.field-types/name)
(s/def :party-relationship/security-group-id :udm.field-types/id)
(s/def :party-relationship/priority-type-id :udm.field-types/id)
(s/def :party-relationship/party-relationship-type-id :udm.field-types/id)
(s/def :party-relationship/permissions-enum-id :udm.field-types/id)
(s/def :party-relationship/position-title :udm.field-types/name)
(s/def :party-relationship/comments :udm.field-types/comment)
(s/def :udm.party-entities/party-relationship (s/keys :opt [:party-relationship/party-id-from
                                                            :party-relationship/party-id-to
                                                            :party-relationship/role-type-id-from
                                                            :party-relationship/role-type-id-to
                                                            :party-relationship/from-date
                                                            :party-relationship/thru-date
                                                            :party-relationship/status-id
                                                            :party-relationship/relationship-name
                                                            :party-relationship/security-group-id
                                                            :party-relationship/priority-type-id
                                                            :party-relationship/party-relationship-type-id
                                                            :party-relationship/permissions-enum-id
                                                            :party-relationship/position-title
                                                            :party-relationship/comments]))


(s/def :party-relationship-type/party-relationship-type-id :udm.field-types/id)
(s/def :party-relationship-type/parent-type-id :udm.field-types/id)
(s/def :party-relationship-type/has-table :udm.field-types/indicator)
(s/def :party-relationship-type/party-relationship-name :udm.field-types/name)
(s/def :party-relationship-type/description :udm.field-types/description)
(s/def :party-relationship-type/role-type-id-valid-from :udm.field-types/id)
(s/def :party-relationship-type/role-type-id-valid-to :udm.field-types/id)
(s/def :udm.party-entities/party-relationship-type (s/keys :opt [:party-relationship-type/party-relationship-type-id
                                                                 :party-relationship-type/parent-type-id
                                                                 :party-relationship-type/has-table
                                                                 :party-relationship-type/party-relationship-name
                                                                 :party-relationship-type/description
                                                                 :party-relationship-type/role-type-id-valid-from
                                                                 :party-relationship-type/role-type-id-valid-to]))


(s/def :party-role/party-id :udm.field-types/id)
(s/def :party-role/role-type-id :udm.field-types/id)
(s/def :udm.party-entities/party-role (s/keys :opt [:party-role/party-id :party-role/role-type-id]))


(s/def :party-status/status-id :udm.field-types/id)
(s/def :party-status/party-id :udm.field-types/id)
(s/def :party-status/status-date :udm.field-types/date-time)
(s/def :party-status/change-by-user-login-id :udm.field-types/id-vlong)
(s/def :udm.party-entities/party-status (s/keys :opt [:party-status/status-id
                                                      :party-status/party-id
                                                      :party-status/status-date
                                                      :party-status/change-by-user-login-id]))


(s/def :party-type/party-type-id :udm.field-types/id)
(s/def :party-type/parent-type-id :udm.field-types/id)
(s/def :party-type/has-table :udm.field-types/indicator)
(s/def :party-type/description :udm.field-types/description)
(s/def :udm.party-entities/party-type (s/keys :opt [:party-type/party-type-id
                                                    :party-type/parent-type-id
                                                    :party-type/has-table
                                                    :party-type/description]))


(s/def :party-type-attr/party-type-id :udm.field-types/id)
(s/def :party-type-attr/attr-name :udm.field-types/id-long)
(s/def :party-type-attr/description :udm.field-types/description)
(s/def :udm.party-entities/party-type-attr (s/keys :opt [:party-type-attr/party-type-id
                                                         :party-type-attr/attr-name
                                                         :party-type-attr/description]))


(s/def :person/party-id :udm.field-types/id)
(s/def :person/salutation :udm.field-types/name)
(s/def :person/first-name :udm.field-types/name)
(s/def :person/middle-name :udm.field-types/name)
(s/def :person/last-name :udm.field-types/name)
(s/def :person/personal-title :udm.field-types/name)
(s/def :person/suffix :udm.field-types/name)
(s/def :person/nickname :udm.field-types/name)
(s/def :person/first-name-local :udm.field-types/name)
(s/def :person/middle-name-local :udm.field-types/name)
(s/def :person/last-name-local :udm.field-types/name)
(s/def :person/other-local :udm.field-types/name)
(s/def :person/member-id :udm.field-types/id)
(s/def :person/gender :udm.field-types/indicator)
(s/def :person/birth-date :udm.field-types/date)
(s/def :person/deceased-date :udm.field-types/date)
(s/def :person/height :udm.field-types/floating-point)
(s/def :person/weight :udm.field-types/floating-point)
(s/def :person/mothers-maiden-name :udm.field-types/long-varchar)
(s/def :person/old-marital-status :udm.field-types/indicator)
(s/def :person/marital-status-enum-id :udm.field-types/id)
(s/def :person/social-security-number :udm.field-types/long-varchar)
(s/def :person/passport-number :udm.field-types/long-varchar)
(s/def :person/passport-expire-date :udm.field-types/date)
(s/def :person/total-years-work-experience :udm.field-types/floating-point)
(s/def :person/comments :udm.field-types/comment)
(s/def :person/employment-status-enum-id :udm.field-types/id)
(s/def :person/residence-status-enum-id :udm.field-types/id)
(s/def :person/occupation :udm.field-types/name)
(s/def :person/years-with-employer :udm.field-types/numeric)
(s/def :person/months-with-employer :udm.field-types/numeric)
(s/def :person/existing-customer :udm.field-types/indicator)
(s/def :person/card-id :udm.field-types/id-long)
(s/def :udm.party-entities/person (s/keys :opt [:person/party-id
                                                :person/salutation
                                                :person/first-name
                                                :person/middle-name
                                                :person/last-name
                                                :person/personal-title
                                                :person/suffix
                                                :person/nickname
                                                :person/first-name-local
                                                :person/middle-name-local
                                                :person/last-name-local
                                                :person/other-local
                                                :person/member-id
                                                :person/gender
                                                :person/birth-date
                                                :person/deceased-date
                                                :person/height
                                                :person/weight
                                                :person/mothers-maiden-name
                                                :person/old-marital-status
                                                :person/marital-status-enum-id
                                                :person/social-security-number
                                                :person/passport-number
                                                :person/passport-expire-date
                                                :person/total-years-work-experience
                                                :person/comments
                                                :person/employment-status-enum-id
                                                :person/residence-status-enum-id
                                                :person/occupation
                                                :person/years-with-employer
                                                :person/months-with-employer
                                                :person/existing-customer
                                                :person/card-id]))


(s/def :priority-type/priority-type-id :udm.field-types/id)
(s/def :priority-type/description :udm.field-types/description)
(s/def :udm.party-entities/priority-type (s/keys :opt [:priority-type/priority-type-id :priority-type/description]))


(s/def :role-type/role-type-id :udm.field-types/id)
(s/def :role-type/parent-type-id :udm.field-types/id)
(s/def :role-type/has-table :udm.field-types/indicator)
(s/def :role-type/description :udm.field-types/description)
(s/def :udm.party-entities/role-type (s/keys :opt [:role-type/role-type-id
                                                   :role-type/parent-type-id
                                                   :role-type/has-table
                                                   :role-type/description]))


(s/def :role-type-attr/role-type-id :udm.field-types/id)
(s/def :role-type-attr/attr-name :udm.field-types/id-long)
(s/def :role-type-attr/description :udm.field-types/description)
(s/def :udm.party-entities/role-type-attr (s/keys :opt [:role-type-attr/role-type-id
                                                        :role-type-attr/attr-name
                                                        :role-type-attr/description]))


(s/def :vendor/party-id :udm.field-types/id)
(s/def :vendor/manifest-company-name :udm.field-types/name)
(s/def :vendor/manifest-company-title :udm.field-types/name)
(s/def :vendor/manifest-logo-url :udm.field-types/url)
(s/def :vendor/manifest-policies :udm.field-types/very-long)
(s/def :udm.party-entities/vendor (s/keys :opt [:vendor/party-id
                                                :vendor/manifest-company-name
                                                :vendor/manifest-company-title
                                                :vendor/manifest-logo-url
                                                :vendor/manifest-policies]))


