(ns clojure-template.server.main-test
  (:require [com.stuartsierra.component :as component]
            [clojure-template.server.main :refer [system-map prod-system]]
            [clojure-template.server.infrastructure.database :refer [new-database]]))

(def test-system-map
  (-> system-map
      (assoc :database (new-database {:auto-commit        true
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
                                      :database-name      "test_postgres"
                                      :server-name        "localhost"
                                      :port-number        5432
                                      :register-mbeans    false}))
      ;; we don't need to start the web-server for testing
      (dissoc :web-server)))

(defn with-test-system [fn]
  (let [test-system (atom test-system-map)]
    (swap! test-system component/start-system)
    (-> @test-system :database .migrate)
    (fn @test-system)
    (swap! test-system component/stop-system)))
