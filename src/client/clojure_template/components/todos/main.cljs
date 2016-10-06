(ns clojure-template.components.todos.main
  (:require [re-frame.core :as rf]
            [clojure-template.components.todos.logic-bundle :as lb]
            [clojure-template.components.todos.header :refer [header-component]]
            [clojure-template.components.todos.add :refer [add-component]]
            [clojure-template.components.todos.body :refer [body-component]]))

(defn todos-component []
  (let [todos (rf/subscribe [lb/todos-subscription])]
    (lb/fetch-todos)
    (fn []
      [:div.container
       [:div.row
        [header-component]
        [add-component]
        [body-component @todos]]])))
