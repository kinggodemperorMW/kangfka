(ns kangfka.reader.reader
  (:require [kangfka.conf.conf :as conf]))

(defn read [& {:keys [file-path file-name] :or {file-path conf/default-file-path file-name conf/default-file-name}}]
  (slurp (str file-path "/" file-name)))

