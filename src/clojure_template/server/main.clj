(ns clojure-template.server.main
  (:require [compojure.handler :refer [site]]
            [com.stuartsierra.component :as component]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.gzip :refer [wrap-gzip]]
            [ring.middleware.logger :refer [wrap-with-logger]]
            [environ.core :refer [env]]
            [org.httpkit.server :refer [run-server]]
            [clojure-template.server.routing :refer [routes]]
            [clojure-template.server.web-server :refer [new-server]]
            [clojure-template.server.database :refer [new-database]])
  (:gen-class))

(def http-handler
  (-> routes
      (wrap-defaults site-defaults)
      wrap-with-logger
      wrap-gzip))

(def prod-system
  (component/system-map
    :database (new-database {:adapter "h2"
                             :url     "jdbc:h2:~/database"})
    :server (new-server
              (Integer/valueOf ^String (or (env :port) 3000))
              (site http-handler))))

(defn -main []
  (component/start-system prod-system))
