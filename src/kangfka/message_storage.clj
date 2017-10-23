(ns kangfka.core
  (:use [clojure.java.io :as io]))

;topic 에 새로운 메시지를 추가함 TODO
(defn put [topic message]
  (println topic))

;topic 에서 메시지를 읽음 TODO
(defn read-messages [topic])

;topic 파일이 없으면 생성함 TODO
(defn create-topic-file
  [topic]
  (let [topic-file-path (get-topic-file-path topic)]
    (if (.exists (io/file topic-file-path))
      (println (str topic-file-path " already exists."))
      (println (str topic-file-path " does not exist.")))))

;topic 파일 경로를 만듬
(defn get-topic-file-path
  [topic]
  (let [file-path (str (System/getProperty "user.dir") "/local-file/" topic ".kangfka")]
    (println (str "[topic] " topic " => " file-path))
    file-path))

(create-topic-file "hi")
