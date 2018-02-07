(ns kangfka-core.zk.client
  (:require [zookeeper :as zk])
  (:require [zookeeper.data :as data])
  (:require [clojure.data.json :as json]))

; 외부에서 config 파일로 받아야 함
(def connect-string "127.0.0.1:2181")

(def client (zk/connect connect-string))

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
     (zk/create-all client path))))

(defn get-nodes [client]
  (zk/children client "/nodes"))

(defn add-node [client node-list]
  (doseq [node node-list]
    (do
      (let [hostname (:hostname node)
            node-dir-name (str "/nodes/" hostname)]
        (make-dir client node-dir-name)
        (zk/set-data
          client
          node-dir-name
          (data/to-bytes (json/write-str node))
          (get-dir-version client node-dir-name))))))

(defn add-partition [client topic]
  (let [topic-name (:name topic)
        partition-size (:partition topic)
        node-list (get-nodes client)
        node-list-count (count node-list)]
    (doseq [partition-id (range 0 partition-size)]
      (let [partition-dir-name (str "/topics/" topic-name "/" partition-id)]
        (make-dir client partition-dir-name)
        (zk/set-data
         client
         partition-dir-name
         (.getBytes (str (nth node-list (mod partition-id node-list-count))))
         (get-dir-version client partition-dir-name))))))

(defn add-topic [client topic-list]
  (doseq [t topic-list]
    (add-partition client t)))
