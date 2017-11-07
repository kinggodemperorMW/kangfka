(ns kangfka.server.runner
  (:import [org.eclipse.jetty.server Server ServerConnector]
           org.eclipse.jetty.server.handler.AbstractHandler
           (javax.servlet.http HttpServletResponse)))

(defn- new-handler [handler]
  (proxy [AbstractHandler] []
    (handle [target base-request request ^HttpServletResponse response]
      (with-open [writer (.getWriter response)]
        (.print writer (handler request))))))

(defn run-jetty [handler]
  (let [^Server server (Server.)
        ^ServerConnector connector (doto (ServerConnector. server)
                                     (.setPort 8080))]
    (doto server
      (.addConnector connector)
      (.setHandler (new-handler handler))
      (.start)
      (.join))))