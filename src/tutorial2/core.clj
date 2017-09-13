(ns tutorial2.core
  (:require [clojure.tools.cli :refer [cli]])
  (:require [clojure.java.io :as io])
  (:gen-class))

(defn write-file [s]
  (with-open [w (io/writer  "./out.txt" :append true)]
    (.write w s)
    (.close w)))

(defn read-file []
  (with-open [r (io/reader "./out.txt")]
    (doseq [line (line-seq r)]
      (println line))
    (.close r)))

(defn -main [& [args]]
  (if args (write-file args))
  (read-file))

;(defn -main [& args]
;  (let [[opts args banner] (cli args
;                                ["-h" "--help" "Print this help"
;                                 :default false :flag true]
;                                )]
;    (when (:help opts)
;      (println banner)))
;  (if args (println args)
;             ;if (= (get args 0) "write") ((println "hello"))
;             
;    )
;  )

