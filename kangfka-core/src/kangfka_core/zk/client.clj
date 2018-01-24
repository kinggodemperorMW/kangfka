(ns kangfka-core.zk.client
  (:require [zookeeper :as zk])
  (:require [zookeeper.data :as data])
  (:require [clojure.data.json :as json]))

(def connect-string "127.0.0.1:2181")

; (def client (zk/connect connect-string))

(defstruct node :hostname :ip :port)

(defstruct topic :name :partition)

(defn init [client]
  (do
    (zk/create client "/nodes" :persistent? true)
    (zk/create client "/topics" :persistent? true)
    (zk/create client "/consumers" :persistent? true)))

(defn get-dir-version [client path]
  (:version (zk/exists client path)))

(defn make-dir [client path]
  (let [version (get-dir-version client path)]
   (if (nil? version)
     (zk/create client path))))

(defn add-node [client node-list]
  (doseq [node node-list]
    (do
      (let [hostname (:hostname node)
            node-dir-name (str "/nodes/" hostname)]
        (make-dir client node-dir-name)
        (zk/set-data client node-dir-name (data/to-bytes (json/write-str node)) (get-dir-version client node-dir-name))))))

(defn add-topic [client topic-list]
  (doseq [topic topic-list]
    (do
      (let [topic-name (:name topic)
            topic-dir-name (str "/topics/" topic-name)]
        (make-dir client topic-dir-name)
        (zk/set-data client topic-dir-name (data/to-bytes (json/write-str topic)) (get-dir-version client topic-dir-name))))))
