(ns etag-demo.handler
  (:use [compojure.core])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.etag.core :as etag]))

(defroutes app-routes
  (etag/with-etag (route/resources "/etag") {}))

(def app
  (->
    app-routes
    (handler/site)))
