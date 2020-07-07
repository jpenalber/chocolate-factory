(ns chocolate-factory.routes
    (:require
        [compojure.core     :refer [routes GET POST]]
        [compojure.route    :refer [not-found]]
        [chocolate-factory.factory :refer [insert-wall walls compute-cached]]))

;define routes for the API
(def my-routes (routes
    (GET "/query" [] (str (compute-cached @walls)))
    (POST "/wall/:height{[0-9]+}" [height] (insert-wall height))
    (not-found "Error")))