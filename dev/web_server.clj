(ns web-server
  (:require [com.stuartsierra.component :as component]
            [figwheel-sidecar.repl-api :as figwheel]))

(defrecord DevServer []
  component/Lifecycle

  (start [_]
    (figwheel/start-figwheel!))

  (stop [_]
    (figwheel/stop-figwheel!)))

(defn new-dev-server []
  (->DevServer))
