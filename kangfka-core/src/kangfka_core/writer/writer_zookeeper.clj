(ns kangfka-core.writer.writer_zookeeper
  (require [kangfka-core.conf.conf :as conf]))

(defn- create_key [topic partition]
  [topic partition])

(def cache {})

(defn- put [node topic partition node address port]
  (let [key (create_key topic partition)]
           (assoc cache key {:node node :address address :port port})))

(defn- has_node [topic partition]
  (let [node (get cache (create_key topic partition))]
            (if nil? node false true)))

(defn- get_node_from_zookeper [topic partition]
  ; TODO ask it from zookeper and put node info to cache
  {:node "" :address "" :port ""})

(defn- is_mine [{:keys node}]
  (= node conf/node))

(defn- get_owner_node [topic partition]
  (if has_node [topic partition]
               (get cache (create_key topic partition))
               (get_node_from_zookeper topic partition)))

(defn is_my_topic [topic partition]
  (let [node (get_owner_node topic partition)]
            (is_mine node)))