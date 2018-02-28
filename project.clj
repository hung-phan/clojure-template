(defproject clojure-template "1.0.0"
  :description "This is the todos template for clojures"
  :url "https://github.com/hung-phan/clojure-template"
  :license {:name "MIT"
            :url  "https://opensource.org/licenses/MIT"}

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.10.64"]
                 [org.clojure/data.generators "0.1.2"]
                 [org.clojure/core.async "0.3.465"]
                 [com.stuartsierra/component "0.3.2"]
                 [aleph "0.4.4"]
                 [ring "1.6.3"]
                 [ragtime "0.7.2"]
                 [ring/ring-defaults "0.3.1"]
                 [bk/ring-gzip "0.2.1"]
                 [ring.middleware.logger "0.5.0"]
                 [prone "1.1.4"]
                 [compojure "1.6.0"]
                 [metosin/compojure-api "1.1.11"]
                 [prismatic/schema "1.1.7"]
                 [org.clojure/java.jdbc "0.7.4"]
                 [hikari-cp "2.0.0"]
                 [org.postgresql/postgresql "42.1.4"]
                 [environ "1.1.0"]
                 [hiccup "1.0.5"]
                 [cheshire "5.8.0"]
                 [funcool/cats "2.1.0"]
                 [ns-tracker "0.3.1"]
                 [bidi "2.1.2"]
                 [garden "1.3.3"]
                 [reagent "0.7.0"]
                 [re-frame "0.10.2"]
                 [kibu/pushy "0.3.8"]
                 [cljs-http "0.1.44"]
                 [binaryage/devtools "0.9.8"]]

  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-environ "1.0.2"]]

  :min-lein-version "2.8.1"

  :source-paths ["src"]

  :test-paths ["test"]

  :clean-targets ^{:protect false} [:target-path :compile-path "resources/public/js" "resources/public/css"]

  :uberjar-name "clojure-template.jar"

  ;; Use `lein run` if you just want to start a HTTP server, without figwheel
  :main server.main

  ;; nREPL by default starts in the :main namespace, we want to start in `user`
  ;; because that's where our development helper functions like (run) and
  ;; (browser-repl) live.
  :repl-options {:init-ns user}

  :cljsbuild {:builds
              [{:id           "app_production"
                :source-paths ["src"]
                :jar          true
                :compiler     {:main            client.main
                               :output-to       "resources/public/js/app.js"
                               :output-dir      "target"
                               :optimizations   :advanced
                               :pretty-print    false
                               :closure-defines {"goog.DEBUG" false}}}

               {:id           "test"
                :source-paths ["src" "test"]
                :compiler     {:main          client.test-runner
                               :output-to     "resources/public/js/test.js"
                               :optimizations :none}}

               {:id           "prefetch_dependencies"
                :source-paths ["dev"]
                :compiler     {:main          local.prefetch-dependencies
                               :asset-path    "js/prefetch_dependencies"
                               :output-to     "resources/public/js/prefetch_dependencies.js"
                               :output-dir    "resources/public/js/prefetch_dependencies"
                               :optimizations :none}}]}

  :garden {:builds [{:id           "app_development"
                     :source-paths ["src"]
                     :stylesheet   client.styles/app
                     :compiler     {:output-to     "resources/public/css/app.css"
                                    :pretty-print? true}}

                    {:id           "app_production"
                     :source-paths ["src"]
                     :stylesheet   client.styles/app
                     :compiler     {:output-to     "resources/public/css/app.css"
                                    :pretty-print? false}}]}

  :doo {:build "test"}

  :profiles {:dev
             {:dependencies [[figwheel "0.5.14"]
                             [figwheel-sidecar "0.5.14"]
                             [com.cemerick/piggieback "0.2.2"]
                             [org.clojure/tools.nrepl "0.2.13"]
                             [ring/ring-mock "0.3.2"]
                             [midje "1.9.1"]]

              :plugins      [[lein-garden "0.2.8"]
                             [lein-figwheel "0.5.14"]
                             [lein-doo "0.1.8"]
                             [lein-midje "3.2.1"]
                             [lein-auto "0.1.3"]]

              :source-paths ["dev"]

              :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}

             :uberjar
             {:source-paths ^:replace ["src"]
              :prep-tasks   [["compile"]
                             ["cljsbuild" "once" "app_production"]
                             ["garden" "once" "app_production"]]
              :hooks        []
              :omit-source  true
              :aot          :all}})
