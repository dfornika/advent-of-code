(ns aoc.2019.01.part-1
  (:gen-class))

(defn calculate-fuel
  [mass]
  (-> mass
      (/ 3)
      (Math/floor)
      (- 2)
      (int)))

(defn -main
  [& args]
  (let [masses (map #(Integer/parseInt %) (line-seq (java.io.BufferedReader. *in*)))]
    (println (reduce + (map calculate-fuel masses)))))


(comment
  (def masses [12 14 1969 100756])
  )
