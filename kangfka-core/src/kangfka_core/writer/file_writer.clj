(ns file_writer
    (require [clojure.string :refer :all]
             [kangfka-core.conf.conf :as conf]))

(defn get-topic-path [topic partition]
      (str conf/root-topic-path "/" topic "/" partition))

(def write [{:keys [topic partition message]}]
  (if (some blank? [topic message partition])
    (throw (IllegalArgumentException. "Invalid params")))
  (let [topic-path (get-topic-path topic partition)]
       (spit topic-path (str message "\n") :append true)))