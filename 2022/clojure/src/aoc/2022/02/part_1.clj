(ns aoc.2022.02.part-1
  (:require [clojure.java.io :as io]
            [clojure.string :as str])
  (:gen-class))



(def decoder-key {\A :rock
                  \B :paper
                  \C :scissors
                  \X :rock
                  \Y :paper
                  \Z :scissors})


(def move-scores {:rock 1
                  :paper 2
                  :scissors 3})


(def defeats {:rock :scissors
              :paper :rock
              :scissors :paper})


(defn decode-char
  ""
  [c]
  (get decoder-key c))


(defn decode-line
  ""
  [line]
  (let [chars (map first (str/split line #"\s"))]
    (map decode-char chars)))


(defn score-move
  ""
  [move]
  (get move-scores move)) 


(defn score-outcome
  "A round is a sequence of two keywords from #{:rock :paper :scissors}"
  [round]
  (let [opponent-move (first round)
        our-move (second round)]
    (cond
      (= (defeats opponent-move) our-move) 0
      (= opponent-move our-move) 3
      (= opponent-move (defeats our-move)) 6)))

(defn score-round
  [round]
  (let [our-move (second round)]
    (+ (score-move our-move)
       (score-outcome round))))


(defn -main
  [& args]
  (let [input (str/split-lines (slurp *in*))]
    (->> input
         (map decode-line)
         (map score-round)
         (reduce +)
         println)))


(comment

  (def example-input (->> (io/resource "02_part_1_example_input.txt")
                          slurp
                          str/split-lines))
  (decode \A)
  (def rounds (map decode-line example-input))

  (score-outcome (first rounds))
  (score-outcome (second rounds))
  (score-outcome (nth rounds 2))

  (score-move (second (first rounds)))
  (score-move (second (second rounds)))
  (score-move (second (nth rounds 2)))

  (map score-round rounds)
  )
