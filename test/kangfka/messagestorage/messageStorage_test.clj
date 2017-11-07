(ns kangfka.messagestorage.messageStorage-test
  (:require [clojure.test :refer :all])
  (:require [kangfka.messagestorage.messageStorage :refer [write, read-topic-meta-data, get-topic-path, write-topic-meta-data, get-topic, has-topic]])
  (:require [clojure.java.io :as io]))

(def ^:const test-topic-meta-data-path "test-resources/meta/test-topic-meta-data")

(deftest write-test-when-some-param-is-blank
  (testing "일부 파라미터가 blank일 때"
    (def testConsumerName "testConsumerName")
    (def emptyTestTopic "")
    (def testMessage "testMessage")
    (def testMessageContainer {:consumerName testConsumerName :topic emptyTestTopic :message testMessage})
    (is (thrown-with-msg? IllegalArgumentException #"Invalid param" (write testMessageContainer)))))

(deftest write-test-when-some-param-is-nil
  (testing "일부 파라미터가 nil일 때"
    (def testConsumerName "testConsumerName")
    (def testMessage "testMessage")
    (def testMessageContainer {:consumerName testConsumerName :topic nil :message testMessage})
    (is (thrown-with-msg? IllegalArgumentException #"Invalid param" (write testMessageContainer)))))

(deftest write-test
  (testing "key parse test"
    (def testConsumerName "testConsumerName")
    (def testTopic "testTopic")
    (def testMessage "testMessage")
    (def testMessageContainer {:consumerName testConsumerName :topic testTopic :message testMessage})
    (def actualResult (write testMessageContainer))
    (def expectedResult (list testConsumerName testTopic testMessage))
    (is (= actualResult expectedResult))))

(deftest read-meta-data
  (testing "read meta data"
    (def actualResult (read-topic-meta-data test-topic-meta-data-path))
    (def expectedResult {:testK1 "testV1"})
    (is (= actualResult expectedResult))))

(deftest get-topic-path-when-exist-topic
  (testing "get topic path from meta when topic is exist"
    (def topic "testTopic")
    (def actualResult (get-topic-path topic))
    (def expectedResult "resources/topic/testTopic")
    (is (= actualResult expectedResult))))

(deftest get-topic-path-when-not-exist-topic
  (testing "get topic path from meta when topic is not exist"
    (def topic "testTopicNotExist")
    (def actualResult (get-topic-path topic))
    (def expectedResult "resources/topic/testTopicNotExist")
    (is (= actualResult expectedResult))))

(deftest get-topic-when-topic-is-exist
  (testing "get topic from topic meta map"
    (def topic-meta {:test-topic-name "testTopic"})
    (def actualResult (get-topic topic-meta "test-topic-name"))
    (def expectedResult "testTopic")
    (is (= actualResult expectedResult))))

(deftest get-topic-when-topic-is-not-exist
  (testing "get topic from topic meta map"
    (def topic-meta {:test-topic-name "testTopic"})
    (def actualResult (get-topic topic-meta "test-topic-another-name"))
    (is (= actualResult nil))))
