(ns clojure-template.client.components.todos.main
  (:require [re-frame.core :as rf]
            [clojure-template.client.components.todos.logic-bundle :as lb]
            [clojure-template.client.components.todos.header :refer [header-component]]
            [clojure-template.client.components.todos.add :refer [add-component]]
            [clojure-template.client.components.todos.body :refer [body-component]]
            [clojure-template.client.components.todos.footer :refer [footer-component]]))

(defn todos-component []
  (let [todos (rf/subscribe [lb/todos-subscription])]
    (lb/fetch-todos)
    (fn []
      [:div.container
       [:div.row
        [header-component]
        [add-component]
        [body-component @todos]
        [footer-component]]])))
