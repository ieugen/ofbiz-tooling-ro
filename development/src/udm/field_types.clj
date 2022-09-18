(ns udm.field-types
  (:require [clojure.spec.alpha :as s]))

;; (def id [:and
;;          [:re #"^[a-z0-9A-Z][a-z0-9A-Z-_]{0,20}$"]
;;          :string
;;          {:min 1, :max 20}])

;; (def domain-name [:and [:re #"^[a-z0-9A-Z][a-z0-9A-Z-_]{0,20}$"] :string])

(s/def ::blob #(instance? java.sql.Blob %))
(s/def ::byte-array bytes?)
(s/def ::object #(instance? java.lang.Object %))
(s/def ::date-time #(instance? java.time.LocalDate %))
(s/def ::date #(instance? java.time.LocalDate %))
(s/def ::time #(instance? java.time.LocalTime %))

(s/def ::currency-amount #(instance? java.math.BigDecimal %))
(s/def ::currency-precise #(instance? java.math.BigDecimal %))
(s/def ::fixed-point #(instance? java.math.BigDecimal %))
(s/def ::floating-point double?)
(s/def ::integer integer?)
(s/def ::numeric int?)

(s/def ::id string?)
(s/def ::id-long string?)
(s/def ::id-vlong string?)

(s/def ::indicator string?)
(s/def ::very-short string?)
(s/def ::short-varchar string?)
(s/def ::long-varchar string?)
(s/def ::very-long string?)

(s/def ::comment string?)
(s/def ::description string?)
(s/def ::name string?)
(s/def ::value string?)

(s/def ::credit-card-number string?)
(s/def ::credit-card-date string?)
(s/def ::email string?)
(s/def ::url string?)
(s/def ::tel-number string?)
