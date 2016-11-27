(ns clojure-template.server.controllers.application
  (:require [environ.core :refer [env]]
            [clojure-template.server.templates.layouts :as layouts]))

(defn index []
  (layouts/application
    [:div {:id "app"}]))