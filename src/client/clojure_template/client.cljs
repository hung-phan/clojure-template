(ns clojure-template.client
  (:require [om.next :as om]
            [clojure-template.components.todos :refer [Todos]]
            [goog.dom :as gdom]))

(enable-console-print!)

(defonce app-state (atom {:text "Hello Chestnut!"}))
(defonce reconciler (om/reconciler {:state app-state}))

(om/add-root!
  reconciler
  Todos
  (gdom/getElement "app"))
