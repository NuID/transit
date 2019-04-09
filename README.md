# nuid.transit

Cross-platform [transit](https://github.com/cognitect/transit-clj) (de)serialization for data structures across service and language boundaries.

## Requirements

[`jvm`](https://www.java.com/en/download/), [`node + npm`](https://nodejs.org/en/download/), [`clj`](https://clojure.org/guides/getting_started), [`shadow-cljs`](https://shadow-cljs.github.io/docs/UsersGuide.html#_installation)

## From Clojure and ClojureScript

This library smooths over the interface differences between `transit-clj` and `transit-cljs`, which are likely temporary idiosyncrasies. This library will be phased out if the `transit` implementations converge on a common API.

### tools.deps:

`{nuid/transit {:git/url "https://github.com/nuid/transit" :sha "..."}`

### usage:

```
$ clj # or shadow-cljs node-repl
=> (require '[nuid.transit :as transit])
=> (def rt {:round "trip"})
=> (def w (transit/write rt))
=> (def r (transit/read w))
=> (= rt r) ;; => true
```

## From Others

This library is meant to synergize with `transit` libraries in other languages ([`java`](https://github.com/cognitect/transit-java), [`javascript`](https://github.com/cognitect/transit-js), and [others](https://github.com/cognitect/transit-format#implementations)). This is primarly useful for (de)serialization of language-specific implementations of custom classes or types, such as elliptic curve points and big numbers.

## Contributing

Install [`git-hooks`](https://github.com/icefox/git-hooks) and fire away. Make sure not to get bitten by [`externs`](https://clojurescript.org/guides/externs) if modifying `npm` dependencies.

### formatting:

```
$ clojure -A:cljfmt            # check
$ clojure -A:cljfmt:cljfmt/fix # fix
```

### dependencies:

```
## check
$ npm outdated 
$ clojure -A:depot

## update
$ npm upgrade -s
$ clojure -A:depot:depot/update
```
