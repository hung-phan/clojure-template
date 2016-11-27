(ns clojure-template.server.daos.todos
  (:require [schema.core :as s]
            [clojure.data.generators :as generators]))

(s/defrecord Todo [id :- s/Uuid
                   text :- s/Str
                   complete :- s/Bool])

(def ^:private todos
  (repeatedly 10 #(->Todo (generators/uuid) (generators/string) (generators/boolean))))

(defn all [] todos)
