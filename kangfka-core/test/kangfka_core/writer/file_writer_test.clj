(ns kangfka-core.writer.file-writer-test
    (require [clojure.test :refer :all]
             [kangfka-core.writer.file-writer :refer :all]
             [kangfka-core.conf.conf :as conf]
             [clojure.java.io :as io]))

(def test_topic "test_topic")
(def test_partition "0")
; 왜 Long -> String으로 전환이 되지 않는가?
;(def test_partition 0)
(def test_message "test_message")

(defn create-file-path [topic partition]
      (str conf/root-topic-path "/" topic "/" partition))

(defn remove-write-test-file [f]
      (def test-file-path (create-file-path test_topic test_partition))
      (if (.exists (io/file test-file-path))
          (io/delete-file test-file-path))
      (f))

(defn read-messages []
      (let [file-path (create-file-path test_topic test_partition)]
           (slurp file-path)))

(deftest test-get-topic-path-when-success
    (is (= (get-topic-path "name" 0) "/root/name/0")))

(deftest test-write-when-topic-is-empty
         (def empty_topic "")
         (is (thrown-with-msg? IllegalArgumentException #"Invalid param" (write empty_topic test_partition test_message))))

(deftest test-write-when-partition-is-empty
         (def empty_partition nil)
         (is (thrown-with-msg? IllegalArgumentException #"Invalid param" (write test_topic empty_partition test_message))))

(deftest test-write-when-message-is-empty
         (def empty_message "")
         (is (thrown-with-msg? IllegalArgumentException #"Invalid param" (write test_topic test_partition empty_message))))


(deftest test-write-when-write-message
         (testing "메세지 한 개 파일 쓰기 성공 시나리오"
                  (write test_topic test_partition test_message)
                  (def actual_result (read-messages))
                  (def expected_result test_message)
                  (is (= actual_result expected_result))))

(use-fixtures :each remove-write-test-file)