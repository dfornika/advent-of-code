(ns aoc.2019.04.part-1
  (:require [clojure.string :as str])
  (:gen-class))

(defn adjacent-digits
  [n]
  (if (= (count (str n))
         (count (dedupe (str n))))
    false
    true))

(defn digits-never-decrease
  [n]
  (let [digits (map #(Character/digit % 10) (str n))] 
    (every? true? (map #(<= (first %) (second %)) (partition 2 1 digits)))))

(defn -main
  [& args]
  ;; (def input-file "../inputs/input_04.txt")
  (def input-file *in*)
  (def password-range (map read-string (-> (slurp input-file)
                                           str/trim-newline
                                           (str/split #"-"))))

  (def passwords-meeting-criteria
    (filter (every-pred adjacent-digits digits-never-decrease)
            (range (first password-range) (inc (second password-range)))))

  (println (count passwords-meeting-criteria)))

(comment
  (def password-examples [111111 223450 123789])
  (map (every-pred adjacent-digits digits-never-decrease) password-examples)
  )
