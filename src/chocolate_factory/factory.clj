(ns chocolate-factory.factory
    (:require 
        [chocolate-factory.common :refer [parse-int]]))

(def walls (ref []))

(def cache (atom nil))

(defn compute
    "this function compute a wall list to find units of hard chocolate"
    [walls]
    (let [m-left 
          ; find the highest wall on the left for each wall
          (rest (reverse (reduce 
            (fn [a b] (cons (max (first a) b) a)) 
            [0] 
            walls)))
        m-right 
          ; find the highest wall on the right for each wall
          (drop-last (reduce 
            (fn [a b] (cons (max (first a) b) a)) 
            [0] 
            (reverse walls)))]
    (reduce + 
    ; for each wall calculate the chocolate units on top of this wall
    ; it uses the formula: units = min(max-left, max-right) - wall-height
    (map (fn [left right height] (- (min left right) height)) 
      m-left 
      m-right 
      walls))))

(defn compute-cached
"cache of compute"
[walls]
(if @cache 
    @cache
    (let [units (compute walls)]
    (reset! cache  units)
    units)))

(defn insert-wall
"this function adds walls to the list and clear old cached values"
[height]
(dosync
    ; clear cache
    (reset! cache nil) 
    ; update the walls global reference, receive a string from the request and pics the first continuous number
    (alter walls conj (parse-int height)))
"OK")