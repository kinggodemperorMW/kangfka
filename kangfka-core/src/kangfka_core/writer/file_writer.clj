(ns kangfka-core.writer.file-writer
    (require [clojure.string :refer [blank?]]
             [kangfka-core.conf.conf :as conf]))

(defn get-topic-path
  ([topic partition] (get-topic-path topic partition conf/root-topic-path))
  ([topic partition root-path]
   (str root-path "/" topic "/" partition)))

(defn write [topic partition message]
  (if (some blank? [topic partition message])
    (throw (IllegalArgumentException. "Invalid params")))
  (let [topic-path (get-topic-path topic partition)]
       (spit topic-path (str message "\n") :append true)))
