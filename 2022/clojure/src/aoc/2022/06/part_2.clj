(ns aoc.2022.06.part-2
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [aoc.2022.06.part-1 :as part-1])
  (:gen-class))


(defn -main
  [& args]
  (let [input (str/trim-newline (slurp *in*))]
    (->> input
         (part-1/find-marker 14)
         println)))


(comment

  (def example-input (->> (io/resource "06_example_input.txt")
                          slurp
                          str/trim-newline))

  (->> example-input
       (part-1/find-marker 14))
  )
