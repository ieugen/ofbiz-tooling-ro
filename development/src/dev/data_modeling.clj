(ns dev.data-modeling
  (:require [malli.core :as m]
            [malli.generator :as mg]
            [malli.dot :as md]
            [udm.field-types :as ft]
            [udm.core :as udm]))

(mg/generate keyword?)

(mg/generate [:enum "a" "b" "c"])

(mg/generate pos-int? {:seed 10 :size 100})

(mg/generate ft/id)

(mg/generate ft/domain-name)

(mg/generate udm/Person)

(spit "person.dot" (md/transform udm/Person))

