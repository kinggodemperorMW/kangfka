(ns kangfka_core.message_storage.message_storage
    (:require [clojure.string :refer :all])
    (:require [clojure.data.json :as json])
    (:require [kangfka_core.conf.conf :as conf]))

(defn read-topic-meta-data [path]
      (let [metaJson (slurp path)]
           (json/read-str metaJson :key-fn keyword)))

(defn get-topic [topic-meta topic]
      (get topic-meta (keyword topic)))

(defn has-topic [topic]
      (let [meta (read-topic-meta-data conf/topic-meta-data-path)]
           (not (blank? (get (get-topic meta topic) :path)))))

(defn write-topic-meta-data [origin-topic-meta path topic]
      (let [topic-meta (json/write-str (assoc origin-topic-meta (keyword topic) {:name topic :path (str conf/root-topic-path topic)}))]
           (spit conf/topic-meta-data-path topic-meta)))

(defn get-topic-path [topic]
      (let [meta (read-topic-meta-data conf/topic-meta-data-path)]
           (def topic-path (get (get meta (keyword topic)) :path))
           (if (blank? topic-path)
               (def topic-path (str conf/root-topic-path topic)))
           (write-topic-meta-data meta topic-path topic))
      topic-path)

(defn write [{:keys [topic message]}]
      (if (some blank? [topic message])
          (throw (IllegalArgumentException. "Invalid param")))

      (let [topic-path (get-topic-path topic)]
           (spit topic-path (str message "\n") :append true))

      {:topic topic :message message})

(defn is-valid-from-and-to [from to]
      (def valid-from-and-to true)
      (if (or (and (>= from  0) (< to 0)) (< from to))
          (def valid-from-and-to false))
      valid-from-and-to)

(defn read [{:keys [topic from to] :or {from -1 to -1}}]
      (if (not (is-valid-from-and-to from to))
          (throw (IllegalArgumentException. "Invalid from and to")))

      (def topic-messages nil)
      (if (has-topic topic)
          (let [topic-path (get-topic-path topic)]
               (def topic-messages
                   (split-lines (slurp (str topic-path))))))

      topic-messages)
