(ns dspec.core-test
  (:require [midje.sweet :refer :all]
            [clojure.spec.alpha :as s]
            [dspec.core :refer :all]))

(defn- has-problems-with?
  "Returns true if a given spec has failed to conform and thrown an exception e"
  [spec e]
  (->> e ex-data vals (mapcat ::s/problems) (mapcat :via) (some #(= % spec))))

(against-background
  [(before :contents (reset))]
  (facts "about registering component configuration"
         (fact "can register a component"
               (register :a (s/def ::spec-a string?)) => {:a ::spec-a})
         (fact "will ignore a call to register a component that is already registered"
               (register :a (s/def ::spec-a string?)) => {:a ::spec-a})
         (fact "will register multiple components"
               (register :b (s/def ::spec-b string?)) => {:a ::spec-a :b ::spec-b})
         (fact "can deregister a registered component"
               (deregister :a) => {:b ::spec-b})
         (fact "will ignore a call to deregister a component that is not registered"
               (deregister :c) => {:b ::spec-b})
         (fact "can validate registered components"
               (validate {:b "string"}) => {:b "string"}
               (validate {:b 1}) => (throws RuntimeException (partial has-problems-with? ::spec-b))
               (validate {:a "string"}) => (throws RuntimeException (partial has-problems-with? ::spec-b)))
         (fact "when no components are registered validation will always be successful"
               (do (reset) (validate {:a 1})) => {:a 1})
         ))

;;
;; Component fixtures
;;

(s/def ::username string?)
(s/def ::password string?)
(s/def ::database string?)
(s/def ::host string?)

(s/def ::component-a (s/keys :req-un [::host ::database]))
(s/def ::component-b (s/keys :req-un [::host ::username ::password]))

(def ^:private component-a {:host "server.lxc" :database "db"})
(def ^:private component-b {:host "server.lxc" :username "user" :password "secret"})

(def ^:private has-problems-with-component-a? (partial has-problems-with? ::component-a))
(def ^:private has-problems-with-component-b? (partial has-problems-with? ::component-b))

(against-background

  [(before :contents (do (reset)
                         (register :a ::component-a)
                         (register :b ::component-b)))]

  (facts "about registering multiple component configurations"
         (fact "valid component configuration"
               (validate {:a component-a :b component-b}) => {:a component-a :b component-b})
         (fact "reports problems with a missing component"
               (validate {:a component-a}) => (throws RuntimeException has-problems-with-component-b?)
               (validate {}) => (throws RuntimeException has-problems-with-component-a?))
         (fact "reports problems with invalid component config"
               (validate
                 {:a component-a :b (dissoc component-b :username)}) => (throws RuntimeException has-problems-with-component-b?))
         (fact "validates multiple components"
               (validate {:a component-a :b component-b}) => {:a component-a :b component-b})
         (fact "reports problems with multiple components"
               (validate
                 {:a (dissoc component-a :database)
                  :b (dissoc component-b :username)}) => (throws RuntimeException
                                                                 has-problems-with-component-a?
                                                                 has-problems-with-component-b?))))
