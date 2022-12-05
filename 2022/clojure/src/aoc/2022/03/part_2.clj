(ns aoc.2022.03.part-2
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :as set]
            [aoc.2022.03.part-1 :as part-1])
  (:gen-class))


(defn -main
  [& args]
  (let [input (str/split-lines (slurp *in*))]
    (->> input
         (partition 3)
         (map part-1/common-elements)
         (map #(reduce set/union %))
         (map part-1/char->priority)
         (reduce +)
         println)))


(comment
  
  (def example-input (->> (io/resource "03_part_1_example_input.txt")
                          slurp
                          str/split-lines))

  (def common-elements-per-group (map part-1/common-elements (partition 3 example-input)))

  (reduce set/union common-elements-per-group)
)
