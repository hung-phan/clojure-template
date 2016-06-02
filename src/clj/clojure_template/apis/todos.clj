(ns clojure-template.apis.todos
  (:require [compojure.api.sweet :refer [GET]]
            [ring.util.http-response :refer [ok]]
            [schema.core :as s]
            [clojure-template.models.todos :as Todo]))

(def todos-api-v1
  (GET "/todos" []
    :return [{:text s/Str :complete s/Bool}]
    :summary "get todos list"
    (ok Todo/todos)))