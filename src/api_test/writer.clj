(ns api-test.writer
  (:require [clojure.java.io :as io]))

(defn write-file [message file-name]
  (println "message :" message)
  (println "file-name :" file-name)
  (spit (io/resource file-name) (str message "\n") :append true))
