(ns server.api-todos-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [server.main-test :refer [with-test-system]]))

(deftest api-todos-test
  (with-test-system
    (fn [test-system]
      (testing "GET /api/v1/todos"
        (let [handler (-> test-system :http-handler :handler)
              response (handler (mock/request :get "/api/v1/todos"))]
          (testing "should respond with 200 HTTP status"
            (is (= 200 (:status response)))))))))
