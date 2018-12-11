(ns lein-nrepl-rebl.core
  (:require [cognitect.rebl :as rebl]
            [clojure.tools.nrepl.middleware :as nrepl]
            [clojure.tools.nrepl.middleware.session :refer [session]]
            [clojure.tools.nrepl.transport :as transport]))


(defrecord ReblTransport [transport handler-msg]
  clojure.tools.nrepl.transport.Transport
  (recv [this timeout]
    (transport/recv transport timeout))
  (send [this msg]
    (transport/send transport msg)
    (when-let [val (:value msg)]
      (let [code-form (read-string (:code handler-msg))]
        (when (or (not (sequential? code-form))
                  (not (= 'cursive.repl.runtime/completions (first code-form))))
          (rebl/submit code-form (if (string? val)
                                   (read-string val)
                                   val)))))
    this))


(defn wrap-rebl [handler]
  (rebl/ui)
  (fn [msg]
    (-> (update msg :transport #(->ReblTransport % msg))
        handler)))


(nrepl/set-descriptor! #'wrap-rebl
                       {:requires #{#'session}
                        :expects #{"eval"}
                        :handles {}})
