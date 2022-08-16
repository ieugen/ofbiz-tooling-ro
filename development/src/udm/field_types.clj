(ns udm.field-types
  (:require [clojure.spec.alpha :as s]))

;; (def id [:and
;;          [:re #"^[a-z0-9A-Z][a-z0-9A-Z-_]{0,20}$"]
;;          :string
;;          {:min 1, :max 20}])

;; (def domain-name [:and [:re #"^[a-z0-9A-Z][a-z0-9A-Z-_]{0,20}$"] :string])

(s/def ::date-time #(instance? java.time.LocalDate %))
(s/def ::date #(instance? java.time.LocalDate %))
(s/def ::time #(instance? java.time.LocalTime %))

(s/def ::integer int?)

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
