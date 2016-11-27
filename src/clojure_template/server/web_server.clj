(ns clojure-template.server.web-server
  (:require [org.httpkit.server :refer [run-server]]
            [com.stuartsierra.component :as component]))

(defrecord Server [port http-handler server]
  component/Lifecycle

  (start [this]
    (println "Server start at port" port)
    (let [httpkit-server (run-server http-handler {:port port})
          server (:server (meta httpkit-server))]
      (assoc this :server server)))

  (stop [_]
    (when server
      (.stop server))))

(defn new-server [port http-handler]
  (map->Server {:port port :http-handler http-handler}))
