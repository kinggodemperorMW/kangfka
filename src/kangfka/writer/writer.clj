(ns kangfka.writer.writer
  (:require [kangfka.conf.conf :as conf]))

(defn write [message & {:keys [file-path file-name] :or {file-path conf/default-file-path file-name conf/default-file-name}}]
  (with-open [w (clojure.java.io/writer (str file-path "/" file-name) :append true)]
    (.write w message)))