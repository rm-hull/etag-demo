(defproject etag-demo "0.0.1"
  :description "etag demo"
  :license {:name "The MIT License (MIT)"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [
    [org.clojure/clojure "1.6.0"]
    [compojure "1.1.8"]
    [ring "1.3.0-RC1"]
    [ring.middleware.etag "1.0.0-SNAPSHOT"]]
  :plugins [[lein-ring "0.8.10"]]
  :ring {:handler etag-demo.handler/app}
  :profiles {:uberjar {:aot :all}}
  :min-lein-version "2.3.4"
  :uberjar-exclusions [#"\.SF" #"\.RSA" #"\.DSA"]
  :global-vars {*warn-on-reflection* true})
