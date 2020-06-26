(ns chocolate-factory.core-test
  (:require [clojure.test :refer :all]
            [chocolate-factory.core :refer :all]
            [ring.mock.request :as mock]))

(deftest compute-test
(testing "Compute test" (is (= (compute [2 1 0 2 0]) 3)) 
(is (= (compute [0 2 0 1 2]) 3))
(is (= (compute [0 2 0 1]) 1))
(is (= (compute [1 0 2 0]) 1))
(is (= (compute [0 3 0 1 0 2 1 0 2 0]) 8))
(is (= (compute [0 0 0 0 0 0 0 0 0 0]) 0))
(is (= (compute []) 0))
(is (= (compute [2 2 2 2 2 2 2 2 2 2]) 0))
(is (= (compute [4 0 2 4 5 3 2 4 2 2 2 2 2 4 2 1 5 2 6 3 1 2 3 4 2]) 45))
(is (= (compute (reverse [4 0 2 4 5 3 2 4 2 2 2 2 2 4 2 1 5 2 6 3 1 2 3 4 2])) 45))
(is (= (compute [4 0 2 4 5 3 2 4 2 2 2 2 2 4 2 1 20 2 6 3 1 2 3 4 2]) 46))
(is (= (compute (reverse [4 0 2 4 5 3 2 4 2 2 2 2 2 4 2 1 20 2 6 3 1 2 3 4 2])) 46))))


(deftest api-test
  (testing "Test GET request to /hello?name={a-name} returns expected response"
    (let [response (my-routes (-> (mock/request :get  "/query")))
          body     (parse-int (:body response))]
      (is (= (:status response) 200))
      (is (= body 0)))
      
    (let [response (my-routes (-> (mock/request :post  "/wall/2")))
          body     (:body response)]
      (is (= (:status response) 200))
      (is (= body "OK")))

    (let [response (my-routes (-> (mock/request :post  "/wall/0")))
          body     (:body response)]
      (is (= (:status response) 200))
      (is (= body "OK")))

    (let [response (my-routes (-> (mock/request :post  "/wall/2")))
          body     (:body response)]
      (is (= (:status response) 200))
      (is (= body "OK")))
      
    (let [response (my-routes (-> (mock/request :get  "/query")))
          body     (parse-int (:body response))]
      (is (= (:status response) 200))
      (is (= body 2)))
      
    (let [response (my-routes (-> (mock/request :post  "/wall/2rd")))
          body     (:body response)]
      (is (= (:status response) 404))
      (is (= body "Error")))
      
    (let [response (my-routes (-> (mock/request :get  "/query")))
          body     (parse-int (:body response))]
      (is (= (:status response) 200))
      (is (= body 2)))
      
    (let [response (my-routes (-> (mock/request :post  "/wall/0")))
        body     (:body response)]
      (is (= (:status response) 200))
      (is (= body "OK")))

    (let [response (my-routes (-> (mock/request :post  "/wall/2")))
          body     (:body response)]
      (is (= (:status response) 200))
      (is (= body "OK")))
      
    (let [response (my-routes (-> (mock/request :get  "/query")))
          body     (parse-int (:body response))]
      (is (= (:status response) 200))
      (is (= body 4)))))