# DynamicSpec

[![Build Status](https://travis-ci.org/brydoncheyney/dspec.png?branch=master)](https://travis-ci.org/brydoncheyney/dspec)

[![Clojars Project](http://clojars.org/dspec/latest-version.svg)](http://clojars.org/dspec)

Dynamic data validation using registered component specs.

## Usage

Components can register a [spec](https://clojure.org/guides/spec) against a keyword:

```clojure
(ns example.core
  (:require [dpsec.core :as dspec]))

  (s/def ::string string?)

  (defn -main [& args]
    (dspec/register :a ::string)
    (dspec/register :b ::string))
```

Data can then be validated against the registered component specs:

```clojure
    (dspec/validate {:a "foo" :b "bar"})
```

If data fails to conform to the registered component specs a `RuntimeException` will be thrown, with ex-data that contains the map of [s/explain-data](https://clojure.github.io/clojure/branch-master/clojure.spec-api.html#clojure.spec/explain-data), keyed by the given component keyword.

Using a component library such as [component](https://github.com/stuartsierra/component) or [mount](https://github.com/tolitius/mount):

```clojure
    (defstate a :start (dspec/register :a ::string))
```
```clojure
    (defstate b :start (dspec/register :b ::string))
```
```clojure
    (defstate config :start (dspec/validate data))
```

### Configuration Files

A utility function `load-config` exists to load and parse a yaml configuration file:

```clojure
    (config/load-config "resources/Service.yml")
```

This can be validated against any registered configuration specs:

```clojure
    (dspec/validate (config/load-config "resources/Service.yml"))
```

## Example

A service using [mount](https://github.com/tolitius/mount) to manage component state can be found in the simple [example app](https://github.com/brydoncheyney/dspec/tree/master/example). On service start, the configuration is validated against the specs defined for the component dependencies of the service.

## License

Copyright © 2017 Brydon Cheyney
