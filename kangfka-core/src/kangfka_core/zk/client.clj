(ns kangfka-core.zk.client
  (:require [zookeeper :as zk]))

(def connect-string "127.0.0.1:2181")

; (def client (zk/connect connect-string))

(defstruct node :ip :port)

(defstruct topic :name :partition)

(defn init [client]
  (do
    (zk/create client "/nodes" :persistent? true)
    (zk/create client "/topics" :persistent? true)
    (zk/create client "/consumers" :persistent? true)))

(defn add-node [client node-list]
  (doseq [node node-list]
    (do
      (let [version (:version (zk/exists client "/nodes"))]
        (zk/set-data client "/nodes" (.getBytes node "UTF-8") version)))))

(defn add-topic [client topic-list]
  (doseq [topic topic-list]
    (do
      (let [version (:version (zk/exists client "/topics"))]
        (zk/set-data client "/topics" (.getBytes topic "UTF-8") version)))))
