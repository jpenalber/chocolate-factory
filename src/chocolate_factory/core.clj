(ns chocolate-factory.core
  (:require 
    [ring.adapter.jetty :refer [run-jetty]]
    [chocolate-factory.routes :refer [my-routes]]
    [chocolate-factory.common :refer [parse-int]])
  (:gen-class))


(defn -main
  [& args]
  (if (first args)
    (run-jetty my-routes {:port (parse-int (first args))})
    (run-jetty my-routes {:port 3000}))) 
