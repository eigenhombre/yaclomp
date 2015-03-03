(ns yaclomp.core
  (:gen-class)
  (:require [yaclomp.parser :refer [parse-org]]
            [clojure.pprint]))


(defn -main [& [infile]]
  (if-not infile
    (println "No input file specified.")
    (->> infile
         slurp
         parse-org
         clojure.pprint/pprint)))
