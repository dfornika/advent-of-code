(ns aoc.2022.04.part-2
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :as set]
            [aoc.2022.04.part-1 :as part-1])
  (:gen-class))


(defn any-overlap?
  ""
  [[set-1 set-2]]
  (or (not (empty? (set/intersection set-1 set-2)))
      (not (empty? (set/intersection set-1 set-2)))))


(defn -main
  [& args]
  (let [input (str/split-lines (slurp *in*))]
    (->> input
         (map #(str/split % #","))
       (map #(map part-1/expand-range %))
       (map #(map set %))
       (map any-overlap?)
       (map {false 0 true 1})
       (reduce +)
       println)))


(comment
  (def example-input (->> (io/resource "04_example_input.txt")
                          slurp
                          str/split-lines))

  (->> example-input
       (map #(str/split % #","))
       (map #(map part-1/expand-range %))
       (map #(map set %))
       (map any-overlap?)
       (map {false 0 true 1})
       (reduce +)
       )
  )
