(ns dev.generators-at-work
  (:require [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [mundaneum.query      :as wq]
            [mundaneum.properties :as wp]))

(wq/entity "James Joyce")
(wp/wdt :author)

(def property 
  (prop/for-all [v (gen/vector gen/small-integer)]
                (let [s (sort v)]
                  (and (= (count v) (count s))
                       (or (empty? s)
                           (apply <= s))))))

(tc/quick-check 100 property)