(ns clojure-template.server.database
  (:require [hikari-cp.core :as hikari]
            [clojure.java.jdbc :as jdbc]
            [com.stuartsierra.component :as component]))

(defrecord Database [datasource connection]
  component/Lifecycle

  (start [this]
    (jdbc/with-db-connection
      [connection {:datasource datasource}]
      (assoc this :connection connection)))

  (stop [this]
    (when datasource
      (hikari/close-datasource datasource)
      (assoc this :connection nil))))

(defn new-database [datasource-options]
  (map->Database {:datasource (hikari/make-datasource datasource-options)}))
