(ns clojure-template.client
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [reagent.core :as r]
            [goog.dom :as gdom]))

(enable-console-print!)

(defn Todos []
  [:div.container
   [:div.row
    [:div.col-md-12
     "Hello world"]]])

(r/render-component [Todos] (gdom/getElement "app"))

(js/console.log "yolo")
