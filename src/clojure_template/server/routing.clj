(ns clojure-template.server.routing
  (:require [compojure.core :refer [ANY GET PUT POST DELETE defroutes]]
            [compojure.route :refer [resources not-found]]
            [ring.util.response :refer [redirect]]
            [bidi.bidi :as bidi]
            [clojure-template.server.controllers.application :as application-controller]
            [clojure-template.server.apis.base :as base-apis]
            [clojure-template.common.routes :refer [app-routes]]))

(defroutes routes
           (resources "/public")
           base-apis/apis
           (GET "/*" [& request]
             ;; handle routing in client side
             (if (bidi/match-route app-routes (str "/" (:* request)))
               (application-controller/index)
               (redirect "/404.html"))))
