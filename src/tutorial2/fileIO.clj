(ns tutorial2.fileIO
    (:require [clojure.java.io :as io]))

; 파일에 메시지 추가 
(defn append-msg [path msg]
    (with-open [w (io/writer path :append true)] 
      (.write w msg) 
        (.close w)))
  
; 파일 전체 출력 
(defn read-file [path]
(with-open [r (io/reader path)]
    (doseq [line (line-seq r)]
    (println line))
    (.close r)))