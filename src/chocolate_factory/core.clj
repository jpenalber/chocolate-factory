(ns chocolate-factory.core
  (:require 
    [ring.adapter.jetty :refer [run-jetty]]
    [compojure.core     :refer [routes GET POST]]
    [compojure.route    :refer [not-found]])
  (:gen-class))

(defn parse-int 
  "this function parse the input into a number (pic the first continuous number on the string)"
  [s]
  (Integer. (re-find  #"\d+" s )))

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

(defn insert_wall
  "this function adds walls to the list and clear old cached values"
  [height]
  (dosync
    ; clear cache
    (reset! cache nil) 
    ; update the walls global reference, receive a string from the request and pics the first continuous number
    (alter walls conj (parse-int height)))
  "OK")

;define routes for the API
(def my-routes (routes
  (GET "/query" [] (str (compute-cached @walls)))
  (POST "/wall/:height{[0-9]+}" [height] (insert_wall height))
  (not-found "Error")))

(defn -main
  [& args]
  (if (first args)
    (run-jetty my-routes {:port (parse-int (first args))})
    (run-jetty my-routes {:port 3000}))) 
