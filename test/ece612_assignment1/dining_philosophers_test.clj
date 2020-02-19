(ns ece612-assignment1.dining-philosophers-test
  (:require [clojure.test :refer :all]
            [ece612-assignment1.dining-philosophers :refer :all]
            [ece612-assignment1.dining-philosophers :refer :all]
            [clj-time.core :as t]))

(defn get-num-blocked-threads
  "Gets the number of threads in BLOCKED state"
  [threads]
  (count (filter #(= % "BLOCKED") (map #(str (.getState %)) threads))))

(defn shutdown-threads
  "Shutdowns list of threads"
  [threads]
  (map #(.interrupt %) threads))

(defn get-num-forks-in-use
  "Number of forks in use"
  []
  (let [forks [fork1 fork2 fork3 fork4 fork5]]
    (count (filter #(true? %) (map #(:in-use %) forks)))))

(deftest test-blocked-threads-and-forks-in-use
  (testing "During the operation we shouldn't have all the threads blocked
    otherwise that means we have a deadlock. Also, forks we should have maximum of 4 forks in use"
    (let [threads (start)
          start-time (t/now)]
      (while (< (t/in-seconds (t/interval start-time (t/now))) 30);running for 30 seconds
        (do
          (is (< (get-num-blocked-threads threads) 5))
          (is (< (get-num-forks-in-use) 5))))
      (shutdown-threads threads))))
