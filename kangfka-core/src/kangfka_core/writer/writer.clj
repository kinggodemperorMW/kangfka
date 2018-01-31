(ns kangfka-core.writer.writer
  (require [kangfka-core.writer.file-writer :as file-writer]
           [kangfka-core.writer.writer-zookeeper :refer :all]))

(defn write [{:keys [topic partition message] :or {partition 0}}]
  (file-writer/write topic partition message))
