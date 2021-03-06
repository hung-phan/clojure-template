(ns client.components.todos.main
  (:require [re-frame.core :as rf]
            [client.components.todos.logic-bundle :as lb]
            [client.components.todos.header :refer [header-component]]
            [client.components.todos.add :refer [add-component]]
            [client.components.todos.body :refer [body-component]]
            [client.components.todos.footer :refer [footer-component]]))

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
