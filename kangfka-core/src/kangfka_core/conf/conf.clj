(ns kangfka-core.conf.conf
    (:require [clojure.java.io :as io]))

(def data-file (io/resource "data.txt"))
