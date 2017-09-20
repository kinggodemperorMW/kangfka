(ns tutorial2.core
  (:require [tutorial2.utils :as utils])
  (:require [tutorial2.router :as router])
  (:require [clojure.tools.cli :refer [cli]])
  (:require [clojure.java.io :as io])
  (:require [compojure.core :as compojure]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]])
  (:gen-class))

(def wrapped-app (-> router/myroute
                     (wrap-defaults site-defaults)))

(defn -main [& [args]]
  (jetty/run-jetty #'wrapped-app {:join? false :port (utils/parse-int args)}))