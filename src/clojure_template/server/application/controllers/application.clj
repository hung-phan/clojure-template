(ns clojure-template.server.application.controllers.application
  (:require [environ.core :refer [env]]
            [clojure-template.server.application.templates.layouts :as layouts]))

(defn index []
  (layouts/application
    [:div {:id "app"}]))