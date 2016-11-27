(ns clojure-template.client.components.todos.body
  (:require [re-frame.core :as rf]
            [clojure-template.client.components.todos.logic-bundle :as lb]))

(defn body-item-component [index todo]
  (let [{:keys [complete text]} todo
        complete-todo #(rf/dispatch (lb/complete-todo index))
        remove_todo #(rf/dispatch (lb/remove-todo index))]
    [:tr
     [:td
      (if complete [:s text] [:span text])]
     [:td
      [:button.btn.btn-xs.btn-success
       {:type "button" :on-click complete-todo}
       [:i.fa.fa-check]]]
     [:td
      [:button.btn.btn-xs.btn-danger
       {:type "button" :on-click remove_todo}
       [:i.fa.fa-remove]]]]))

(defn body-component
  [todos]
  [:div.col-md-12.todos-body
   [:table.table
    [:tbody
     (map-indexed
       (fn [index todo] ^{:key (:id todo)} [body-item-component index todo])
       todos)]]])
