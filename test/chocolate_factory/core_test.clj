(ns chocolate-factory.core-test
  (:require [clojure.test :refer :all]
    [chocolate-factory.core :refer :all]))

(deftest a-test
(testing "Compute test" (is (= (compute [2 1 0 2 0]) 3)) 
(is (= (compute [0 2 0 1 2]) 3))
(is (= (compute [0 2 0 1]) 1))
(is (= (compute [1 0 2 0]) 1))
(is (= (compute [0 3 0 1 0 2 1 0 2 0]) 8))))
