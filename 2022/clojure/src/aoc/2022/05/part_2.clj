(ns aoc.2022.05.part-2
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [aoc.2022.05.part-1 :as part-1])
  (:gen-class))



(defn apply-move
  "Move :quantity crates from one stack to another."
  [acc move]
  (let [{:keys [quantity from to]} move
        from-stack-size (count (get acc from []))
        num-remaining-crates-in-from-stack (- from-stack-size quantity)
        moved-crates (take-last quantity (get acc from))]
    #_(println acc)
    #_(println move)
    (-> acc
        (update from #(into [] (take num-remaining-crates-in-from-stack %)))
        (update to #(into [] (concat % moved-crates))))))


(defn -main
  [& args]
  (let [input (str/split-lines (slurp *in*))
        crate-stack-diagram-lines (first (split-with not-empty input))
        initial-state (part-1/parse-crate-stack-diagram crate-stack-diagram-lines)
        move-lines (drop 1 (second (split-with not-empty input)))
        moves (map part-1/parse-move-line move-lines)]
    (->> moves
         (reduce apply-move initial-state)
         (part-1/take-top-from-each-stack)
         (apply str)
         println)))

(comment

  
  )
