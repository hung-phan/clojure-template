(ns clojure-template.components.todos.logic-bundle
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [re-frame.core :as rf]
            [cljs-http.client :as http]
            [clojure-template.store :as db]
            [cljs.core.async :refer [<!]]))

(def ^:private set-todos-action :todos/set-todos)
(def ^:private add-todo-action :todos/add-todo)
(def ^:private remove-todo-action :todos/remove-todo)
(def ^:private complete-todo-action :todos/complete-todo)

(def ^:private todos-path :todos)
(def ^:private todos-interceptors [db/interceptors [(rf/path todos-path)]])

(def todos-subscription ::todos)

(defn set-todos [todos] [set-todos-action todos])
(defn add-todo [todo] [add-todo-action todo])
(defn remove-todo [index] [remove-todo-action index])
(defn complete-todo [index] [complete-todo-action index])
(defn fetch-todos []
  (go
    (let [resp (<! (http/get "/api/v1/todos"))]
      (rf/dispatch (set-todos (:body resp))))))

(rf/reg-sub
  todos-subscription
  (fn [db _]
    (get db todos-path)))

(rf/reg-event-db
  :initialize-db
  todos-interceptors
  (fn [_ _]
    []))

(rf/reg-event-db
  set-todos-action
  todos-interceptors
  (fn [_ [_ todos]]
    todos))

(rf/reg-event-db
  add-todo-action
  todos-interceptors
  (fn [todos [_ todo]]
    (conj todos {:text todo :complete false})))

(rf/reg-event-db
  remove-todo-action
  todos-interceptors
  (fn [todos [_ index]]
    (vec (concat (subvec todos 0 index) (subvec todos (inc index))))))

(rf/reg-event-db
  complete-todo-action
  todos-interceptors
  (fn [todos [_ index]]
    (update-in todos [index :complete] not)))
