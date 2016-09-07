(ns clojure-template.styles
  (:require [garden.def :refer [defstylesheet defstyles]]
            [garden.units :refer [px]]))

(defstyles
  app
  [:.todos-body
   {:margin-top (px 20)}])
