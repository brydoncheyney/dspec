(defproject dspec "0.1.0"
  :description "Dynamic data validation using registered component specs"
  :url "http://github.com/brydoncheyney/dspec"
  :license  {:name "The MIT License"
             :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clojure-future-spec "1.9.0-alpha17"]]
  :signing  {:gpg-key "3F43809AE125AE7217006EB0BED6059AE9D61E7C"}
  :profiles  {:dev {:plugins [[lein-midje "3.2.1"]
                              [lein-ancient "0.6.10"]]
                    :dependencies [[midje "1.9.0-alpha6"]
                                   [io.forward/yaml "1.0.6"]]}})
