(ns kangfka_core.message_storage.message_storage_test
    (:require [clojure.test :refer :all])
    (:require [kangfka_core.message_storage.message_storage :refer :all]
        [kangfka_core.conf.conf :as conf]))

(def ^:const path-for-read-meta "test_resources/meta/meta_data_for_read_meta_data")

(deftest write-test-when-some-param-is-blank
         (testing "일부 파라미터가 blank일 때"
                  (def test-consumer-name "testConsumerName")
                  (def empty-test-topic "")
                  (def test-message "testMessage")
                  (def test-message-container {:consumerName test-consumer-name :topic empty-test-topic :message test-message})
                  (is (thrown-with-msg? IllegalArgumentException #"Invalid param" (write test-message-container)))))

(deftest write-test-when-some-param-is-nil
         (testing "일부 파라미터가 nil일 때"
                  (def test-consumer-name "testConsumerName")
                  (def test-message "testMessage")
                  (def test-message-container {:consumerName test-consumer-name :topic nil :message test-message})
                  (is (thrown-with-msg? IllegalArgumentException #"Invalid param" (write test-message-container)))))

(deftest write-test
         (testing "key parse test"
                  (def test-consumer-name "testConsumerName")
                  (def test-topic "testTopic")
                  (def test-message "testMessage")
                  (def test-message-container {:consumerName test-consumer-name :topic test-topic :message test-message})
                  (def actual-result (write test-message-container))
                  (def expected-result {:topic test-topic :message test-message})
                  (is (= actual-result expected-result))))

(deftest write-topic-meta-data-test
         (testing "write topic meta data"
                  (def test-path "testPathForTestTopic3")
                  (def origin-topic-meta {:testTopic1 {"path" "testPath" "name" "testTopic1"}})
                  (def test-topic "testTopic3")
                  (write-topic-meta-data origin-topic-meta test-path test-topic)
                  (def actual-result (read-topic-meta-data conf/topic-meta-data-path))
                  (is (= actual-result {:testTopic1 {:path "testPath" :name "testTopic1"} :testTopic3 {:path "resources/topic/testTopic3" :name "testTopic3"}}))))

(deftest read-meta-data-test
         (testing "read meta data"
                  (def actual-result (read-topic-meta-data path-for-read-meta))
                  (def expected-result {:testK1 "testV1"})
                  (is (= actual-result expected-result))))

(deftest get-topic-path-test-when-exist-topic
         (testing "get topic path from meta when topic is exist"
                  (def topic "testTopic")
                  (def actual-result (get-topic-path topic))
                  (def expected-result "resources/topic/testTopic")
                  (is (= actual-result expected-result))))

(deftest get-topic-path-test-when-not-exist-topic
         (testing "get topic path from meta when topic is not exist"
                  (def topic "testTopicNotExist")
                  (def actual-result (get-topic-path topic))
                  (def expected-result "resources/topic/testTopicNotExist")
                  (is (= actual-result expected-result))))

(deftest get-topic-test-when-topic-is-exist
         (testing "get topic from topic meta map"
                  (def topic-meta {:test-topic-name "testTopic"})
                  (def actual-result (get-topic topic-meta "test-topic-name"))
                  (def expected-result "testTopic")
                  (is (= actual-result expected-result))))

(deftest get-topic-test-when-topic-is-not-exist
         (testing "get topic from topic meta map"
                  (def topic-meta {:test-topic-name "testTopic"})
                  (def actual-result (get-topic topic-meta "test-topic-another-name"))
                  (is (= actual-result nil))))

(deftest has-topic-test-when-topic-is-not-exist
         (testing "has topic test when topic is not exist"
                  (def not-exist-topic-name "not-exist-topic-name")
                  (def actual-result (has-topic not-exist-topic-name))
                  (def expected-result false)
                  (is (= actual-result expected-result))))

(deftest has-topic-test-when-topic-exist
         (testing "has topic test when topic is exist"
                  (def exist-topic-name "testTopic1")
                  (def actual-result (has-topic exist-topic-name))
                  (def expected-result true)
                  (is (= actual-result expected-result))))

(deftest read-test-when-topic-is-not-exist
         (testing "read test when topic is not exist"
                  (def topic-name "not-exist-topic-name")
                  (def actual-result (read topic-name))
                  (def expected-result nil)
                  (is (= actual-result expected-result))))

(deftest read-test-when-topic-is-exist
         (testing "read test when topic is exist"
                  (def topic-name "testTopic3")
                  (def actual-result (read topic-name))
                  (def expected-result "I have a message.")
                  (is (= actual-result expected-result))))

(deftest read-test-with-from-to-when-both-are-not-exist
         (testing "read test with from and to when both are not exist"
                  (def topic-name {:topic "testTopic3"})
                  (def actual-result (read topic-name))
                  (def expected-result ["I have a message1." "I have a message2." "I have a message3." "I have a message4." "I have a message5."])
                  (is (= actual-result expected-result))))

(deftest read-test-with-from-to-when-both-are-not-exist
         (testing "read test with from and to when both are not exist"
                  (def topic-name {:topic "testTopic3"})
                  (def actual-result (read topic-name))
                  (def expected-result ["I have a message1." "I have a message2." "I have a message3." "I have a message4." "I have a message5."])
                  (is (= actual-result expected-result))))


(deftest is-valid-from-and-to-test-when-from-and-to-are-less-than-0
         (testing "is valid from and to test when both are less than 0"
                  (def from -1)
                  (def to -1)
                  (def actual-result (is-valid-from-and-to from to))
                  (def expected-result true)
                  (is (= actual-result expected-result))))

(deftest is-valid-from-and-to-test-when-to-is-less-than-from
         (testing "is valid from and to test when to is less than from"
                  (def from 3)
                  (def to 2)
                  (def actual-result (is-valid-from-and-to from to))
                  (def expected-result false)
                  (is (= actual-result expected-result))))