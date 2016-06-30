(ns clojure-template.templates.layouts
  (:require [environ.core :refer [env]]
            [hiccup.page :refer [html5 include-css include-js]]
            [ring.middleware.anti-forgery :refer [*anti-forgery-token*]]))

(defn application [& content]
  (html5 [:head
          [:meta {:charset "utf-8"}]
          [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge"}]
          [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
          [:meta {:csrf *anti-forgery-token*}]
          [:title "Application"]
          (include-css "css/app.css")
          [:body
           content
           (include-js "js/compiled/clojure_template.js")]]))