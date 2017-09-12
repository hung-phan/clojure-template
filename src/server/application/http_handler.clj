(ns server.application.http-handler
  (:require [bidi.bidi :as bidi]
            [environ.core :refer [env]]
            [compojure.core :refer [ANY GET PUT POST DELETE routes]]
            [compojure.route :refer [resources not-found]]
            [compojure.api.middleware :refer [wrap-components]]
            [ring.util.response :refer [redirect]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.gzip :refer [wrap-gzip]]
            [ring.middleware.logger :refer [wrap-with-logger]]
            [ring.middleware.reload :refer [wrap-reload]]
            [prone.middleware :refer [wrap-exceptions]]
            [com.stuartsierra.component :as component]
            [server.application.controllers.application :as application-controller]
            [server.application.apis.base :as base-apis]
            [share.routes :refer [app-routes]]))


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

          default-handler (-> routes
                              (wrap-defaults site-defaults)
                              (wrap-components {:database database})
                              wrap-with-logger
                              wrap-gzip)

          development-handler (-> default-handler
                                  wrap-exceptions
                                  wrap-reload)]

      (assoc this :handler (if (= "development" (env :clj-env))
                             development-handler
                             default-handler))))

  (stop [this]
    (when handler
      (assoc this :handler nil))))

(defn new-http-handler []
  (map->HTTPHandler {}))
