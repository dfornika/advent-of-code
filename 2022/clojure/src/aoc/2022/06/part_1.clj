(ns aoc.2022.06.part-1
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :as set])
  (:gen-class))




(defn all-unique?
  ""
  [coll]
  (= (count coll) (count (set coll))))



(defn find-marker
  ""
  [window-size datastream]
  (loop [current-element (first datastream)
         remaining-datastream (rest datastream)
         processed-datastream []
         sliding-window []]
    (if (or (empty? remaining-datastream)
            (and (>= (count sliding-window) window-size) (all-unique? sliding-window)))
      #_[(count processed-datastream) processed-datastream sliding-window]
      (count processed-datastream)
      (recur (first remaining-datastream)
             (rest remaining-datastream)
             (conj processed-datastream current-element)
             (if (< (count sliding-window) window-size)
               (conj sliding-window current-element)
               (conj (into [] (rest sliding-window)) current-element))))))

(defn -main
  [& args]
  (let [input (str/trim-newline (slurp *in*))]
    (->> input
         (find-marker 4)
         println)))


(comment
  (def example-input (->> (io/resource "06_example_input.txt")
                          slurp
                          str/trim-newline))

  (->> example-input
      (find-marker 4))
  
  )

