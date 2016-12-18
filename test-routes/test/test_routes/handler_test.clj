(ns test-routes.handler-test
  (:require [clojure.test :refer :all]
						[midje.sweet :refer :all]
            [ring.mock.request :as mock]
            [test-routes.handler :refer :all]))

(facts "hello returns the correct status"
	(fact "the wrapped call returns 200"
		(let [
        nada (print "CALL FOR one, wrapped\n")
				resp 
					(app (mock/request :get "/one"))
				status
					(:status resp) 
			]
			status
		) => 200
	)
	(fact "the second wrapped call returns 200"
		(let [
        nada (print "CALL FOR two, wrapped\n")
				resp 
					(app (mock/request :get "/two"))
				status
					(:status resp) 
			]
			status
		) => 200
	)
	(fact "the unwrapped call returns 200"
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

