(ns aoc.2022.04.part-1
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :as set])
  (:gen-class))


(defn expand-range
  ""
  [range-str]
  (let [[min max] (map parse-long (str/split range-str #"-"))]
    (range min (inc max))))


(defn one-fully-contains-another
  ""
  [[set-1 set-2]]
  (or (empty? (set/difference set-1 set-2))
      (empty? (set/difference set-2 set-1))))


(defn -main
  [& args]
  (let [input (str/split-lines (slurp *in*))]
    (->> input
         (map #(str/split % #","))
         (map #(map expand-range %))
         (map #(map set %))
         (map one-fully-contains-another)
         (map {false 0 true 1})
         (reduce +)
         println)))
         


(comment

  (def example-input (->> (io/resource "04_example_input.txt")
                          slurp
                          str/split-lines))

  (->> example-input
       (map #(str/split % #","))
       (map #(map expand-range %))
       (map #(map set %))
       (map one-fully-contains-another)
       (map {false 0 true 1})
       (reduce +)
       )

  )
