(ns kangfka-core.zk.client-test
  (:use [zookeeper]
        [clojure.test]
        [kangfka-core.zk.client])
  (:import [org.apache.curator.test TestingServer])
  (:require [clojure.data.json :as json]))

(defn setup-embedded-zk [f]
  (let [server (TestingServer. 2181)]
    (do (f)
        (.close server))))

(use-fixtures :once setup-embedded-zk)

(deftest init-test
  (let [client (connect connect-string)
        nodes-directory "/nodes"
        topics-directory "/topics"
        consumers-directory "/consumers"]

    (init client)

    (is (not= nil (exists client nodes-directory)))
    (is (not= nil (exists client topics-directory)))
    (is (not= nil (exists client consumers-directory)))))

(deftest add-node-test
  (let [client (connect connect-string)]

    (init client)

    (def node1 (struct node "hostname" "127.0.0.1" 1234))
    (def node-list [node1])
    (add-node client node-list)
    (is (= (json/write-str node1) (String. (:data (data client "/nodes/hostname")))))))

(deftest add-topic-test
  (let [client (connect connect-string)]

    (init client)

    (add-topic client ["topic1"])
    (is (= "topic1" (String. (:data (data client "/topics")))))))
