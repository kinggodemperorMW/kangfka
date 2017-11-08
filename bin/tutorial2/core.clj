(ns tutorial2.core
  (:require [clojure.tools.cli :refer [parse-opts]])
  (:require [clojure.java.io :as io])
  (:require [clojure.data.json :as json])
  (:gen-class))

; get topic metadata
(def get-topic-meta (json/read-str (slurp "./.topic") :key-fn keyword))

; write topic metadata
(defn write-topic-meta [topic]
  (with-open [w (io/writer "./.topic" :encoding "UTF-8" :append false)]
    (.write w (json/write-str {:name topic :path (str "./" topic)}))
    (.write w "\n")
    (.close w)
    )
  )

; topic 파일 생성 
(defn create-topic [topic]
  (with-open [w (io/writer  (str "./" topic) :encoding "UTF-8" :append true)]
    (.close w)
    )
  )

; topic 파일에 메시지 추가 
(defn c-write-message-to-topic [topic msg]
  (with-open [w (io/writer (str "./" topic) :append true)]
    (.write w msg)
    (.close w)))

(defn read-file [topic]
  (with-open [r (io/reader (str "./" topic))]
    (doseq [line (line-seq r)]
      (println line))
    (.close r)))

(defn write-message-to-topic [topic msg]
  (println (str topic)) (println (str msg)) (println (str "./" topic))
  (println get-topic-meta)
  )

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
      (:write options)
      (write-message-to-topic (:topic options) (:message options))
      (:read options) 
      (read-file (:topic options))
      :else (println options)
      )
    )
  )


