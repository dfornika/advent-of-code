(ns aoc.core
  (:require [clojure.string :as str])
  (:gen-class))

(def input (line-seq (java.io.BufferedReader. *in*)))

(defn -main [& args]
  (dorun
   (map println input)))
