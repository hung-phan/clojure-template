(ns clojure-template.server.application-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [clojure-template.server.main :refer [http-handler]]))

(deftest get-todos
  (testing "GET /"
    (let [response (http-handler (mock/request :get "/"))
          body (:body response)]
      (is (= 200 (:status response)))
      (is (re-find #"<div id=\"app\"></div>" body)))))
