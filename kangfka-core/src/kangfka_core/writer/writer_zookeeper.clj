(ns writer_zookeeper
    (require [kangfka-core.conf.conf :as conf]))

(defn create_key [topic partition]
      [topic partition])

(defn cache {})

(defn put [node topic partition node address]
      (let key (create_key topic partition)
               (assoc cache key {:node node :address address})))

(defn has_node [topic partition]
      (let node (get cache (create_key topic partition))
           (if nil? node false true)))

(defn get_node_from_zookeper [topic partition]
      ; TODO ask it from zookeper and put node info to cache
      {:node "" :address ""})

(defn is_i [{:keys node}]
  (= node conf/node))

(def get_owner_node [topic partition]
  (if has_node [topic partition]
               (def node (get cache (create_key topic partition)))
               (def node (get_node_from_zookeper topic partition)))
  node)

(def is_my_topic [topic partition]
  (let node (get_owner_node topic partition)
            (is_i node)))