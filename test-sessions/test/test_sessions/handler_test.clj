(ns test-sessions.handler-test
  (:require [clojure.test :refer :all]
						[midje.sweet :refer :all]
            [ring.mock.request :as mock]
            [test-sessions.handler :refer :all])
 (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]])
	(:require [ring.middleware.session :refer [wrap-session]])
	(:require [ring.middleware.session.store :refer [SessionStore]])
	(:import [java.util UUID]))


(deftype MyMemoryStore [session-map]
  SessionStore
  (read-session [_ key]
		{:cart-id "cart:id:1"})
  (write-session [_ key data])
  (delete-session [_ key]))


(defn build-mock-app
	[{:keys [session-options]}]
	(let [
			mock-wrapped-routes
				(->
					need-middle-routes
					(wrap-routes wrap-session session-options)
					(wrap-routes wrap-one))
		]
		(routes
			mock-wrapped-routes
			no-middle-routes)
	)
)

(facts "hello returns the correct status"
	(fact "the wrapped call returns 200"
		(let [
				wrap-session-options {
						:store (MyMemoryStore. (atom {}))
						:cookie-name "ring-session"
						:cookie-attrs {:path "/" :http-only true}
				}
        nada (print "CALL FOR one, wrapped\n")
				mck-app 
					(build-mock-app 
						{:session-options wrap-session-options})
				resp 
					(mck-app (mock/request :get "/one"))
				status
					(:status resp) 
			]
			status
		) => 200
	)
	(future-fact "the unwrapped call returns 200"
		(let [
        nada (print "CALL FOR no, unwrapped\n")
				resp 
					(app (mock/request :get "/no"))
				status
					(:status resp) 
			]
			status
		) => 200
	)
)

