(ns clojure-template.application-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [clojure-template.server :refer [http-handler]]))

(deftest get-todos
  (let [response (http-handler (mock/request :get "/"))
        body (:body response)]
    (is (= 200 (:status response)))
    (is (re-find #"<div id=\"app\"></div>" body))))
