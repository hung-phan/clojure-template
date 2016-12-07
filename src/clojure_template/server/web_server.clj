(ns clojure-template.server.web-server
  (:require [compojure.handler :refer [site]]
            [org.httpkit.server :refer [run-server]]
            [com.stuartsierra.component :as component]))

(defrecord Server [port http-handler server]
  component/Lifecycle

  (start [this]
    (println "Server start at port" port)
    (let [server (run-server (site (:handler http-handler)) {:port port})]
      (assoc this :server server)))

  (stop [this]
    (when server
      (server)
      (assoc this :server nil))))

(defn new-server [port]
  (map->Server {:port port}))
