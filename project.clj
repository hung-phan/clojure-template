(defproject clojure-template "0.1.0"
  :description "This is the todos template for clojures"
  :url "https://github.com/hung-phan/clojure-template"
  :license {:name "MIT"
            :url  "https://opensource.org/licenses/MIT"}

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.293"]
                 [org.clojure/data.generators "0.1.2"]
                 [org.clojure/core.async "0.2.395"]
                 [com.stuartsierra/component "0.3.1"]
                 [http-kit "2.2.0"]
                 [ring "1.5.0"]
                 [ring/ring-defaults "0.2.1"]
                 [bk/ring-gzip "0.1.1"]
                 [ring.middleware.logger "0.5.0"]
                 [compojure "1.5.1"]
                 [metosin/compojure-api "1.1.9"]
                 [prismatic/schema "1.1.3"]
                 [environ "1.1.0"]
                 [binaryage/devtools "0.8.3"]
                 [hiccup "1.0.5"]
                 [cheshire "5.6.3"]
                 [funcool/cats "2.0.0"]
                 [ns-tracker "0.3.1"]
                 [bidi "2.0.14"]
                 [garden "1.3.2"]
                 [reagent "0.6.0"]
                 [re-frame "0.8.0"]
                 [kibu/pushy "0.3.6"]
                 [cljs-http "0.1.42"]
                 [cljsjs/jquery "2.2.4-0"]
                 [cljsjs/bootstrap "3.3.6-1"]]

  :plugins [[lein-cljsbuild "1.1.3"]
            [lein-environ "1.0.1"]]

  :min-lein-version "2.6.1"

  :source-paths ["src/server" "src/client" "src/common"]

  :test-paths ["test/server"]

  :clean-targets ^{:protect false} [:target-path :compile-path "resources/public/js" "resources/public/css"]

  :uberjar-name "clojure-template.jar"

  ;; Use `lein run` if you just want to start a HTTP server, without figwheel
  :main clojure-template.server

  ;; nREPL by default starts in the :main namespace, we want to start in `user`
  ;; because that's where our development helper functions like (run) and
  ;; (browser-repl) live.
  :repl-options {:init-ns user}

  :cljsbuild {:builds
              [{:id           "app_development"
                :source-paths ["src/client" "src/common"]

                :figwheel     true
                ;; Alternatively, you can configure a function to run every time figwheel reloads.
                ;; :figwheel {:on-jsload "clojure-template.client/on-figwheel-reload"}

                :compiler     {:main            clojure-template.client
                               :asset-path      "js/app"
                               :output-to       "resources/public/js/app.js"
                               :output-dir      "resources/public/js/app"
                               :closure-defines {"goog.DEBUG" true}}}

               {:id           "app_production"
                :source-paths ["src/client" "src/common"]
                :jar          true
                :compiler     {:main            clojure-template.client
                               :output-to       "resources/public/js/app.js"
                               :output-dir      "target"
                               :optimizations   :advanced
                               :pretty-print    false
                               :closure-defines {"goog.DEBUG" false}}}

               {:id           "test"
                :source-paths ["src/client" "src/common" "test/client"]
                :compiler     {:main          clojure-template.test-runner
                               :output-to     "resources/public/js/test.js"
                               :optimizations :none}}

               {:id           "prefetch_dependencies"
                :source-paths ["dev"]
                :compiler     {:main          dev.prefetch-dependencies
                               :asset-path    "js/prefetch_dependencies"
                               :output-to     "resources/public/js/prefetch_dependencies.js"
                               :output-dir    "resources/public/js/prefetch_dependencies"
                               :optimizations :none}}]}

  ;; When running figwheel from nREPL, figwheel will read this configuration
  ;; stanza, but it will read it without passing through leiningen's profile
  ;; merging. So don't put a :figwheel section under the :dev profile, it will
  ;; not be picked up, instead configure figwheel here on the top level.

  :figwheel {;; :http-server-root "public"                  ;; serve static assets from resources/public/
             :server-port    3000                           ;; default
             ;; :server-ip "127.0.0.1"                      ;; default
             :css-dirs       ["resources/public/css"]       ;; watch and update CSS

             ;; Instead of booting a separate server on its own port, we embed
             ;; the server ring handler inside figwheel's http-kit server, so
             ;; assets and API endpoints can all be accessed on the same host
             ;; and port. If you prefer a separate server process then take this
             ;; out and start the server with `lein run`.
             :ring-handler   user/http-handler

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

             :server-logfile "log/figwheel.log"}

  :garden {:builds [{:id           "app_development"
                     :source-paths ["src/client"]
                     :stylesheet   clojure-template.styles/app
                     :compiler     {:output-to     "resources/public/css/app.css"
                                    :pretty-print? true}}

                    {:id           "app_production"
                     :source-paths ["src/client"]
                     :stylesheet   clojure-template.styles/app
                     :compiler     {:output-to     "resources/public/css/app.css"
                                    :pretty-print? false}}]}

  :doo {:build "test"}

  :profiles {:dev
             {:dependencies [[figwheel "0.5.8"]
                             [figwheel-sidecar "0.5.8"]
                             [com.cemerick/piggieback "0.2.1"]
                             [org.clojure/tools.nrepl "0.2.12"]
                             [ring/ring-mock "0.3.0"]
                             [prone "1.1.4"]
                             [midje "1.8.3"]]

              :plugins      [[lein-ring "0.9.7"]
                             [lein-garden "0.2.8"]
                             [lein-figwheel "0.5.2"]
                             [lein-doo "0.1.6"]
                             [lein-midje "3.2.1"]
                             [lein-auto "0.1.2"]]

              :ring         {:handler               clojure-template.server/http-handler
                             :stacktrace-middleware prone.middleware/wrap-exceptions}

              :source-paths ["dev"]

              :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}

             :uberjar
             {:source-paths ^:replace ["src/server" "src/common"]
              :prep-tasks   [["compile"]
                             ["cljsbuild" "once" "app_production"]
                             ["garden" "once" "app_production"]]
              :hooks        []
              :omit-source  true
              :aot          :all}})
