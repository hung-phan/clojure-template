(ns clojure-template.apis.todos
  (:require [compojure.api.sweet :refer [GET]]
            [ring.util.http-response :refer [ok]]
            [schema.core :as s]
            [clojure.data.generators :as generators]))

(defn get-random-todo []
  {:text (generators/string) :complete (generators/boolean)})

(def todos-api-v1
  (GET "/todos" []
       :return [{:text s/Str :complete s/Bool}]
       :summary "get todos list"
       (ok (repeatedly 10 get-random-todo))))