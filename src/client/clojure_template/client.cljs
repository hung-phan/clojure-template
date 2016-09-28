(ns clojure-template.client
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [reagent.core :as r]
            [goog.dom :as gdom]
            [devtools.core :as devtools]
            [clojure-template.components.todos.main :refer [todos-component]]))

;; enable console log debugging using devtools
(when ^boolean js/goog.DEBUG
  (devtools.core/set-pref! :dont-detect-custom-formatters true)
  (devtools/install!))

(r/render-component [todos-component] (gdom/getElement "app"))
