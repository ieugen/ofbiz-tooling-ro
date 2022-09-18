(ns user
  (:require [portal.api :as p]
            [hyperfiddle.rcf]))

;; (def portal (p/open))
;; (def p (p/open {:launcher :vs-code}))
;; (add-tap #'p/submit)

(set! *warn-on-reflection* true)
(hyperfiddle.rcf/enable!)
