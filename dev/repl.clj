(ns repl
  (:require [figwheel-sidecar.repl-api :as figwheel]))

(figwheel/start-figwheel!)
(figwheel/cljs-repl)