(ns udm.core
  (:require [udm.field-types :as ft]))

(def Person [:map
             [::id ft/id]
             [::salutation string?]
             [::first-name string?]
             [::last-name string?]])
