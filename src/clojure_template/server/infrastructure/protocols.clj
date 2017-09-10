(ns clojure-template.server.infrastructure.protocols)

(defprotocol Migration
  "Protocol for database migration"
  (create-rag-config [this] "Create rag configuration of migrate and rollback operation")
  (migrate [this] "Migrate the datastore up to the latest migration")
  (rollback [this] "Rollback the datastore one or more migrations"))
