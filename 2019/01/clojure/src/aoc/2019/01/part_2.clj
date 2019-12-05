(ns aoc.2019.01.part-2
  (:gen-class))

(defn calculate-fuel
  [mass]
  (-> mass
      (/ 3)
      (Math/floor)
      (- 2)
      (int)))

(defn calculate-total-fuel
  [initial-mass]
  (loop [fuel-mass initial-mass
         fuel-masses []]
    (if (neg? (calculate-fuel fuel-mass))
      (reduce + fuel-masses)
      (recur (calculate-fuel fuel-mass) (conj fuel-masses (calculate-fuel fuel-mass))))))

(defn -main
  [& args]
  (let [masses (map #(Integer/parseInt %) (line-seq (java.io.BufferedReader. *in*)))]
    (println (reduce + (map calculate-total-fuel masses)))))


(comment
  (def masses [12 14 1969 100756])
  )
