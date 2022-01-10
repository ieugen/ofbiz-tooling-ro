(ns user
  (:require [portal.api :as p]))

(def portal (p/open))

(add-tap #'p/submit)


