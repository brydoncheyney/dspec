(ns dspec.config-test
  (:require [midje.sweet :refer :all]
            [clojure.java.io :as io]
            [yaml.core :as yaml]
            [dspec.config :refer [load-config]]))

(def ^:private f (io/as-file "."))

(facts "about loading service config files"
       (load-config ..filename..) => ..service..
       (provided
         (io/file ..filename..) => f
         (slurp ..filename..) => ..data..
         (yaml/parse-string ..data..) => ..service..))
