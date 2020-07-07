(ns chocolate-factory.common)

(defn parse-int 
    "this function parse the input into a number (pic the first continuous number on the string)"
    [s]
    (Integer. (re-find  #"\d+" s )))
