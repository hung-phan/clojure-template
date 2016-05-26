(ns clojure-template.templates.layouts
  (:require [environ.core :refer [env]]
            [hiccup.page :refer [html5 include-css include-js]]))

(defn application [& content]
  (html5 [:head
          (include-css "css/style.css")
          [:body
           content
           (include-js "js/compiled/clojure_template.js")]]))