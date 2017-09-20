(ns tutorial2.router
  (:require [compojure.core :as compojure]))

(compojure/defroutes myroute
  (compojure/GET "/" [] {:body "Hello World!"
                         :status 200
                         :headers {"Content-Type" "text/plain"}})
  
  (compojure/GET "/user/:id" [id]
                 (str "Hello " id))
  
  (compojure/GET "/user/json/:id" [id]
                 {:body (str "{\"name\":\"" id "\"}")
                         :status 200
                         :headers {"Content-Type" "application/json"}}))

