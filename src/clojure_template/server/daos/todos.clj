(ns clojure-template.server.daos.todos
  (:require [schema.core :as s]
            [clojure.data.generators :as generators]
            [clojure-template.server.daos.protocol :as protocol]))

(s/defrecord Todo [id :- s/Uuid
                   text :- s/Str
                   complete :- s/Bool])

(def ^:private todos
  (repeatedly 10 #(->Todo
                    (generators/uuid)
                    (generators/string)
                    (generators/boolean))))

(defrecord TodosDao [database]
  protocol/DAO

  (all [_]
    todos))

(defn new-todos-dao []
  (map->TodosDao {}))
