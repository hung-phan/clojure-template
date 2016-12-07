(ns user
  (:require [figwheel-sidecar.repl-api :as figwheel]
            [com.stuartsierra.component :as component]
            [figwheel :refer [new-figwheel-server]]
            [clojure-template.server.main :refer [system-dependencies]]
            [clojure-template.server.database :refer [new-database]]))

;; Let Clojure warn you when it needs to reflect on types, or when it does math
;; on unboxed numbers. In both cases you should add type annotations to prevent
;; degraded performance.
(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)

(def dev-system-dependencies
  (assoc system-dependencies
    :database (new-database {:adapter "h2"
                             :url     "jdbc:h2:~/dev-database"})
    :fighwheel-server (new-figwheel-server)))

(def dev-system
  (atom (apply component/system-map
               (flatten (seq dev-system-dependencies)))))

(defn start-system []
  (swap! dev-system component/start))

(defn stop-system []
  (swap! dev-system component/stop))

(defn restart-system []
  (do
    (stop-system)
    (start-system)))

(defn run-browser-repl []
  (figwheel/cljs-repl))
