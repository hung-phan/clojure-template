(ns client.store
  (:require [re-frame.core :as rc]))

(def interceptors [(when ^boolean js/goog.DEBUG rc/debug)])

(rc/dispatch [:initialize-db])
