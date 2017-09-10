(ns clojure-template.server.main
  (:require [environ.core :refer [env]]
            [com.stuartsierra.component :as component]
            [clojure-template.server.infrastructure.database :refer [new-database]]
            [clojure-template.server.application.http-handler :refer [new-http-handler]]
            [clojure-template.server.infrastructure.web-server :refer [new-server]])
  (:gen-class))


(def system-map
  (component/system-map
    ;; replace this configuration with production configuration
    :database (new-database {:auto-commit        true
                             :read-only          false
                             :connection-timeout 30000
                             :validation-timeout 5000
                             :idle-timeout       600000
                             :max-lifetime       1800000
                             :minimum-idle       10
                             :maximum-pool-size  10
                             :pool-name          "db-pool"
                             :adapter            "postgresql"
                             :username           "developer"
                             :password           "developer"
                             :database-name      "postgres"
                             :server-name        "database"
                             :port-number        5432
                             :register-mbeans    false})
    :http-handler (component/using (new-http-handler)
                                   [:database])
    :web-server (component/using (new-server (Integer/valueOf ^String (or (env :port) 3000)))
                                 [:http-handler])))

(def prod-system (atom system-map))

(defn -main []
  (swap! prod-system component/start-system))
