(ns server.domain.dao.todos
  (:require [clojure.java.jdbc :as jdbc]
            [server.domain.model.todos :refer [map->Todo]]))

(defn all [database]
  (jdbc/with-db-connection
    [conn {:datasource (:datasource database)}]
    (for [row (jdbc/query conn ["SELECT id, text, complete from todos;"])]
      (map->Todo row))))
