(ns kangfka.web.core
  (:require [kangfka.server.runner :refer [run-jetty]])
  (:import javax.servlet.http.HttpServletRequest))

(defn handler [^HttpServletRequest request]
  (str "Hello World "))

(defn -main [& args]
  (run-jetty handler))