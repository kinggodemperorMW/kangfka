(ns kangfka-core.message.message)

(defn wrap-message
    ([topic message]
        (let [timestamp (System/currentTimeMillis)]
            {:topic topic
             :message message
             :timestamp timestamp
             :to-string (fn [] 
                (clojure.string/join "|" [topic message timestamp]))})))
                