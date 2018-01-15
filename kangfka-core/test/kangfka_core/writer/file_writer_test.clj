(ns kangfka-core.writer.file_writer_test
    (require [clojure.test :refer :all]
             [kangfka-core.writer.file-writer :refer :all]))



(defn testfoo [{:keys [aaa] :or {aaa 10}}]
    aaa)

(deftest foo-test
    (is (= (testfoo {}) 10)))
    
(deftest get-topic-path-test
    (is (= (get-topic-path "name" 0) "/root/name/0")))