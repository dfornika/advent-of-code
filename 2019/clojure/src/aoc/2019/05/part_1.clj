(ns aoc.2019.05.part-1
  (:require [clojure.string :as str])
  (:gen-class))

(defn digits
  [number]
  (map #(Character/digit % 10) (str number)))

(defn get-opcode
  [instruction]
  (last (digits instruction)))

(defn get-parameter-modes
  [instruction]
  (let [modes (drop-last 2 (digits (format "%05d" instruction)))
        modes-map {0 :position
                   1 :immediate}]
     (reverse (map modes-map modes))))

(defn get-operands
  [state]
  (let [{:keys [memory instruction-pointer halted]} state
        ip instruction-pointer
        parameter-modes (get-parameter-modes (nth memory ip))
        ]
    (map
     (cond (= parameter-mode :position) (nth memory parameter)
           (= parameter-mode :immediate) parameter))))

(defn opcode-1
  "Given a block of memory and an instruction pointer (ip):
    - Treat the next three values after (nth memory ip) as pointers into the program.
    - Dereference the first two pointers and sum their values
    - Dereference the third pointer and replace that value with the above sum.
    - return the updated program"
  [state]
  (let [{:keys [memory instruction-pointer halted]} state
        ip instruction-pointer
        parameters (take 3 (drop (inc ip) memory))
        
        operands (get-operands state)]
    (assoc state :memory (assoc memory (last parameters) (reduce + operands)
                                :instruction-pointer (+ ip 1 (count parameters)))))

(defn opcode-2
    "Given a program and an instruction pointer (ip):
    - Treat the next three values after (nth memory ip) as pointers into the program.
    - Dereference the first two pointers and multiply their values
    - Dereference the third pointer and replace that value with the above product.
    - return the updated program"
  [state]
  (let [{:keys [memory instruction-pointer halted]} state
        ip instruction-pointer
        parameters (take 3 (drop (inc ip) memory))
        parameter-modes (get-parameter-modes (nth memory ip))
        operands (get-operands state)]
    (assoc state :memory (assoc memory (last parameters) (reduce * operands)
                                :instruction-pointer (+ ip 1 (count parameters)))))

(defn opcode-3
  [state]
  (print "input: ")
  (let [{:keys [memory instruction-pointer halted]} state
        ip instruction-pointer
        parameters (take 1 (drop (inc ip) memory))
        input (read-string (read-line))]
      (assoc state :memory (assoc memory ip input)
                :instruction-pointer (+ ip 1 (count parameters)))))

(defn opcode-4
  [state]
  (let [{:keys [memory instruction-pointer halted]} state
        ip instruction-pointer
        parameters (take 1 (drop (inc ip) memory))]
    (println (first parameters))
    (assoc state :halted true)))

(defn opcode-99
  "Halt computation"
  [state]
  (assoc state :halted true))


(defn -main
  [& args]
    
  (def instruction-set {1 opcode-1
                        2 opcode-2
                        3 opcode-3
                        4 opcode-4
                        99 opcode-99})
  
  (def memory (mapv read-string (str/split (str/trim-newline (slurp *in*)) #",")))

  (def initial-state {:instruction-pointer 0
                      :memory memory
                      :halted false})
  
  (defn compute
    [state instruction-set]
    (loop [state state]
        (if (:halted state)
          state
          (recur ((instruction-set (get-opcode (nth (:memory state) (:instruction-pointer state)))) state)))))


  )



(comment
  
  (def memory [3 0 4 0 99])
  (def initial-state {:instruction-pointer 0 :memory memory :halted false})
  (compute initial-state instruction-set)
  )
