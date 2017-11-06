(ns kangfka.core
  (:use [clojure.java.io :as io]))

(defn get-topic-folder-path
  [topic]
  (let [folder-path (str (System/getProperty "user.dir") "/local-file/" topic ".kangfka")]
    (println (str "[topic] " topic " => " folder-path))
    folder-path))

(defn create-topic-folder
  [topic]
  (let [topic-folder-path (get-topic-folder-path topic)
        topic-folder (io/file topic-folder-path)]
    (if (.exists (io/file topic-folder))
      (println (str topic-folder-path " already exists."))
      (.mkdirs topic-folder))))

(defn put [topic message]
  (create-topic-folder topic)
  (let [wrtr (io/writer (str (get-topic-folder-path topic) "/0") :append true)]
    (.write wrtr message)
    (.close wrtr)))

(defn read-messages [topic]
  (println (reader (map #(.getAbsolutePath %) (filter #(.isFile %) (file-seq (io/as-file (str (get-topic-folder-path topic) "/"))))))))
  ;CompilerException java.lang.IllegalArgumentException: Cannot open <("/Users/naver/dev/github/kangfka/local-file/hi.kangfka/0")> as an InputStream.
  ;코드가 뭔가 이상하다;;

(put "hi" "kkk")

(read-messages "hi")