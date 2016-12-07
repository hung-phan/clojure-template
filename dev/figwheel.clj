(ns figwheel
  (:require [com.stuartsierra.component :as component]
            [figwheel-sidecar.repl-api :as figwheel]))

(def ^:private figwheel-config
  {;; :http-server-root "public"                  ;; serve static assets from resources/public/
   ;; :server-port    8080                        ;; default
   ;; :server-ip "127.0.0.1"                      ;; default
   :css-dirs       ["resources/public/css"]    ;; watch and update CSS

   ;; Instead of booting a separate server on its own port, we embed
   ;; the server ring handler inside figwheel's http-kit server, so
   ;; assets and API endpoints can all be accessed on the same host
   ;; and port. If you prefer a separate server process then take this
   ;; out and start the server with `lein run`.
   ;; :ring-handler   user/figwheel-http-handler

   ;; Start an nREPL server into the running figwheel process. We
   ;; don't do this, instead we do the opposite, running figwheel from
   ;; an nREPL process, see
   ;; https://github.com/bhauman/lein-figwheel/wiki/Using-the-Figwheel-REPL-within-NRepl
   ;; :nrepl-port 7888

   ;; To be able to open files in your editor from the heads up display
   ;; you will need to put a script on your path.
   ;; that script will have to take a file path and a line number
   ;; ie. in  ~/bin/myfile-opener
   ;; #! /bin/sh
   ;; emacsclient -n +$2 $1
   ;;
   ;; :open-file-command "myfile-opener"

   :server-logfile "log/figwheel.log"

   :builds         [{:id           "app_development"
                     :source-paths ["src"]
                     :figwheel     true
                     ;; Alternatively, you can configure a function to run every time figwheel reloads.
                     ;; :figwheel {:on-jsload "clojure-template.client/on-figwheel-reload"}
                     :compiler     {:main            "clojure-template.client.main"
                                    :asset-path      "js/app"
                                    :output-to       "resources/public/js/app.js"
                                    :output-dir      "resources/public/js/app"
                                    :closure-defines {"goog.DEBUG" true}}}]})

(defrecord FigwheelServer [config]
  component/Lifecycle

  (start [this]
    (figwheel/start-figwheel! config)
    this)

  (stop [this]
    (figwheel/stop-figwheel!)
    this))

(defn new-figwheel-server []
  (->FigwheelServer figwheel-config))
