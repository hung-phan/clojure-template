(ns clojure-template.server.main
  (:require [com.stuartsierra.component :as component]
            [environ.core :refer [env]]
            [clojure-template.server.http-handler :refer [new-http-handler]]
            [clojure-template.server.web-server :refer [new-server]]
            [clojure-template.server.database :refer [new-database]])
  (:gen-class))

(def system-dependencies {:database     (new-database {:adapter "h2"
                                                       :url     "jdbc:h2:~/prod-database"})
                          :web-server   (component/using (new-server (Integer/valueOf ^String (or (env :port) 3000)))
                                                         [:http-handler])
                          :http-handler (component/using (new-http-handler)
                                                         [:database])})

(def prod-system
  (atom (apply component/system-map
               (flatten (seq system-dependencies)))))

(defn -main []
  (swap! prod-system component/start))
