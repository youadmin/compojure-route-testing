(ns test-sessions.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]])
	(:require [ring.middleware.session :refer [wrap-session]]))

;; steps::: 
;; in handler, do you see a session?
;; mock a session. you should see a session now
;; mock it with a property. can you see the property (say cart-id)?

(defn wrap-one
	[handler]
	(fn [request]
		(do 
			(print "IN WRAP ONE\n")
			(handler request)
		)
	)
)

(def need-middle-routes
	(routes
		(GET "/one" 
			{session :session} 
			(print "session::: " session)
			"Hello one WRAP One"
		)))

(def wrapped-need-middle-routes
	(->
		need-middle-routes
		(wrap-routes wrap-session)
		(wrap-routes wrap-one)))

(defroutes no-middle-routes
  (GET "/no" [] "Hello no wrap")
  (route/not-found "Not Found"))

(def app-routes 
	(routes
		wrapped-need-middle-routes
		no-middle-routes))

(def app app-routes)

