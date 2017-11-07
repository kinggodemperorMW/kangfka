(ns kangfka-web.core
  (use [ring.util.response])
  (require [ring.adapter.jetty :as ring]
           [ring.middleware.json :as middleware]
           [compojure.core :refer :all]
           [compojure.route :as route]
           [kangfka-core.core :as core])
  (:gen-class))

(defroutes app-routes
  (GET "/" [] "kangfka")
  (GET "/messages/:topic" [topic]
    "message")
  ; (POST "/message" {body :body}
  ;   (let [message (message/wrap-message (get body "topic") (get body "message"))
  ;         res (assoc body "timestamp" (:timestamp message))]
  ;     (writer/write ((:to-string message)) (utils/data-file (:topic message)))
  ;     (response res)))
  (route/not-found "404"))

(def app
  (-> (middleware/wrap-json-body app-routes {:keyword true})
      (middleware/wrap-json-response)))

(defn -main [& args]
  (ring/run-jetty app {:port 3000}))
