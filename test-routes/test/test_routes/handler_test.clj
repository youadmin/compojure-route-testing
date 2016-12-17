(ns test-routes.handler-test
  (:require [clojure.test :refer :all]
						[midje.sweet :refer :all]
            [ring.mock.request :as mock]
            [test-routes.handler :refer :all]))

(facts "hello returns the correct status"
	(fact "the call returns 200"
		(let [
				resp 
					(app (mock/request :get "/"))
				status
					(:status resp) 
			]
			status
		) => 201
	)
)

