(ns clojure-template.client
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [bidi.bidi :as bidi]
            [pushy.core :as pushy]
            [reagent.core :as r]
            [goog.dom :as gdom]
            [devtools.core :as devtools]
            [clojure-template.components.todos.main :refer [todos-component]]
            [clojure-template.components.static-page.main :refer [static-page-component]]
            [clojure-template.routes :refer [app-routes]]))

;; enable console log debugging using devtools
(when ^boolean js/goog.DEBUG
  (devtools.core/set-pref! :dont-detect-custom-formatters true)
  (devtools/install!))

(defn- render [component]
  (r/render-component component (gdom/getElement "app")))

(def history
  (pushy/pushy
    ;;; to get the params from route: access :key route-params
    (fn [{:keys [handler]}]
      (let [component (case handler
                        :todos [todos-component]
                        :static-page [static-page-component])]
        (render component)))
    (partial bidi/match-route app-routes)))

(pushy/start! history)
