(ns clojure-template.styles
  (:require [garden.def :refer [defstylesheet defstyles]]
            [clojure-template.components.todos.style :as todos]))

(defstyles app
           todos/body)
