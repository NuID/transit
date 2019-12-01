<p align="right"><a href="https://nuid.io"><img src="https://nuid.io/svg/logo.svg" width="20%"></a></p>

# nuid.transit

Cross-platform [transit](https://github.com/cognitect/transit-clj) (de)serialization for data structures across service and language boundaries.

## Requirements

[`jvm`](https://www.java.com/en/download/), [`node + npm`](https://nodejs.org/en/download/), [`clj`](https://clojure.org/guides/getting_started), [`shadow-cljs`](https://shadow-cljs.github.io/docs/UsersGuide.html#_installation)

## Clojure and ClojureScript

This library smooths over the interface differences between `transit-clj` and `transit-cljs`, which are likely temporary idiosyncrasies. This library will be phased out if the `transit` implementations converge on a common API.

### tools.deps:

`{nuid/transit {:git/url "https://github.com/nuid/transit" :sha "..."}}`

### usage:

```
$ clj # or shadow-cljs node-repl
=> (require '[nuid.transit :as transit])
=> (def rt {:round "trip"})
=> (def w (transit/write rt))
=> (def r (transit/read w))
=> (= rt r) ;; => true
```

## Licensing

Apache v2.0 or MIT
