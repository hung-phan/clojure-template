(ns clojure-template.server.main
  (:require [com.stuartsierra.component :as component]
            [environ.core :refer [env]]
            [clojure-template.server.database :refer [new-database]]
            [clojure-template.server.http-handler :refer [new-http-handler]]
            [clojure-template.server.web-server :refer [new-server]])
  (:gen-class))


(def system-map
  (component/system-map
    :database (new-database {:adapter "h2"
                             :url     "jdbc:h2:/tmp/prod-database"})
    :http-handler (component/using (new-http-handler)
                                   [:database])
    :web-server (component/using (new-server (Integer/valueOf ^String (or (env :port) 3000)))
                                 [:http-handler])))

(def prod-system (atom system-map))

(defn -main []
  (swap! prod-system component/start-system))
