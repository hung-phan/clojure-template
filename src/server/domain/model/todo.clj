(ns server.domain.model.todo
  (:require [schema.core :as s]))

(s/defrecord Todo [id :- s/Num
                   text :- s/Str
                   complete :- s/Bool])
