(ns clojure-template.client.styles
  (:require [garden.def :refer [defstylesheet defstyles]]
            [clojure-template.client.components.todos.styles :as todos]))

(defstyles app
           todos/body)
