(ns app.database
  (:require [clojure.spec.alpha :as s]
            [mount.core :refer [defstate]]
            [dspec.core :refer [register]]
            [app.config :refer [config]]))

(s/def ::database string?)
(s/def ::username string?)
(s/def ::database-spec (s/keys :req-un [::database ::username]))

(defn- start
  [{:keys [database]}]
  (prn "Starting database")
  (clojure.pprint/pprint database))

(defstate database :start (do
                             (start config)
                             (register :database ::database-spec)))
