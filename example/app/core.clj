(ns app.core
  (:require [clojure.spec.alpha :as s]
            [mount.core :refer [defstate]]
            [dspec.core :refer [validate]]
            [app.database :refer [database]]
            [app.component :refer [component]]
            [app.config :refer [config]]))

(defn- start [config]
  (prn "Starting application...")
  (clojure.pprint/pprint config)
  (try
    (validate config)
    (catch Exception e
      (->> e ex-data (str "Invalid configuration - ") prn))))

(defstate app :start (start config))
