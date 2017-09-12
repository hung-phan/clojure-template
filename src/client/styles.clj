(ns client.styles
  (:require [garden.def :refer [defstylesheet defstyles]]
            [client.components.todos.styles :as todos]))

(defstyles app
           todos/body)
