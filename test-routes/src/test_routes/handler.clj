(ns test-routes.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]])
	(:require [ring.middleware.session :refer [wrap-session]]))

(defn wrap-one
	[handler]
	(fn [request]
		(do 
			(print "IN WRAP ONE\n")
			(handler request)
		)
	)
)

(defn wrap-two
	[handler]
	(fn [request]
		(do 
			(print "IN WRAP TWO\n")
			(handler request)
		)
	)
)


(def need-middle-routes
	(->
		(routes
  		(GET "/two" [] "Hello two WRAP One")
  		(GET "/one" [] "Hello one WRAP One"))
			(wrap-session)
			(wrap-routes wrap-one)
			(wrap-routes wrap-two)))

(defroutes no-middle-routes
  (GET "/no" [] "Hello no wrap")
  (route/not-found "Not Found"))

(def app-routes 
	(routes
		need-middle-routes
		no-middle-routes))

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
