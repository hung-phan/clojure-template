(ns clojure-template.templates.contents
  (:require [environ.core :refer [env]]))

(defn index []
  [:div {:id "app"}])
