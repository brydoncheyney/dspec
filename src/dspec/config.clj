(ns dspec.config
  (:require [clojure.java.io :as io]
            [yaml.core :as yaml]))

(defn- data
  [f]
  (when (.exists (io/file f))
    (slurp f)))

(defn- config
  [data]
  (when-not (nil? data)
    (yaml/parse-string data)))

(defn load-config [f] (-> f data config))
