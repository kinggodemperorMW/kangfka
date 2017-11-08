(ns tutorial2.core
  (:require [clojure.tools.cli :refer [parse-opts]])
  (:require [tutorial2.topic :as topic])
  (:require [tutorial2.fileIO :as io])
  (:gen-class))

(defn write-message-to-topic [topic msg]

  ; 토픽 정보 가져오기
  (def topic-metadata (topic/metadata topic))

  (if (empty? topic-metadata) 
    (topic/new-topic topic) ; 원하는 토픽 정보가 없으면 새로 생성
    (io/append-msg (get (nth topic-metadata 0) :path) msg))) ; 있으면 메시지 쓰기

(defn read-topic-data [topic]

  (def topic-metadata (topic/metadata topic))

  (if (empty? topic-metadata) 
    (println "(err: no data)")
    (io/read-file (get (nth topic-metadata 0) :path))))


(def cli-options
  [["-w" "--write"]
   ["-t" "--topic TOPIC"]
   ["-m" "--message MESSAGE"]
   ["-r" "--read"]
   ["-h" "--help"]])

(defn -main [& args]
  (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)]
    (cond
      (:help options) (println summary)
      (:write options) (write-message-to-topic (:topic options) (:message options))
      (:read options) (read-topic-data (:topic options))
      :else (println options)
      )
    )
  )


