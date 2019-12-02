(ns aoc.2019.01.core-test
  (:require [clojure.test :refer :all]
            [aoc.2019.01.core :refer :all]))

(deftest calculate-fuel-test-0
  (testing "Minimum mass for zero fuel"
    (is (= 0 (calculate-fuel 6)))))

(deftest calculate-fuel-test-1
  (testing "Example data"
    (is (= (reduce + [2 2 654 33583])
           (reduce + (map calculate-fuel [12 14 1969 100756]))))))
