(ns clojure-template.core
  (:require [om.core :as om :include-macros true]
            [clojure-template.components.app :refer [root-component]]))

(enable-console-print!)

(defonce app-state (atom {:text "Hello Chestnut!"}))

(om/root
  root-component
  app-state
  {:target (.getElementById js/document "app")})
