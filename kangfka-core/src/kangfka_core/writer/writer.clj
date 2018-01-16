(ns kangfka-core.writer.writer
  (require [kangfka-core.writer.file_writer :as file_writer]
           [kangfka-core.writer.writer_zookeeper :refer :all]))

(defn write [{:keys [topic partition message] :or {partition 0}}]
  (file_writer/write topic partition message))