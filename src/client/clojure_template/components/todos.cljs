(ns clojure-template.components.todos)

(def app-data
  [{:complete true :text "Yolo 1"}])

(defn todos-header []
  [:div.col-md-12
   [:h1 "Todos List"]])

(defn todos-body-item [todo]
  (let [{:keys [complete text]} todo]
    [:tr
     [:td
      (if complete [:s text] [:span text])]
     [:td
      [:button.btn.btn-xs.btn-success {:type "button"} [:i.fa.fa-check]]]
     [:td
      [:button.btn.btn-xs.btn-danger {:type "button"} [:i.fa.fa-remove]]]]))

(defn todos-body
  [todos]
  [:div.col-md-12.todos-body
   [:table.table
    [:tbody
     (for [todo todos] ^{:key todo} [todos-body-item todo])]]])

(defn todos []
  [:div.container
   [:div.row
    [todos-header]
    [todos-body app-data]]])
