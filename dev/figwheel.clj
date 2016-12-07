(ns figwheel
  (:require [com.stuartsierra.component :as component]
            [figwheel-sidecar.repl-api :as figwheel]))

(defrecord FigwheelServer []
  component/Lifecycle

  (start [this]
    (figwheel/start-figwheel!)
    this)

  (stop [this]
    (figwheel/stop-figwheel!)
    this))

(defn new-figwheel-server []
  (->FigwheelServer))
