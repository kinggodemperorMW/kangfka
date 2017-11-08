;(ns api-test.handler
;  (:require [compojure.core :refer :all]
;            [compojure.handler :as handler]
;            [compojure.route :as route]
;            [ring.middleware.json :as middleware]))
;
;;(defroutes app-routes
;;  (GET "/" [] "Hello World")
;;  (route/not-found "Not Found"))
;;
;;(def app
;;  (wrap-defaults app-routes site-defaults))
;
;(defroutes app-routes
;           (POST "/" request
;             (let [name (or (get-in request [:params :name])
;                            (get-in request [:body :name])
;                            "John Doe")]
;               {:status 200
;                :body {:name name
;                       :desc (str "The name you sent to me was " name)}}))
;           (route/resources "/")
;           (route/not-found "Not Found"))
;
;(def app
;  (-> (handler/site app-routes)
;      (middleware/wrap-json-body {:keywords? true})
;      middleware/wrap-json-response))
;
;;; The {:keywords? true} above makes sure that when my body is parsed,
;;; I get my keys as keywords and not as strings.