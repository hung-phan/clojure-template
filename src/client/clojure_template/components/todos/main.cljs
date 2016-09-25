(ns clojure-template.components.main
  (:require [re-frame.core :refer [subscribe dispatch]]
            [clojure-template.components.todos.logic-bundle :as lb]
            [clojure-template.components.todos.header :refer [header-component]]
            [clojure-template.components.todos.body :refer [body-component]]))

(defn todos-component []
  (let [todos (subscribe [lb/todos-subscription])]
    (lb/fetch-todos)
    (fn []
      [:div.container
       [:div.row
        [header-component]
        [body-component @todos]]])))
