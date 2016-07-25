(ns clojure-template.helpers
  (:require [cheshire.core :refer :all]))

(defn parse-body [resp]
  (parse-string (slurp (:body resp)) true))
