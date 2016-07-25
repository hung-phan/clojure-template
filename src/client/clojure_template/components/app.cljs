(ns clojure-template.components.app
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(defn root-component [app]
  (reify
    om/IRender
    (render [_]
      (dom/div nil (dom/h1 nil (:text app))))))

