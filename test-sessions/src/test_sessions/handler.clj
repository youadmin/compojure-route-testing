(ns test-sessions.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route])
	(:require [ring.middleware.anti-forgery :refer [wrap-anti-forgery]])
	(:require [ring.util.anti-forgery :refer [anti-forgery-field]])
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
		(POST "/post/one" 
			{session :session} 
			(println "session::: " session)
			"Hello POST one"
		)
		(GET "/one" 
			{session :session} 
			(println "session::: " session)
			{
				:status 200
				:headers {"Content-Type" "text/html; charset=utf-8"}
				:body 
					(str
						"<!DOCTYPE html>"
						"<form method=\"post\" action=\"/post/one\" accept-charset='UTF-8'>"
							(anti-forgery-field)
							"<input type='text' value='text input' name='go_time' />"
							"<button type=\"submit\" value=\"go\" />go</button>"
						"</form>"
					)
			}

		)
		(GET "/two" 
			{session :session} 
			(println "session::: " session)
			{
				:status 200
				:headers {"Content-Type" "text/html; charset=utf-8"}
				:body 
					(str
						"<!DOCTYPE html>"
						"<form method=\"post\" action=\"/post/one\" accept-charset='UTF-8'>"
							(anti-forgery-field)
							"<input type='text' value='text input' name='go_time' />"
							"<button type=\"submit\" value=\"go\" />go</button>"
						"</form>"
					)
			}

		)
	)
)

(defn custom-csrf-read-token
	[req]
		(get-in
			req
			[
			:session
			:ring.middleware.anti-forgery/anti-forgery-token])
)

(comment
(def wrapped-need-middle-routes
	(->
		need-middle-routes
		(wrap-routes wrap-anti-forgery)
		(wrap-routes wrap-session)
		(wrap-routes wrap-one))))

(defroutes no-middle-routes
  (GET "/no" [] "Hello no wrap")
  (route/not-found "Not Found"))

(def app-routes 
	(->
		(routes
			need-middle-routes
			no-middle-routes)
			(wrap-anti-forgery {:read-token custom-csrf-read-token})
			(wrap-session)
		))

(def app app-routes)

