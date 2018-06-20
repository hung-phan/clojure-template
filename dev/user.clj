(ns user
  (:require [figwheel-sidecar.repl-api :as figwheel]
            [com.stuartsierra.component :as component]
            [local.figwheel :refer [new-figwheel-server]]
            [local.seed-data :refer [seed-todos]]
            [server.main :refer [system-map prod-system]]
            [server.infrastructure.persistence.database :refer [new-database]]))

;; Let Clojure warn you when it needs to reflect on types, or when it does math
;; on unboxed numbers. In both cases you should add type annotations to prevent
;; degraded performance.
(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)

(def dev-system-map
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
                                      :database-name      "postgres"
                                      :server-name        "localhost"
                                      :port-number        5432
                                      :register-mbeans    false}))
      (assoc :figwheel (new-figwheel-server))))

(def dev-system
  (atom dev-system-map))

(defn start-system []
  (swap! dev-system component/start-system))

(defn stop-system []
  (swap! dev-system component/stop-system))

(defn restart-system []
  (do
    (stop-system)
    (start-system)))

(defn start-browser-repl []
  (figwheel/cljs-repl))
