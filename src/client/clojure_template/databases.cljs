(ns clojure-template.databases
  (:require [re-frame.core :refer [reg-event-db debug dispatch]]))

(def app-state {:todos []})

(def interceptors [(when ^boolean js/goog.DEBUG debug)])

(reg-event-db
  :initialize-db
  interceptors
  (fn [] app-state))

(dispatch [:initialize-db])
