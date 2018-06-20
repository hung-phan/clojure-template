(ns server.infrastructure.persistence.database
  (:require [hikari-cp.core :as hikari]
            [ragtime.repl :as rag-repl]
            [ragtime.jdbc :as jdbc]
            [com.stuartsierra.component :as component]
            [server.infrastructure.persistence.protocols :as protocols]))

(defrecord Database [datasource-options datasource]
  component/Lifecycle

  (start [this]
    (let [datasource (hikari/make-datasource datasource-options)]
      (assoc this :datasource datasource)))

  (stop [this]
    (when datasource
      (hikari/close-datasource datasource)
      (assoc this :datasource nil)))

  protocols/Migration

  (create-rag-config [_]
    {:datastore  (jdbc/sql-database {:datasource datasource})
     :migrations (jdbc/load-resources "migrations")})

  (migrate [this]
    (rag-repl/migrate (.create-rag-config this)))

  (rollback [this]
    (rag-repl/rollback (.create-rag-config this))))

(defn new-database [datasource-options]
  (map->Database {:datasource-options datasource-options}))
