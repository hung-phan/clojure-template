(ns clojure-template.components.todos.logic-bundle
  (:require-macros [reagent.ratom :refer [reaction]]
                   [cljs.core.async.macros :refer [go]])
  (:require [re-frame.core :refer [reg-event-db reg-sub-raw path dispatch]]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]
            [clojure-template.databases :refer [interceptors]]))

;; event handlers
(defn set-todos [todos] [:todos/set-todos todos])

(defn fetch-todos []
  (go
    (let [resp (<! (http/get "/api/v1/todos"))]
      (dispatch (set-todos (:body resp))))))

(def todos-interceptors [(path :todos)])

(reg-event-db
  :todos/set-todos
  [interceptors todos-interceptors]
  (fn [_ [_ todos]]
    todos))

;; subscriptions
(def todos-subscription ::todos)

(reg-sub-raw
  todos-subscription
  (fn [db]
    (reaction (get-in @db [:todos]))))
