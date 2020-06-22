(ns chocolate-factory.core
  (:require 
    [clojure.core.memoize :as memo]
    [ring.adapter.jetty :refer [run-jetty]]
    [compojure.core     :refer [routes GET POST]]
    [compojure.route    :refer [not-found]])
  (:gen-class))

(defn parse-int 
  "this function parse the input into a number (pic the first continuous number on the string)"
  [s]
  (Integer. (re-find  #"\d+" s )))

(def walls (ref []))

(defn compute
  "this function compute a wall list to find units of hard chocolate"
  [walls]
  (let [m-left 
        (rest (reduce 
          (fn [a b] (conj a (max (last a) b))) 
          [0] 
          walls)) 
      m-right 
        (reverse (rest (reduce 
          (fn [a b] (conj a (max (last a) b))) 
          [0] 
          (reverse walls))))]
  (reduce + 
  (map (fn [left right height] (- (min left right) height)) 
    m-left 
    m-right 
    walls))))

; cache of compute
(def compute-cached (memo/memo compute))

(defn insert_wall
  "this function adds walls to the list and clear old cached values"
  [height]
  (memo/memo-clear! compute-cached [@walls])
  ; update the walls global reference, receive a string from the request and pics the first continuous number
  (dosync (alter walls conj (parse-int height)))
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
