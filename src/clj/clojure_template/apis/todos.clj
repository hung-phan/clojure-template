(ns clojure-template.apis.todos
  (:require [compojure.api.sweet :refer [GET]]
            [ring.util.http-response :refer [ok]]
            [schema.core :as s]))

(def todos-api-v1
  (GET "/todos" []
       :return [{:text s/Str :complete s/Bool}]
       :summary "get todos list"
       (ok [{:text "Todo 1" :complete true}
            {:text "Todo 2" :complete false}
            {:text "Todo 3" :complete true}])))