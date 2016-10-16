(ns clojure-template.components.static-page.main)

(defn static-page-component []
  [:div.container
   [:div:row
    [:div.col-md-12
     [:h1 "Static Page"]
     [:a {:href "/"} "Back to Home page"]]]])