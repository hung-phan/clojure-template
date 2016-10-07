(ns clojure-template.server
  (:require [compojure.core :refer [ANY GET PUT POST DELETE defroutes]]
            [compojure.route :refer [resources]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.gzip :refer [wrap-gzip]]
            [ring.middleware.logger :refer [wrap-with-logger]]
            [environ.core :refer [env]]
            [ring.adapter.jetty :refer [run-jetty]]
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

(defn -main [& [port]]
  (let [port (read-string (or port (env :port) 3000))]
    (run-jetty http-handler {:port port :join? false})))
