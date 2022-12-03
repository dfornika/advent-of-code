(ns aoc.2022.02.part-2
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [aoc.2022.02.part-1 :as part-1])
  (:gen-class))

(def decoder-key {\A :rock
                  \B :paper
                  \C :scissors
                  \X :lose
                  \Y :draw
                  \Z :win})

(defn choose-move
  [opponent-move desired-outcome]
  (cond
    (= desired-outcome :lose) (part-1/defeats opponent-move)
    (= desired-outcome :draw) opponent-move
    (= desired-outcome :win) (part-1/defeats (part-1/defeats opponent-move))))

(defn replace-desired-outcome-with-move
  [[opponent-move desired-outcome]]
  (let [our-move (choose-move opponent-move desired-outcome)]
     [opponent-move our-move]))

(defn -main
  [& args]
  (let [input (str/split-lines (slurp *in*))]
    (->> input
         (map #(part-1/decode-line decoder-key %))
         (map replace-desired-outcome-with-move)
         (map part-1/score-round)
         (reduce +)
         println)))


(comment
  (def example-input (->> (io/resource "02_part_1_example_input.txt")
                          slurp
                          str/split-lines))
  (part-1/decode-char decoder-key \A)
  (part-1/decode-char decoder-key \X)

  (def rounds (map #(part-1/decode-line decoder-key %) example-input))

  (replace-desired-outcome-with-move (first rounds))
  
  (part-1/score-outcome (replace-desired-outcome-with-move (first rounds)))
  (score-outcome (second rounds))
  (score-outcome (nth rounds 2))

  (choose-move :rock :lose)
  (choose-move :rock :draw)
  (choose-move :rock :win)
  )
