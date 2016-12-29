(ns clojure-template.server.application-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [clojure-template.server.main-test :refer [with-test-system]]))

(deftest application-test
  (with-test-system
    (fn [test-system]
      (testing "GET /"
        (let [handler (-> test-system :http-handler :handler)
              response (handler (mock/request :get "/"))
              body (:body response)]
          (testing "should respond with 200 HTTP status"
            (is (= 200 (:status response))))

          (testing "should return div with id app"
            (is (re-find #"<div id=\"app\"></div>" body))))))))
