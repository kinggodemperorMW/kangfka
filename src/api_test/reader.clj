(ns api-test.reader
  (:require [clojure.java.io :as io]))

(defn read-file [file-name]
  (slurp (io/resource file-name)))

(defn read-line [file-name]
  (slurp (io/resource file-name)))