(ns aoc.2019.03.part-2
  (:require [clojure.string :as str]
            [clojure.set :as set])
  (:gen-class))

(defn move-right
  ""
  [initial-state distance]
  (let [initial-x (get-in initial-state [:position :x])
        initial-length (get-in initial-state [:position :length])
        new-xs (range (inc initial-x) (+ initial-x (inc distance)))
        y (get-in initial-state [:position :y])
        new-points (set (map #(assoc {:y y} :x % :length (+ initial-length (- % initial-x))) new-xs))]
    (-> initial-state
        (update-in [:position :x] + distance)
        (update-in [:position :length] + distance)
        (assoc-in [:points] (set/union (:points initial-state) new-points)))))

(defn move-down
  ""
  [initial-state distance]
  (let [initial-y (get-in initial-state [:position :y])
        initial-length (get-in initial-state [:position :length])
        new-ys (range (- initial-y distance) initial-y)
        x (get-in initial-state [:position :x])
        new-points (set (map #(assoc {:x x} :y % :length (+ initial-length (- initial-y %))) new-ys))]
    (-> initial-state
        (update-in [:position :y] #(- % distance))
        (update-in [:position :length] + distance)
        (assoc-in [:points] (set/union (:points initial-state) new-points)))))

(defn move-left
  ""
  [initial-state distance]
  (let [initial-x (get-in initial-state [:position :x])
        initial-length (get-in initial-state [:position :length])
        new-xs (range (- initial-x distance) initial-x)
        y (get-in initial-state [:position :y])
        new-points (set (map #(assoc {:y y} :x % :length (+ initial-length (- initial-x %))) new-xs))]
    (-> initial-state
        (update-in [:position :x] - distance)
        (update-in [:position :length] + distance)
        (assoc-in [:points] (set/union (:points initial-state) new-points)))))

(defn move-up
  ""
  [initial-state distance]
  (let [initial-y (get-in initial-state [:position :y])
        initial-length (get-in initial-state [:position :length])
        new-ys (range (inc initial-y) (+ initial-y (inc distance)))
        x (get-in initial-state [:position :x])
        new-points (set (map #(assoc {:x x} :y % :length (+ initial-length (- % initial-y))) new-ys))]
    (-> initial-state
        (update-in [:position :y] +  distance)
        (update-in [:position :length] + distance)
        (assoc-in [:points] (set/union (:points initial-state) new-points)))))

(defn manhattan-distance
  [p1 p2]
  (let [x1 (:x p1)
        y1 (:y p1)
        x2 (:x p2)
        y2 (:y p2)]
    (+ (Math/abs (- x2 x1)) (Math/abs (- y2 y1)))))

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
      (move-up initial-state distance))))

(defn intersection-points
  [wire-1 wire-2]
  (set/intersection (set (map #(select-keys % [:x :y]) (:points wire-1)))
                    (set (map #(select-keys % [:x :y]) (:points wire-2)))))

(defn intersection-length
  [wire intersection-point]
  (filter #(and (= (:x intersection-point) (:x %))
                (= (:y intersection-point) (:y %)))
          (:points wire)))

(defn -main
  [& args]
  (def input-file "../inputs/test_input_03_small.txt")
  (def input-file *in*)
  (def wire-paths (mapv #(str/split % #",") (str/split (slurp input-file) #"\n")))

  (def origin {:x 0 :y 0 :length 0})
  (def initial-state {:position origin
                      :points #{}})

  (def wire-1
    (reduce add-points initial-state (first wire-paths)))

  (def wire-2
    (reduce add-points initial-state (second wire-paths)))

  (def wire-1-intersections
    (flatten (map (partial intersection-length wire-1) (intersection-points wire-1 wire-2))))

  (def wire-2-intersections
    (flatten (map (partial intersection-length wire-2) (intersection-points wire-1 wire-2))))

  (println
   (apply min
          (remove nil?
                  (for [a wire-1-intersections
                        b wire-2-intersections]
                    (if (and (= (:x a) (:x b))
                             (= (:y a) (:y b)))
                      (+ (:length a) (:length b))))))))

(comment
  (def wire-1-path ["R8" "U5" "L5" "D3"])
  (def wire-2-path ["U7" "R6" "D4" "L4"])
  (def wire-1-path ["R75" "D30" "R83" "U83" "L12" "D49" "R71" "U7" "L72"])
  (def wire-2-path ["U62" "R66" "U55" "R34" "D71" "R55" "D58" "R83"])
  (def wire-1-path ["R98" "U47" "R26" "D63" "R33" "U87" "L62" "D20" "R33" "U53" "R51"])
  (def wire-2-path ["U98" "R91" "D20" "R16" "D67" "R40" "U7" "R15" "U6" "R7"])
  (def origin {:x 0 :y 0 :length 0})
  (def initial-state  {:position origin :points #{}})
  (def wire-1
    (reduce add-points initial-state wire-1-path))
  (def wire-2
    (reduce add-points initial-state wire-2-path))
  
  
  )
