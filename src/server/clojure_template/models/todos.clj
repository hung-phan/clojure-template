(ns clojure-template.models.todos
  (:require [schema.core :as s]
            [clojure.data.generators :as generators]))

(s/defrecord Todo
  [text :- s/Str
   complete :- s/Bool])

(def todos
  (repeatedly 10 (fn []
                   (->Todo (generators/string) (generators/boolean)))))
