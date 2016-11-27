(ns clojure-template.templates.layouts
  (:require [environ.core :refer [env]]
            [hiccup.page :refer [html5 include-css include-js]]))

(defn application [& content]
  (html5 [:head
          [:meta {:charset "utf-8"}]
          [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge"}]
          [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
          [:title "Application"]
          (include-css "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css")
          (include-css "https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css")
          (include-css "/css/app.css")
          [:body
           content
           (include-js "/js/app.js")]]))