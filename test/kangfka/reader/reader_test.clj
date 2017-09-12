(ns kangfka.reader.reader-test
  (:require [clojure.test :refer :all])
  (:require [kangfka.reader.reader :refer [read]])
  (:import (java.io FileNotFoundException)))

(def ^:const correct-test-file-path "test-resources/reader")
(def ^:const correct-test-file-name "reader-test.txt")

(def ^:const empty-test-file-name "reader-test-empty.txt")

(def ^:const wrong-test-file-path "wrong-test-resources/reader")
(def ^:const wrong-test-file-name "wrong-reader-test.txt")


(deftest read-test-when-exist-file
  (testing "올바른 경로와 파일일 때"
    (def actual-contents (read :file-path correct-test-file-path :file-name correct-test-file-name))
    (def expected-contents "test-content1\n\ntest-content2\n\ntest-content3")
    (is (= actual-contents expected-contents))))

(deftest read-test-when-not-exist-file
  (testing "잘못된 경로와 파일일 때"
    (is (thrown? FileNotFoundException (read :file-path wrong-test-file-path :file-name wrong-test-file-name)))))

(deftest read-test-when-empty-file
  (testing "비어있는 파일일 때"
    (def actual-contents (read :file-path correct-test-file-path :file-name empty-test-file-name))
    (def expected-contents "")
    (is (= actual-contents expected-contents))))