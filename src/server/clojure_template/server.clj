(ns clojure-template.server
  (:require [compojure.core :refer [ANY GET PUT POST DELETE defroutes]]
            [compojure.route :refer [resources]]
            [compojure.handler :refer [site]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.gzip :refer [wrap-gzip]]
            [ring.middleware.logger :refer [wrap-with-logger]]
            [environ.core :refer [env]]
            [org.httpkit.server :refer [run-server]]
            [clojure-template.controllers.application :as application-controller]
            [clojure-template.apis.base :as base-apis])
  (:gen-class))

(defroutes routes
           base-apis/apis
           (GET "/" [] (application-controller/index))
           (resources "/public"))

(def http-handler
  (-> routes
      (wrap-defaults site-defaults)
      wrap-with-logger
      wrap-gzip))

(defn -main []
  (let [port (Integer/valueOf ^String (or (env :port) 3000))]
    (println "Server start at port" port)
    (run-server (site http-handler) {:port port})))
