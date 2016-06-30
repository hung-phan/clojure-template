(ns clojure-template.styles
  (:require [garden.def :refer [defstylesheet defstyles]]
            [garden.units :refer [px]]))

(defstyles app
  [:h1
   {:text-decoration "underline"}])
