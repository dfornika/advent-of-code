(ns aoc.2022.01.part-2
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [aoc.2022.01.part-1 :as part-1])
  (:gen-class))

(defn -main
  [& args]
  (let [input (str/split-lines (slurp *in*))]
    (->> input
         part-1/group-calories-by-elf
         part-1/sum-calories
         (sort-by val)
         reverse
         (take 3)
         (reduce #(+ % (second %2)) 0)
         println)))

(comment
  
  (def example-input (->> (io/resource "01_part_1_example_input.txt")
                          slurp
                          str/split-lines))

  (->> example-input
         part-1/group-calories-by-elf
         part-1/sum-calories
         (sort-by val)
         reverse
         (take 3)
         (reduce #(+ % (second %2)) 0)
         )
  )
