(ns clojure-template.client
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [reagent.core :as r]
            [goog.dom :as gdom]
            [clojure-template.components.todos :refer [todos]]))

(enable-console-print!)

(r/render-component [todos] (gdom/getElement "app"))
