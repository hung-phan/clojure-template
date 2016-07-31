(ns clojure-template.client
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [reagent.core :as r]
            [goog.dom :as gdom]))

(enable-console-print!)

(defn Todos []
  [:div
   {:class-name "container"}
   [:div
    {:class-name "row"}
    [:div
     {:class-name "col-md-12"}
     "Hello world"]]])

(r/render-component [Todos] (gdom/getElement "app"))
