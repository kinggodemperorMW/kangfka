(ns api-test.core
  (:use [ring.util.response])
  (:require [ring.adapter.jetty :as ring]
           [ring.middleware.json :as middleware]
           [compojure.core :refer :all]
           [compojure.route :as route]
           [api-test.reader :as reader]
           [api-test.writer :as writer]
           [cheshire.core :as json])
  (:gen-class))
;
;(defn what-is-my-ip
;  ([request]
;   {:status 200
;    :headers {"Content-Type" "text/plain"}
;    :body (:remote-addr request)})
;  ([request respond raise]
;   (respond (what-is-my-ip request))))

(defroutes app-routes
           (GET "/" request (str request))

           (GET "/message/:topic" [topic]
             (response (reader/read-file topic)))

           (POST "/message/:topic" {params :params
                                    body :body}
             (let [message-data (str "<h1>" (get params :topic) "</h1><br>message: <br>" body)
                   content (get body "body")
                   topic (get params :topic)]
               ;(println body)
               ;(println topic)
               ;(println message-data)
               (writer/write-file message-data topic)
               (response message-data)
               ))

            (GET "/test" request
              (println (get-in request [:query-string]))
              ;(println (keywordize-keys (form-decode "hello=world&foo=bar")))
              (let [name (or (get-in request [:params :name])
                             (get-in request [:body :name])
                             "John Doe")]
                {:status 200
                 :body {:name name
                        :desc (str "The name you sent to me was " name)}}))
            (route/resources "/")

           (route/not-found "404"))


(def app
  (-> (middleware/wrap-json-body app-routes {:keyword true})
      (middleware/wrap-json-response)))

(defn -main [& args]
  (ring/run-jetty app {:port 3001}))