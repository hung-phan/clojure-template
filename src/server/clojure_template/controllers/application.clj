(ns clojure-template.controllers.application
  (:require [environ.core :refer [env]]
            [clojure-template.templates.layouts :as layouts]))

(defn index []
  (layouts/application
    [:div {:id "app"}]))