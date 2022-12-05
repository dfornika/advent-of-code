(ns aoc.2022.03.part-1
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :as set])
  (:gen-class))


(defn invert-case
  ""
  [c]
  (if (Character/isUpperCase c)
    (first (str/lower-case c))
    (first (str/upper-case c))))


(defn char->priority
  ""
  [c]
  (let [shift (if (Character/isUpperCase c) 6 0)
        c-inverted-case (invert-case c)
        ascii-val (int c-inverted-case)]
    (- ascii-val 64 shift)))


(defn split-in-half
  ""
  [s]
  (let [input-len (count s)
        middle (Math/floor (/ input-len 2))]
    (split-at middle s)))


(defn common-elements
  ""
  [colls]
  (let [sets (map set colls)]
    (apply set/intersection sets)))


(defn -main
  [& args]
  (let [input (str/split-lines (slurp *in*))]
    (->> input
         (map split-in-half)
         (map common-elements)
         (map #(reduce set/union %))
         (map char->priority)
         (reduce +)
         println)))


(comment
  (def example-input (->> (io/resource "03_part_1_example_input.txt")
                          slurp
                          str/split-lines))

  (split-in-half (first example-input))
  (def rucksacks (map split-in-half example-input))
  (def shared-elements (map common-elements rucksacks))
  (apply set/union shared-elements)
  )
