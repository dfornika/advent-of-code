(ns aoc.2022.07.part-1
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.pprint :as pprint])
  (:gen-class))


(defn parse-terminal-output-line
  ""
  [line]
  (let [line-split (str/split line #"\s")]
    (cond (= \$ (first line)) {:line-type :command :executable (first (rest line-split)) :args (apply vector (rest (rest line-split)))}
          (= "dir" (first line-split)) {:line-type :listing :contents-type :directory :name (second line-split)}
          (number? (parse-long (first line-split))) {:line-type :listing :contents-type :file :name (second line-split) :size (parse-long (first line-split))}
        :else line-split)))


(defn find-parent-dir
  ""
  [state]
  (let [current-dir (:current-dir state)
        parent-dir-list (butlast (str/split (:current-dir state) #"/"))]
    (if (= '("") parent-dir-list)
      "/"
      (str/join "/" parent-dir-list))))


(defn execute-cd
  ""
  [state dir]
  (let [current-dir (:current-dir state)
        leading-slash (if (or (empty? current-dir) (= \/ (last current-dir))) "" "/")]
    (cond (= ".." dir) (assoc state :current-dir (find-parent-dir state))
          :else (update state :current-dir #(str % leading-slash dir)))))

  

(defn update-filesystem
  ""
  [state line]
  (let [fs (:filesystem state)
        current-dir (:current-dir state)
        basename (if (= (last current-dir) \/) (:name line) (str "/" (:name line)))]
    (cond (= :directory (:contents-type line))
          (assoc-in state [:filesystem (str current-dir basename)] {:type :directory})
          (= :file (:contents-type line))
          (assoc-in state [:filesystem (str current-dir basename)] {:type :file
                                                                    :size (:size line)}))))



(defn execute-command
  ""
  [state line]
  (let [args (:args line)
        exe (:executable line)]
    (cond (= "cd" exe) (execute-cd state (first args))
          (= "ls" exe) state)))


(defn apply-line
  ""
  [state line]
  (cond (= :command (:line-type line)) (execute-command state line)
        (= :listing (:line-type line)) (update-filesystem state line)))


(defn directory-size
  ""
  [state dir]
  (reduce + (map #(:size (second %)) (filter #(and (str/starts-with? (first %) dir) (= :file (:type (second %)))) (:filesystem state)))))

(defn spy
  [x]
  (pprint/pprint x)
  x)

(defn -main
  [& args]
  (let [input (str/split-lines (slurp *in*))]
    (->> input
         (map parse-terminal-output-line)
         (reduce apply-line {})
         (spy)
         ((fn [state] (map #(vector % (directory-size state %)) (map first (filter #(= :directory (:type (second %))) (:filesystem state))))))
         (spy)
         (filter #(>= 100000 (second %)))
         (spy)
         (reduce #(+ (second %2) %) 0)
         println)))


(comment
  (def example-input (->> (io/resource "07_example_input.txt")
                          slurp
                          str/split-lines))

  (def terminal-lines (map parse-terminal-output-line example-input))

  (def initial-state {})

  (def final-state (reduce apply-line initial-state terminal-lines))


  {:current-dir ""
   :filesystem {"/" {:type :directory}}}
   

  )
