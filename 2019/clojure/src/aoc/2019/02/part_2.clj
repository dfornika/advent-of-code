(ns aoc.2019.02.part-2
  (:require [clojure.string :as str])
  (:gen-class))

(defn opcode-1
  "Given a block of memory and an instruction pointer (ip):
    - Treat the next three values after (nth memory ip) as pointers into the program.
    - Dereference the first two pointers and sum their values
    - Dereference the third pointer and replace that value with the above sum.
    - return the updated program"
  [state]
  (let [{:keys [memory instruction-pointer halted]} state
        ip instruction-pointer
        parameters (take 3 (drop (inc ip) memory))]
    (assoc state :memory (assoc memory (last parameters) (+ (nth memory (first parameters))
                                                            (nth memory (second parameters))))
           :instruction-pointer (+ ip 1 (count parameters)))))

(defn opcode-2
    "Given a program and a program counter (pc):
    - Treat the next three values after (nth program pc) as pointers into the program.
    - Dereference the first two pointers and multiply their values
    - Dereference the third pointer and replace that value with the above sum.
    - return the updated program"
  [state]
  (let [{:keys [memory instruction-pointer halted]} state
        ip instruction-pointer
        parameters (take 3 (drop (inc ip) memory))]
    (assoc state :memory (assoc memory (last parameters) (* (nth memory (first parameters))
                                                            (nth memory (second parameters))))
           :instruction-pointer (+ ip 1 (count parameters)))))

(defn opcode-99
  "Halt computation"
  [state]
  (assoc state :halted true))

(defn initialize-memory
  [state noun verb]
  (let [memory (:memory state)]
    (assoc-in state [:memory] (assoc memory 1 noun 2 verb))))

(defn -main
  [& args]
  
  (def instruction-set {1 opcode-1
                        2 opcode-2
                        99 opcode-99})
  
  (def memory (mapv read-string (str/split (str/trim-newline (slurp *in*)) #",")))

  (def initial-state {:instruction-pointer 0
                      :memory memory
                      :halted false})

  (def desired-output 19690720)
  
  (defn compute
    [initial-state noun verb]
    (loop [state (initialize-memory initial-state noun verb)]
      (if (:halted state)
        {:state state :noun noun :verb verb}
        (recur ((instruction-set (nth (:memory state) (:instruction-pointer state))) state)))))

  (def desired-final-state
    (first (filter #(= desired-output (first (get-in % [:state :memory])))
                   (for [noun (range 100)
                         verb (range 100)]
                     (compute initial-state noun verb)))))
  
  (println (+ (:verb desired-final-state) (* 100 (:noun desired-final-state)))))



(comment
  
  (def memory [1 9 10 3 2 3 11 0 99 30 40 50])
  (def noun 9)
  (def verb 10)
  (def initial-state {:instruction-pointer 0 :memory memory :halted false})
  (compute initial-state noun verb)
  )
