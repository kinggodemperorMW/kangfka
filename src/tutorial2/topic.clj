(ns tutorial2.topic
    (:require [clojure.java.io :as io])
    (:require [clojure.data.json :as json]))

; 데이터들이 저장될 기본 경로 반환 
(def meta-path "./.topic")
(defn data-path [topic]
  (str "./topics/" topic))

; get topic metadata
(def read-meta-file (json/read-str (slurp meta-path) :key-fn keyword))

; topic metadata에서 name를 갖는 topic 정보를 반환
(defn metadata [name] 
  (for [row read-meta-file :when (= (get row :name) name)] row))

; topic metadata에 새로운 토픽 추가
(defn new-topic [topic]
  (with-open [w (io/writer meta-path :encoding "UTF-8" :append false)]
    (.write w (json/write-str (conj read-meta-file {:name topic :path (data-path topic)})))
    (.close w)))

; topic 파일 생성 
; (defn new-topic [topic]
;   (with-open [w (io/writer  (data-path topic) :encoding "UTF-8" :append true)]
;     (.close w))
;   )