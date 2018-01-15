(ns writer
  (require [kangfka-core.writer.file_writer :as file_writer]
           [kangfka-core.writer.write_delegator :refer :all]
           [kangfka-core.writer.writer_zookeeper :refer :all]))

(defn write [{:keys topic partition message}]
      (if (is_my_topic topic partition)
        (file_writer/write topic partition message)
        (let [node (get_owner_node topic partition)]
             (delegate node topic partition message))))