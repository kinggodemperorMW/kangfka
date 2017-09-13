(ns clojure-test.core
  (:require [clojure.tools.cli :refer [parse-opts]])
  (:require [clojure.java.io :as io]))

(def cli-options
  [["-m" "--message MESSAGE" ""]
   ["-r" "--read"]
   ["-h" "--help"]])

(def data-file (io/resource "message.txt" ))

(def get-string-from-file (slurp data-file))

(defn write-string-to-file [message]
  (spit data-file (str message "\n") :append true))

(defn -main [& args]
  (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)]
    (cond
      (:help options) (println summary)
      (:message options)
      (write-string-to-file (:message options))
      (:read options) (println get-string-from-file)
      :else (println options))))
