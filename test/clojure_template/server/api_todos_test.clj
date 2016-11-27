(ns clojure-template.server.api-todos-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [clojure-template.server.helpers :refer [parse-body]]
            [clojure-template.server.main :refer [http-handler]]))

(deftest get-todos
  (testing "GET /api/v1/todos"
    (let [response (http-handler (mock/request :get "/api/v1/todos"))
          body (parse-body response)]
      (is (= 200 (:status response)))
      (is (= 10 (count body))))))
