(ns server.application.apis.base
  (:require [compojure.api.sweet :refer [api context]]
            [server.application.apis.todos :refer [todos-api-v1]]))

(def apis
  (api
    {:swagger {:ui   "/apis"
               :spec "/swagger.json"
               :data {:info {:title "APIS" :description "clojure_template apis"}
                      :tags [{:name "api", :description "apis"}]}}}

    (context "/api/v1" []
      :tags ["api"]

      todos-api-v1)))
