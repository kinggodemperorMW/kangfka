(ns kangfka.messagestorage.messageStorage
  (:require [clojure.string :refer [blank?]])
  (:require [clojure.data.json :as json]))

(def ^:const topic-meta-data-path "resources/meta/topic-meta-data")
(def ^:const root-topic-path "resources/topic/")

(defn read-topic-meta-data [path]
  (let [metaJson (slurp path)]
    (json/read-str metaJson :key-fn keyword)))

(defn get-topic [topic-meta topic]
  (get topic-meta (keyword topic)))

(defn has-topic [path topic]
  (let [meta (read-topic-meta-data topic-meta-data-path)]
    (blank? (get (get-topic meta topic) :path))))

(defn write-topic-meta-data [path topic]
  (let [topic-meta (json/write-str {:name topic :path (str topic-meta-data-path topic)})]
    (spit topic-meta-data-path topic-meta)))

(defn get-topic-path [topic]
  (let [meta (read-topic-meta-data topic-meta-data-path)]
    (def topic-path (get (get meta (keyword topic)) :path))
    (if (blank? topic-path)
      (def topic-path (str root-topic-path topic)))
    (write-topic-meta-data topic-meta-data-path topic-path))
  topic-path)

(defn write [{:keys [topic message]}]
  (if (some blank? [topic message])
    (throw (IllegalArgumentException. "Invalid param")))

  (let [topic-path (get-topic-path topic)]
    (spit topic-path message :append true))

  {:topic topic :message message})

(defn read [topic]
  (def topic-messages nil)
  (if (has-topic topic-meta-data-path topic)
    (let [topic-path (get-topic-path topic)]
      (def topic-messages (slurp (str topic-path)))))
  topic-messages)