(ns clojure-template.store
  (:require [re-frame.core :refer [reg-event-db debug dispatch]]))

(def interceptors [(when ^boolean js/goog.DEBUG debug)])

(dispatch [:initialize-db])
