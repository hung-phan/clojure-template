(ns web-server
  (:require [com.stuartsierra.component :as component]
            [figwheel-sidecar.repl-api :as figwheel]))

(defrecord DevServer []
  component/Lifecycle

  (start [_]
    (println "Start figwheel server")
    (figwheel/start-figwheel!))

  (stop [_]
    (println "Stop figwheel server")
    (figwheel/stop-figwheel!)))

(defn new-dev-server []
  (->DevServer))
