(ns aoc.2022.05.part-1
  (:require [clojure.java.io :as io]
            [clojure.string :as str])
  (:gen-class))

(defn parse-positions-line
  "Takes a line like ' 1   2   3 ', and returns
   a map where the label of the stack is mapped
   to its index in the line like {1 1, 2 5, 3 9}"
  [line]
  (->> line
       (map-indexed #(vector % (parse-long (str %2))))
       (filter second)
       (map reverse)
       (map #(into [] %))
       (into {})))


(defn parse-crates-line
  "Takes a line like '[N] [C]    ', and returns
   a map where the label of the stack is mapped
   to the label of the crate like {1 \\N, 2 \\C}"
  [stack-positions-map line]
  (let [char-seqs (for [stack-position (vals (into (sorted-map) stack-positions-map))]
                    (nth line stack-position))]
    (->> char-seqs
         (map-indexed vector)
         (filter #(not= \space (second %)))
         (map #(vector (inc (first %)) (second %)))
         (into {})
  )))


(defn add-crate
  "
  "
  [stacks crate]
  (let [stack-num (first crate)
        crate-label (second crate)]
    (if (contains? stacks stack-num)
      (update stacks stack-num conj crate-label)
      (assoc stacks stack-num [crate-label]))))


(defn add-crates
  ""
  [stacks crates]
  (reduce add-crate stacks crates))


(defn parse-crate-stack-diagram
  "Takes a sequence of lines like:
   '    [D]    '
   '[N] [C]    '
   '[Z] [M] [P]'
   ' 1   2   3 '
   ...and converts it to a sequence of maps
   from stack label to crate label like:
   ({2 \\D}
    {1 \\N 2 \\C}
    {1 \\Z 2 \\M 3 \\P})

  ...then into a structure like this:
  {1 [\\Z \\N]
   2 [\\M \\C \\D]
   3 [\\P]}
  "
  [lines]
  (let [positions-line (last lines)
        positions-map (parse-positions-line positions-line)
        crates-lines (reverse (butlast lines))
        crates-by-line (map #(parse-crates-line positions-map %) crates-lines)]
    (reduce add-crates {} crates-by-line)))


(defn parse-move-line
  "Takes a string like 'move 1 from 2 to 1'
   and returns a map like {:move 1 :from 2 :to 1}"
  [line]
  (let [tokens (str/split line #"\s")
        keyword-translation {:move :quantity
                             :from :from
                             :to :to}]
    (->> tokens
         (partition 2)
         (reduce #(assoc %1 (keyword-translation (keyword (first %2))) (parse-long (second %2))) {}))))



(defn apply-move
  "Move a single crate from one stack to another."
  [acc move]
  (let [{:keys [from to]} move
        moved-crate (last (get acc from))]
    (-> acc
        (update from #(apply vector (butlast %)))
        (update to #(conj % moved-crate)))))


(defn apply-moves
  "Move :quantity crates from one stack to another."
  [acc moves]
  (let [{:keys [quantity from to]} moves]
    #_(println acc)
    #_(println moves)
    (reduce apply-move acc (map #(select-keys % [:from :to]) (repeat quantity moves)))))


(defn take-top-from-each-stack
  "Takes stacks in a structure like this:
   {1 [\\Z \\N]
    2 [\\M \\C \\D]
    3 [\\P]}
  ...and returns the 'top' item from each stack:
    (\\N \\D \\P)
  "
  [stacks]
  (->> stacks
      (#(update-vals % last))
      (into (sorted-map))
      (map second)))

  

(defn -main
  [& args]
  (let [input (str/split-lines (slurp *in*))
        crate-stack-diagram-lines (first (split-with not-empty input))
        initial-state (parse-crate-stack-diagram crate-stack-diagram-lines)
        move-lines (drop 1 (second (split-with not-empty input)))
        moves (map parse-move-line move-lines)]
    (->> moves
         (reduce apply-moves initial-state)
         (take-top-from-each-stack)
         (apply str)
         println)))


(comment
  (def example-input (->> (io/resource "05_input_minimal.txt")
                          slurp
                          str/split-lines))

  (def crate-stack-diagram-lines (first (split-with not-empty example-input)))

  (let [input example-input
        crate-stack-diagram-lines (first (split-with not-empty input))
        initial-state (parse-crate-stack-diagram crate-stack-diagram-lines)
        move-lines (drop 1 (second (split-with not-empty input)))
        moves (map parse-move-line move-lines)]
    (->> moves
         (reduce apply-moves initial-state)
         (#(update-vals % last))
         (into (sorted-map))
         (map second)
         (apply str)
       ))
  )
