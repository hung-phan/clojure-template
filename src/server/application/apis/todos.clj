(ns server.application.apis.todos
  (:require [schema.core :as s]
            [compojure.api.sweet :refer [GET]]
            [ring.util.http-response :refer [ok]]
            [server.infrastructure.persistence.todo_repository :as todo-repository]))

(def todos-api-v1
  (GET "/todos" []
    :return [{:id s/Num :text s/Str :complete s/Bool}]
    :summary "get todos list"
    :components [database]
    (ok (todo-repository/all database))))