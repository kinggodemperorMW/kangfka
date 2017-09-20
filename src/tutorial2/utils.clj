(ns tutorial2.utils)

(defn parse-int [s]
   (Integer. (re-find  #"\d+" s )))