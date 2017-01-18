(defproject clojure-template "0.1.0"
  :description "This is the todos template for clojures"
  :url "https://github.com/hung-phan/clojure-template"
  :license {:name "MIT"
            :url  "https://opensource.org/licenses/MIT"}

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.293"]
                 [org.clojure/data.generators "0.1.2"]
                 [org.clojure/core.async "0.2.395"]
                 [com.stuartsierra/component "0.3.2"]
                 [http-kit "2.2.0"]
                 [ring "1.5.1"]
                 [ragtime "0.6.3"]
                 [ring/ring-defaults "0.2.2"]
                 [bk/ring-gzip "0.2.1"]
                 [ring.middleware.logger "0.5.0"]
                 [prone "1.1.4"]
                 [compojure "1.5.2"]
                 [metosin/compojure-api "1.1.10"]
                 [prismatic/schema "1.1.3"]
                 [org.clojure/java.jdbc "0.7.0-alpha1"]
                 [hikari-cp "1.7.5"]
                 [org.postgresql/postgresql "9.4.1212"]
                 [environ "1.1.0"]
                 [hiccup "1.0.5"]
                 [cheshire "5.7.0"]
                 [funcool/cats "2.0.0"]
                 [ns-tracker "0.3.1"]
                 [bidi "2.0.16"]
                 [garden "1.3.2"]
                 [reagent "0.6.0"]
                 [re-frame "0.9.1"]
                 [kibu/pushy "0.3.6"]
                 [cljs-http "0.1.42"]
                 [binaryage/devtools "0.8.3"]
                 [cljsjs/jquery "2.2.4-0"]
                 [cljsjs/bootstrap "3.3.6-1"]]

  :plugins [[lein-cljsbuild "1.1.3"]
            [lein-environ "1.0.1"]]

  :min-lein-version "2.6.1"

  :source-paths ["src"]

  :test-paths ["test"]

  :clean-targets ^{:protect false} [:target-path :compile-path "resources/public/js" "resources/public/css"]

  :uberjar-name "clojure-template.jar"

  ;; Use `lein run` if you just want to start a HTTP server, without figwheel
  :main clojure-template.server.main

  ;; nREPL by default starts in the :main namespace, we want to start in `user`
  ;; because that's where our development helper functions like (run) and
  ;; (browser-repl) live.
  :repl-options {:init-ns user}

  :cljsbuild {:builds
              [{:id           "app_production"
                :source-paths ["src"]
                :jar          true
                :compiler     {:main            clojure-template.client.main
                               :output-to       "resources/public/js/app.js"
                               :output-dir      "target"
                               :optimizations   :advanced
                               :pretty-print    false
                               :closure-defines {"goog.DEBUG" false}}}

               {:id           "test"
                :source-paths ["src" "test"]
                :compiler     {:main          clojure-template.client.test-runner
                               :output-to     "resources/public/js/test.js"
                               :optimizations :none}}

               {:id           "prefetch_dependencies"
                :source-paths ["dev"]
                :compiler     {:main          prefetch-dependencies
                               :asset-path    "js/prefetch_dependencies"
                               :output-to     "resources/public/js/prefetch_dependencies.js"
                               :output-dir    "resources/public/js/prefetch_dependencies"
                               :optimizations :none}}]}

  :garden {:builds [{:id           "app_development"
                     :source-paths ["src"]
                     :stylesheet   clojure-template.client.styles/app
                     :compiler     {:output-to     "resources/public/css/app.css"
                                    :pretty-print? true}}

                    {:id           "app_production"
                     :source-paths ["src"]
                     :stylesheet   clojure-template.client.styles/app
                     :compiler     {:output-to     "resources/public/css/app.css"
                                    :pretty-print? false}}]}

  :doo {:build "test"}

  :profiles {:dev
             {:dependencies [[figwheel "0.5.8"]
                             [figwheel-sidecar "0.5.8"]
                             [com.cemerick/piggieback "0.2.1"]
                             [org.clojure/tools.nrepl "0.2.12"]
                             [ring/ring-mock "0.3.0"]
                             [midje "1.8.3"]]

              :plugins      [[lein-garden "0.2.8"]
                             [lein-figwheel "0.5.2"]
                             [lein-doo "0.1.6"]
                             [lein-midje "3.2.1"]
                             [lein-auto "0.1.2"]]

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
