(ns app.component
  (:require [clojure.spec.alpha :as s]
            [mount.core :refer  [defstate]]
            [dspec.core :refer [register]]
            [app.config :refer [config]]
            ))

(s/def ::host string?)
(s/def ::port integer?)
(s/def ::component-spec (s/keys :req-un [::host ::port]))

(defn- start
  [{:keys [component]}]
  (prn "Starting component...")
  (clojure.pprint/pprint component))

(defstate component :start (do
                             (start config)
                             (register :component ::component-spec)))
