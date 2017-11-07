(ns kangfka-core.writer.writer)

(defn write [message data-file]
    (spit data-file (str message "\n") :append true))