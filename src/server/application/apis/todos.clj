(ns server.application.apis.todos
  (:require [schema.core :as s]
            [compojure.api.sweet :refer [GET]]
            [ring.util.http-response :refer [ok]]
            [server.domain.daos.todos :as todos-dao]))

(def todos-api-v1
  (GET "/todos" []
    :return [{:id s/Num :text s/Str :complete s/Bool}]
    :summary "get todos list"
    :components [database]
    (ok (todos-dao/all database))))