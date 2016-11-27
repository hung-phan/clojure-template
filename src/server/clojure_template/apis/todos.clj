(ns clojure-template.apis.todos
  (:require [schema.core :as s]
            [compojure.api.sweet :refer [GET]]
            [ring.util.http-response :refer [ok]]
            [clojure-template.daos.todos :as TodosDAO]))

(def todos-api-v1
  (GET "/todos" []
    :return [{:id s/Uuid :text s/Str :complete s/Bool}]
    :summary "get todos list"
    (ok (TodosDAO/all))))