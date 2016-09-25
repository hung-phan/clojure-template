(ns clojure-template.components.todos.body)

(defn body-item-component [todo]
  (let [{:keys [complete text]} todo]
    [:tr
     [:td
      (if complete [:s text] [:span text])]
     [:td
      [:button.btn.btn-xs.btn-success {:type "button"} [:i.fa.fa-check]]]
     [:td
      [:button.btn.btn-xs.btn-danger {:type "button"} [:i.fa.fa-remove]]]]))

(defn body-component
  [todos]
  [:div.col-md-12.todos-body
   [:table.table
    [:tbody
     (for [todo todos] ^{:key todo} [body-item-component todo])]]])
