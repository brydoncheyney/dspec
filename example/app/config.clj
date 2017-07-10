(ns app.config
  (:require [mount.core :refer [defstate]]
            [dspec.config :refer [load-config]]))

(defstate config :start (do
                          (prn "Loading config...")
                          (load-config "example/resources/app.yml")))
