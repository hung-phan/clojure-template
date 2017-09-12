(ns client.components.todos.add
  (:require [reagent.core :as rc]
            [re-frame.core :as rf]
            [client.components.todos.logic-bundle :as lb]))

(defn add-component []
  (let [todo (rc/atom "")
        update-todo #(reset! todo (-> % .-target .-value))
        add-todo #(do
                   (rf/dispatch (lb/add-todo @todo))
                   (reset! todo ""))]
    (fn []
      [:div.col-md-12
       [:div.form-inline
        [:div.form-group
         [:input.form-control {:type        "text"
                               :placeholder "Todo"
                               :value       @todo
                               :on-change   update-todo}]]
        [:button.btn.btn-success {:type     "button"
                                  :on-click add-todo}
         "Add Todo"]]])))
