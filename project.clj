(defproject kangfka "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [kangfka/kangfka-core "0.1.0-SNAPSHOT"]
                 [kangfka/kangfka-web "0.1.0-SNAPSHOT"]]
  :plugins [[lein-sub "0.2.4"]
            [lein-ring "0.12.1"]]
  :sub ["kangfka_core"
        "kangfka-web"]
  :ring {:handler kangfka-web.core/app :port 3000}
  :main kangfka-web.core)
