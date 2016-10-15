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
          (include-css "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css")
          (include-css "https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css")
          (include-css "/css/app.css")
          [:body
           content
           (include-js "https://code.jquery.com/jquery-2.2.4.min.js")
           (include-js "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js")
           (include-js "/js/app.js")]]))