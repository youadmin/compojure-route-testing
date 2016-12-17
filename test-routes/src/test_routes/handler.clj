(ns test-routes.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))


(comment (str
	"from professional clojure...."
	"(def body-routes (-> (route (GET etc) (wrap-routes mid-one))))"
	"(defroutes other-routes (..)"

	"and then combine them...."
	"(def app-routes (routes body-routes other-routes))"
	"finallly"
	"(def app (-> app-routes wrap-500-catchall (wrap-defaults api-defaults)))"
))
