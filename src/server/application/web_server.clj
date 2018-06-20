(ns server.application.web-server
  (:require [aleph.http :refer [start-server]]
            [com.stuartsierra.component :as component]))

(defrecord Server [port http-handler netty-server]
  component/Lifecycle

  (start [this]
    (println "Web server start at port" port)
    (assoc this :netty-server
                (start-server (:handler http-handler)
                              {:port port})))

  (stop [this]
    (when netty-server
      (println "Web server stop")
      (.close netty-server)
      (assoc this :stop-server-callback nil))))

(defn new-server [port]
  (map->Server {:port port}))
