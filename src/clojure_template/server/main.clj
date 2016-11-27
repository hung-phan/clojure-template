(ns clojure-template.server.main
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
            [clojure-template.server.controllers.application :as application-controller]
            [clojure-template.server.apis.base :as base-apis]
            [clojure-template.common.routes :refer [app-routes]])
  (:gen-class))

(defroutes routes
           (resources "/public")
           base-apis/apis
           (GET "/*" [& request]
             ;; handle routing in client side
             (if (bidi/match-route app-routes (str "/" (:* request)))
               (application-controller/index)
               (redirect "/404.html"))))

(def http-handler
  (-> routes
      (wrap-defaults site-defaults)
      wrap-with-logger
      wrap-gzip))

(defn -main []
  (let [port (Integer/valueOf ^String (or (env :port) 3000))]
    (println "Server start at port" port)
    (run-server (site http-handler) {:port port})))
