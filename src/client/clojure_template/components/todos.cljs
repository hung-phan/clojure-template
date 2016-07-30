(ns clojure-template.components.todos
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]))

(defui Todos
  Object
  (render [this]
    (let [{:keys [count]} (om/props this)]
      (dom/div nil
               (dom/h1 nil count)))))
