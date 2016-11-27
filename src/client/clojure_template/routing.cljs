(ns clojure-template.routing
  (:require [bidi.bidi :as bidi]
            [pushy.core :as pushy]
            [reagent.core :as reagent]
            [goog.dom :as gdom]
            [clojure-template.components.todos.main :refer [todos-component]]
            [clojure-template.components.static-page.main :refer [static-page-component]]
            [clojure-template.routes :refer [app-routes]]))

(pushy/start!
  (pushy/pushy
    ;;; access :key :route-params to get the route params
    (fn [{:keys [handler]}]
      (let [component (case handler
                        :todos [todos-component]
                        :static-page [static-page-component]
                        (throw (js/Error (ex-info "Unhandle route" handler))))]
        (reagent/render-component component (gdom/getElement "app"))))
    (partial bidi/match-route app-routes)))
