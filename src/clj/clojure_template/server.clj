(ns clojure-template.server
  (:require [compojure.core :refer [ANY GET PUT POST DELETE defroutes]]
            [compojure.route :refer [resources]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.gzip :refer [wrap-gzip]]
            [ring.middleware.logger :refer [wrap-with-logger]]
            [environ.core :refer [env]]
            [ring.adapter.jetty :refer [run-jetty]]
            [clojure-template.templates.layouts :as layouts]
            [clojure-template.templates.contents :as contents])
  (:gen-class))

(defroutes routes
  (GET "/" []
    (layouts/application (contents/index)))
  (resources "/"))

(def http-handler
  (-> routes
      (wrap-defaults site-defaults)
      wrap-with-logger
      wrap-gzip))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 10555))]
    (run-jetty http-handler {:port port :join? false})))