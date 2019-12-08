(ns aoc.2019.02.part-1
  (:require [clojure.string :as str])
  (:gen-class))

(defn opcode-1
  "Given a program and a program counter (pc):
    - Treat the next three values after (nth program pc) as pointers into the program.
    - Dereference the first two pointers and sum their values
    - Dereference the third pointer and replace that value with the above sum.
    - return the updated program"
  [state]
  (let [{:keys [program program-counter halted]} state
        pc program-counter]
    (assoc state :program (assoc program (nth program (+ pc 3)) (+ (nth program (nth program (+ pc 1)))
                                                                   (nth program (nth program (+ pc 2)))))
           :program-counter (+ pc 4))))

(defn opcode-2
    "Given a program and a program counter (pc):
    - Treat the next three values after (nth program pc) as pointers into the program.
    - Dereference the first two pointers and multiply their values
    - Dereference the third pointer and replace that value with the above sum.
    - return the updated program"
  [state]
  (let [{:keys [program program-counter halted]} state
        pc program-counter]
    (assoc state :program (assoc program (nth program (+ pc 3)) (* (nth program (nth program (+ pc 1)))
                                                                   (nth program (nth program (+ pc 2)))))
           :program-counter (+ pc 4))))

(defn opcode-99
  "Halt computation"
  [state]
  (assoc state :halted true))


(defn -main
  [& args]
  
  (def instruction-set {1 opcode-1
                        2 opcode-2
                        99 opcode-99})
  
  (def program (mapv read-string (str/split (str/trim-newline (slurp *in*)) #",")))
  
  (def initial-state {:program-counter 0
                      :program (assoc program 1 12 2 2)
                      :halted false})

  (def final-state
    (loop [state initial-state]
      (if (:halted state)
        state
        (recur ((instruction-set (nth (:program state) (:program-counter state))) state)))))
 
  (println (nth (:program final-state) 0)))



(comment
  (def program [1 9 10 3 2 3 11 0 99 30 40 50])
  )
