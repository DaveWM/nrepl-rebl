(ns nrepl-rebl.core
  (:require [cognitect.rebl :as rebl]
            [clojure.tools.nrepl.middleware :as nrepl]
            [clojure.tools.nrepl.middleware.session :refer [session]]
            [clojure.tools.nrepl.transport :as transport])
  (:import (clojure.tools.nrepl.transport Transport)))


(defn form-from-cursive? [form]
  (and (sequential? form)
       (symbol? (first form))
       (= "cursive.repl.runtime" (namespace (first form)))))


(defrecord ReblTransport [transport handler-msg]
  Transport
  (recv [this timeout]
    (transport/recv transport timeout))
  (send [this {:keys [value] :as msg}]
    (transport/send transport msg)
    (when value
      (let [code-form (read-string (:code handler-msg))]
        (when-not (form-from-cursive? code-form)
          (rebl/submit code-form value))))
    this))


(defn wrap-rebl [handler]
  (rebl/ui)
  (fn [msg]
    (-> msg
        (update :transport ->ReblTransport msg)
        handler)))


(nrepl/set-descriptor! #'wrap-rebl
                       {:requires #{}
                        :expects #{"eval"}
                        :handles {}})
