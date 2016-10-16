(ns clojure-template.server
  (:require [compojure.core :refer [ANY GET PUT POST DELETE defroutes]]
            [compojure.route :refer [resources not-found]]
            [compojure.handler :refer [site]]
            [ring.util.response :refer [redirect]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.gzip :refer [wrap-gzip]]
            [ring.middleware.logger :refer [wrap-with-logger]]
            [environ.core :refer [env]]
            [bidi.bidi :as bidi]
            [org.httpkit.server :refer [run-server]]
            [clojure-template.controllers.application :as application-controller]
            [clojure-template.apis.base :as base-apis]
            [clojure-template.routes :refer [app-routes]])
  (:gen-class))

(defn render [params template]
  (if (bidi/match-route app-routes (str "/" (:* params)))
    (template)
    (redirect "/404.html")))

(defroutes routes
           (resources "/public")
           base-apis/apis
           (GET "/*" [& request] (render request application-controller/index)))

(def http-handler
  (-> routes
      (wrap-defaults site-defaults)
      wrap-with-logger
      wrap-gzip))

(defn -main []
  (let [port (Integer/valueOf ^String (or (env "PORT") 3000))]
    (run-server (site http-handler) {:port port})))
