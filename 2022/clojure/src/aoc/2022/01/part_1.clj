(ns aoc.2022.01.part-1
  (:require [clojure.java.io :as io]
            [clojure.string :as str])
  (:gen-class))


(defn parse-int
  [s]
  (Integer/parseInt s))

(defn group-calories-by-elf
  ""
  [calories-list]
  (->> calories-list
       (partition-by empty?)
       (filter #(not (every? empty? %)))
       (map #(map parse-int %) )
       (map-indexed vector)
       (into {})))

(defn sum-calories
  ""
  [calories-by-elf]
  (->> calories-by-elf
       (map #(into [] [(first %) (reduce + (second %))]))
       (into {})))


(defn -main
  [& args]
  (let [input (str/split-lines (slurp *in*))]
    (->> input
         group-calories-by-elf
         sum-calories
         (apply max-key val)
         println)))


(comment

  (def example-input (->> (io/resource "01_example_input.txt")
                          slurp
                          str/split-lines))

  (->> example-input
      group-calories-by-elf
      sum-calories
      (apply max-key val))

  )
