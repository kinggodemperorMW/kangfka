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
  (POST "/admin" {body :body}
    (let [action (get body "action"))]
      (response action))
  (GET "/messages/:topic" [topic]
    "message")
  (route/not-found "404"))

(def app
  (-> (middleware/wrap-json-body app-routes {:keyword true})
      (middleware/wrap-json-response)))

(defn -main [& args]
  (ring/run-jetty app {:port 3000}))
