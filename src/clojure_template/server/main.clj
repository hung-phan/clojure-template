(ns clojure-template.server.main
  (:require [compojure.handler :refer [site]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.gzip :refer [wrap-gzip]]
            [ring.middleware.logger :refer [wrap-with-logger]]
            [environ.core :refer [env]]
            [org.httpkit.server :refer [run-server]]
            [clojure-template.server.routing :refer [routes]])
  (:gen-class))

(def http-handler
  (-> routes
      (wrap-defaults site-defaults)
      wrap-with-logger
      wrap-gzip))

(defn -main []
  (let [port (Integer/valueOf ^String (or (env :port) 3000))]
    (println "Server start at port" port)
    (run-server (site http-handler) {:port port})))
