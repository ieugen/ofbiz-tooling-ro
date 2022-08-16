(ns dev.entity2spec
  "Convert OFBiz entities to clojure specs."
  (:require [clojure.spec.alpha :as s]
            [clojure.java.io :as io]
            [riveted.core :as vtd]))

