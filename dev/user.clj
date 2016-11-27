(ns user
  (:require [clojure-template.server :refer [http-handler]]
            [ring.middleware.reload :refer [wrap-reload]]
            [prone.middleware :refer [wrap-exceptions]]
            [figwheel-sidecar.repl-api :as figwheel]))

;; Let Clojure warn you when it needs to reflect on types, or when it does math
;; on unboxed numbers. In both cases you should add type annotations to prevent
;; degraded performance.
(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)

(def figwheel-http-handler
  (-> http-handler
      wrap-exceptions
      wrap-reload))

(defn run [] (figwheel/start-figwheel!))
(def browser-repl figwheel/cljs-repl)
