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

(defn manhattan-distance
  [p1 p2]
  (let [x1 (:x p1)
        y1 (:y p1)
        x2 (:x p2)
        y2 (:y p2)]
    (+ (Math/abs (- x2 x1)) (Math/abs (- y2 y1)))
    ))

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
  ;; (def input-file "../input_1.txt")
  (def input-file *in*)
  (def wire-paths (mapv #(str/split % #",") (str/split (slurp input-file) #"\n")))

  (def origin {:x 0 :y 0})
  (def initial-state {:position origin
                      :points #{}})

  (def wire-1
    (loop [state initial-state
           path (first wire-paths)]
      (if (empty? path)
        (:points state)
        (recur (add-points state (first path))
               (rest path)))))

  (def wire-2
    (loop [state initial-state
           path (second wire-paths)]
      (if (empty? path)
        (:points state)
        (recur (add-points state (first path))
               (rest path)))))

  (prn (set/intersection wire-1 wire-2))
  #_(println (apply min (map (partial manhattan-distance origin) (set/intersection wire-1 wire-2)))))

(comment
  (def wire-1-path ["R75" "D30" "R83" "U83" "L12" "D49" "R71" "U7" "L72"])
  (def wire-2-path ["U62" "R66" "U55" "R34" "D71" "R55" "D58" "R83"])
  (def origin {:x 0 :y 0})
  (def initial-state  {:position origin :points #{}})
  (def wire-1
    (-> initial-state
      (add-points "R75")
      (add-points "D30")
      (add-points "R83")
      (add-points "U83")
      (add-points "L12")
      (add-points "D49")
      (add-points "R71")
      (add-points "U7")
      (add-points "L72")))
  (def wire-2
    (-> initial-state
        (add-points "U62")
        (add-points "R66")
        (add-points "U55")
        (add-points "R34")
        (add-points "D71")
        (add-points "R55")
        (add-points "D58")
        (add-points "R83")))
  (map (partial manhattan-distance origin) (set/intersection (:points wire-1) (:points wire-2)))
  
  )
