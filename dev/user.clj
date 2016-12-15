(ns user
  (:require [figwheel-sidecar.repl-api :as figwheel]
            [com.stuartsierra.component :as component]
            [figwheel :refer [new-figwheel-server]]
            [garden-development :refer [new-garden-server]]
            [clojure-template.server.main :refer [system-map]]
            [clojure-template.server.database :refer [new-database]]))

;; Let Clojure warn you when it needs to reflect on types, or when it does math
;; on unboxed numbers. In both cases you should add type annotations to prevent
;; degraded performance.
(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)

(def dev-system-map
  (-> system-map
      (assoc :database (new-database {:adapter "h2"
                                      :url     "jdbc:h2:~/dev-database"}))
      (assoc :figwheel (new-figwheel-server))
      (assoc :garden (new-garden-server))))

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
