(ns kangfka-core.writer.writer-zookeeper
  (require [kangfka-core.conf.conf :as conf]))

(defn- create-key [topic partition]
  [topic partition])

(def cache {})

(def zookeeper-function)

(defn set-zookeeper-function [function]
  (def zookeper-function function))

(defn- put [node topic partition node address port]
  (let [key (create-key topic partition)]
       (assoc cache key {:node node :address address :port port})))

(defn- has-node [topic partition]
  (let [node (get cache (create-key topic partition))]
       (if (nil? node) false true)))

(defn- get-node-from-zookeper [topic partition function]
  ; TODO ask it from zookeper and put node info to cache
  {:node "" :address "" :port ""})

(defn- is-mine [{:keys node}]
  (= node conf/node))

(defn- get-owner-node [topic partition]
  (if has-node [topic partition]
               (get cache (create-key topic partition))
               (get-node-from-zookeper topic partition zookeeper-function)))

(defn is-my-topic [topic partition]
  (let [node (get-owner-node topic partition)]
       (is-mine node)))
