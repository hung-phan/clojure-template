(ns garden-development
  (:require [com.stuartsierra.component :as component]
            [suspendable.core :as suspendable]
            [clojure.java.io :as io]
            [garden.core]
            [me.raynes.fs :as fs]))

(defn- output-path [build]
  (-> build :compiler :output-to))

(defn- ensure-output-directory-exists [build]
  (let [dir (-> (output-path build)
                io/file
                fs/absolute
                fs/parent)]
    (when-not (fs/exists? dir)
      (when-not (fs/mkdirs dir)
        (throw (Exception. (format "Could not create directory %s" dir)))))))

(defn- prepare-build [build]
  (ensure-output-directory-exists build))

(defn- compile-builds [builds]
  (doseq [build builds]
    (prepare-build build)
    (try
      (let [stylesheet (eval (:stylesheet build))
            flags (:compiler build)]
        (println (str "Compiling with Garden " (pr-str (:output-to flags)) "..."))
        (println stylesheet)
        (garden.core/css flags stylesheet)
        (println "CSS compilation successful."))
      (catch Exception e
        (println "Error:" (.getMessage e))))
    (flush)))

(defrecord GardenServer [builds]
  component/Lifecycle
  (start [component]
    (println "Start garden for builds" builds)
    (compile-builds builds)
    component)
  (stop [component]
    component)

  suspendable/Suspendable
  (suspend [component]
    component)
  (resume [component _]
    (compile-builds builds)
    component))

(defn new-garden-server []
  ;; look for the configuration of garden in project.clj
  (map->GardenServer {:builds [{:id           "app"
                                :source-paths ["src"]
                                :stylesheet   "clojure-template.client.styles/app"
                                :compiler     {:output-to     "resources/public/css/app.css"
                                               :pretty-print? true}}]}))
