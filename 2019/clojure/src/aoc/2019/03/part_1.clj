(ns aoc.2019.03.part-1
  (:require [clojure.string :as str]
            [clojure.set :as set])
  (:gen-class))

(defn move-right
  ""
  [initial-state distance]
  (let [new-xs (range (inc (get-in initial-state [:position :x])) (inc distance))
        y (get-in initial-state [:position :y])
        new-points (set (map #(assoc {:y y} :x %) new-xs))]
    (-> initial-state
        (update-in [:position :x] #(+ % distance))
        (assoc-in [:points] (set/union (:points initial-state) new-points)))))

(defn move-down
  ""
  [initial-state distance]
  (let [new-ys (range (- distance) (get-in initial-state [:position :y]))
        x (get-in initial-state [:position :x])
        new-points (set (map #(assoc {:x x} :y %) new-ys))]
    (-> initial-state
        (update-in [:position :y] #(- % distance))
        (assoc-in [:points] (set/union (:points initial-state) new-points)))))

(defn move-left
  ""
  [initial-state distance]
  (let [new-xs (range (- distance) (get-in initial-state [:position :x]))
        y (get-in initial-state [:position :y])
        new-points (set (map #(assoc {:y y} :x %) new-xs))]
    (-> initial-state
        (update-in [:position :x] #(- % distance))
        (assoc-in [:points] (set/union (:points initial-state) new-points)))))

(defn move-up
  ""
  [initial-state distance]
  (let [new-ys (range (inc (get-in initial-state [:position :y])) (inc distance))
        x (get-in initial-state [:position :x])
        new-points (set (map #(assoc {:x x} :y %) new-ys))]
    (-> initial-state
        (update-in [:position :y] #(+ % distance))
        (assoc-in [:points] (set/union (:points initial-state) new-points)))))

(defn add-points
  [initial-state movement]
  (let [direction (first movement)
        distance (read-string (apply str (rest movement)))]
    (cond
      (= direction \R)
      (move-right initial-state distance)
      (= direction \D)
      (move-down initial-state distance)
      (= direction \L)
      (move-left initial-state distance)
      (= direction \U)
      (move-up initial-state distance)
      )))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (def input-file "../input_1.txt")
  #_(def input-file *in*)
  (def wire-paths (mapv #(str/split % #",") (str/split (slurp input-file) #"\n")))
  
  (prn wire-paths))

(comment
  (def wire-1-path ["R75" "D30" "R83" "U83" "L12" "D49" "R71" "U7" "L72"])
  (def wire-2-path ["U62" "R66" "U55" "R34" "D71" "R55" "D58" "R83"])
  (def origin {:x 0 :y 0})
  (def initial-state  {:position origin :points #{origin}})
  (add-points initial-state "U5")
  )
