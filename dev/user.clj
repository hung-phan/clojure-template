(ns user
  (:require [ring.middleware.reload :refer [wrap-reload]]
            [prone.middleware :refer [wrap-exceptions]]
            [figwheel-sidecar.repl-api :as figwheel]
            [com.stuartsierra.component :as component]
            [web-server :refer [new-dev-server]]
            [clojure-template.server.main :refer [http-handler]]))

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
    :server (new-dev-server)))

(defn run []
  (component/start-system dev-system))

(defn run-browser-repl []
  (figwheel/cljs-repl))
