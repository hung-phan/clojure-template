(ns clojure-template.server.models.todos
  (:require [schema.core :as s]))

(s/defrecord Todo [id :- s/Num
                   text :- s/Str
                   complete :- s/Bool])
