# nrepl-rebl (alpha)

nREPL middleware to send eval'd forms to REBL.

## Usage

* [Download REBL](http://rebl.cognitect.com/download.html) (note the license agreement)
* Add the [lein-localrepo](https://github.com/kumarshantanu/lein-localrepo) plugin to your `profiles.clj`
* Run `lein localrepo install [path to rebl download]/REBL-0.9.108.jar com.cognitect/rebl 0.9.108`
* Merge this into your `profiles.clj`:
```clojure
{:user 
  :dependencies [[lein-nrepl-rebl "0.1.0-SNAPSHOT"]]
  :repl-options {:nrepl-middleware [lein-nrepl-rebl.core/wrap-rebl]}}

```
* Open your repl as usual - a REBL window should open, and receive any forms you eval in the repl

## License

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
