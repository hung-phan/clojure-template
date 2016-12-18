(ns clojure-template.server.daos.todos
  (:require [clojure.java.jdbc :as jdbc]
            [clojure-template.server.models.todos :refer [map->Todo]]))

(defn all [database]
  (jdbc/with-db-connection
    [conn {:datasource (:datasource database)}]
    (for [row (jdbc/query conn ["SELECT id, text, complete from todos;"])]
      (map->Todo row))))
