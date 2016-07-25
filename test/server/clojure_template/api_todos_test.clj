(ns clojure-template.api-todos-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [clojure-template.helpers :refer [parse-body]]
            [clojure-template.server :refer [http-handler]]))

(def response (http-handler (mock/request :get "/api/v1/todos")))

(def body (parse-body response))

(deftest get-todos
  (is (= (:status response) 200))
  (is (= 10 (count body))))
