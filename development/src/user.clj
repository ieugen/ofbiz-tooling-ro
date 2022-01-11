(ns user
  (:require [portal.api :as p]))

;; (def portal (p/open))

(def p (p/open {:launcher :vs-code}))

(add-tap #'p/submit)


