(ns server.application.controllers.application
  (:require [environ.core :refer [env]]
            [server.application.templates.layouts :as layouts]))

(defn index []
  (layouts/application
    [:div {:id "app"}]))