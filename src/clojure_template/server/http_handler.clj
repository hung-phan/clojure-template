(ns clojure-template.server.http-handler
  (:require [compojure.core :refer [ANY GET PUT POST DELETE routes]]
            [compojure.route :refer [resources not-found]]
            [ring.util.response :refer [redirect]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.gzip :refer [wrap-gzip]]
            [ring.middleware.logger :refer [wrap-with-logger]]
            [ring.middleware.reload :refer [wrap-reload]]
            [prone.middleware :refer [wrap-exceptions]]
            [com.stuartsierra.component :as component]
            [bidi.bidi :as bidi]
            [environ.core :refer [env]]
            [clojure-template.server.controllers.application :as application-controller]
            [clojure-template.server.apis.base :as base-apis]
            [clojure-template.common.routes :refer [app-routes]]))


(defrecord HTTPHandler [database handler]
  component/Lifecycle

  (start [this]
    (let [routes (routes
                   ;; resources
                   (resources "/public")
                   ;; apis
                   base-apis/apis
                   ;; handle routing in client side
                   (GET "/*" [& request]
                     (if (bidi/match-route app-routes (str "/" (:* request)))
                       (application-controller/index)
                       (redirect "/404.html"))))

          ;; initialize middleware
          handler (if (= "development" (env :clj-env))
                    (-> routes
                        (wrap-defaults site-defaults)
                        wrap-with-logger
                        wrap-gzip
                        wrap-exceptions
                        wrap-reload)

                    (-> routes
                        (wrap-defaults site-defaults)
                        wrap-with-logger
                        wrap-gzip))]

      (assoc this :handler handler)))

  (stop [this]
    (when handler
      (assoc this :handler nil))))

(defn new-http-handler []
  (map->HTTPHandler {}))
