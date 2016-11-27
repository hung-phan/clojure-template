(ns user
  (:require [ring.middleware.reload :refer [wrap-reload]]
            [prone.middleware :refer [wrap-exceptions]]
            [figwheel-sidecar.repl-api :as figwheel]
            [com.stuartsierra.component :as component]
            [web-server :refer [new-dev-server]]
            [clojure-template.server.main :refer [http-handler]]
            [clojure-template.server.database :refer [new-database]]))

;; Let Clojure warn you when it needs to reflect on types, or when it does math
;; on unboxed numbers. In both cases you should add type annotations to prevent
;; degraded performance.
(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)

;; using figwheel handler
(def figwheel-http-handler
  (-> http-handler
      wrap-exceptions
      wrap-reload))

(def dev-system
  (component/system-map
    :database (new-database {:adapter "h2"
                             :url     "jdbc:h2:~/database"})
    :server (new-dev-server)))

(defn start-system []
  (component/start-system dev-system))

(defn stop-system []
  (component/stop-system dev-system))

(defn restart-system []
  (do
    (stop-system)
    (start-system)))

(defn run-browser-repl []
  (figwheel/cljs-repl))
