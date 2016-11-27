(ns clojure-template.server.database
  (:require [hikari-cp.core :as hikari]
            [com.stuartsierra.component :as component]))

(defrecord Database [datasource-options datasource]
  component/Lifecycle

  (start [this]
    (let [datasource (hikari/make-datasource datasource-options)]
      (assoc this :datasource datasource)))

  (stop [this]
    (when datasource
      (hikari/close-datasource datasource)
      (assoc this :datasource nil))))

(defn new-database [datasource-options]
  (map->Database {:datasource-options datasource-options}))
