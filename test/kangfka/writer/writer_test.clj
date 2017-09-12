(ns kangfka.writer.writer-test
  (:require [clojure.test :refer :all])
  (:require [kangfka.writer.writer :refer :all])
  (:require [clojure.java.io :as io]))

(def ^:const test-file-path "test-resources/writer")
(def ^:const test-file-name "test-file.txt")

(defn remove-write-test-file [f]
  (def file-name-with-path (str test-file-path "/" test-file-name))
  (if (.exists (io/file file-name-with-path))
              (io/delete-file file-name-with-path))
  (f))

(deftest test-write-success-scenario
  (testing "파일 쓰기 성공 시나리오"
    (write "test-message!!!" :file-path test-file-path :file-name test-file-name)
    (def actual-contents (slurp (str test-file-path "/" test-file-name)))
    (def expected-content "test-message!!!")
    (is (= actual-contents expected-content))))

(use-fixtures :each remove-write-test-file)