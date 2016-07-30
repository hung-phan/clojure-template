(ns clojure-template.client
  (:require [om.next :as om]
            [clojure-template.components.todos :refer [Todos]]
            [goog.dom :as gdom]))

(enable-console-print!)

(defonce app-state (atom {:count 0}))
(defonce reconciler (om/reconciler {:state app-state}))

(js/setInterval
  (fn []
    (swap! app-state update-in [:count] inc))
  1000)

(om/add-root!
  reconciler
  Todos
  (gdom/getElement "app"))
