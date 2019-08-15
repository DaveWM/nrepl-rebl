# nrepl-rebl (alpha)
[![Clojars Project](https://img.shields.io/clojars/v/nrepl-rebl.svg)](https://clojars.org/nrepl-rebl)

nREPL middleware to send eval'd forms to REBL.

NOTE: REBL might not run if you use a JVM that does not support JavaFX.

## Usage

### With Leiningen

* [Download REBL](http://rebl.cognitect.com/download.html) (note the license agreement)
* Add the [lein-localrepo](https://github.com/kumarshantanu/lein-localrepo) plugin to your `profiles.clj`
* Run `lein localrepo install [path to rebl download]/REBL-0.9.108.jar com.cognitect/rebl 0.9.108`
* Merge this into your `profiles.clj`:
```clojure
{:user 
  :dependencies [[nrepl-rebl "0.1.1"]
                 [com.cognitect/rebl "0.9.108"]]
  :repl-options {:nrepl-middleware [nrepl-rebl.core/wrap-rebl]}}

```
* Open your repl as usual - a REBL window should open, and receive any forms you eval in the repl

### With deps.edn

* [Download REBL](http://rebl.cognitect.com/download.html) (note the license agreement)
* Merge the following into your `deps.edn` file:
```clojure
{:aliases {:nrepl {:extra-deps {nrepl/nrepl {:mvn/version "0.5.0"}}}
           :rebl {:extra-deps {org.clojure/clojure {:mvn/version "1.10.0-RC4"}
                               nrepl-rebl {:mvn/version "0.1.1"}
                               com.cognitect/rebl {:local/root "[path to REBL]/REBL-0.9.108.jar"}}}}}
```
* Run `clj -A:nrepl:rebl -m nrepl.cmdline --middleware '[nrepl-rebl.core/wrap-rebl]'` to start an nREPL server
* Connect to the nREPL server from a client of your choice (Cursive, CIDER, etc.)

## License

Distributed under the GPL V3 License
