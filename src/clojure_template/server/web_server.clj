(ns clojure-template.server.web-server
  (:require [compojure.handler :refer [site]]
            [org.httpkit.server :refer [run-server]]
            [com.stuartsierra.component :as component]))

(defrecord Server [port http-handler stop-server-callback]
  component/Lifecycle

  (start [this]
    (println "Web server start at port" port)

    (let [stop-server-callback (run-server (site (:handler http-handler)) {:port port})]
      (assoc this :stop-server-callback stop-server-callback)))

  (stop [this]
    (when stop-server-callback
      (println "Web server stop")

      (stop-server-callback)
      (assoc this :stop-server-callback nil))))

(defn new-server [port]
  (map->Server {:port port}))
