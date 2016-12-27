(defproject test-sessions "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.1"]
								 [ring/ring-anti-forgery "1.0.1"]
							]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler test-sessions.handler/app}
  :profiles
  {:dev 
		{:dependencies [
				[javax.servlet/servlet-api "2.5"]
      	[ring/ring-mock "0.3.0"]
      	[ring "1.5.0"]
				[midje "1.6.3"]
			]}})
