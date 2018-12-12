(ns nrepl-rebl.core
  (:require [cognitect.rebl :as rebl]))


;; Compatibility with the legacy tools.nrepl and the new nREPL 0.4.x.
(if (find-ns 'clojure.tools.nrepl)
  (do (require
        '[clojure.tools.nrepl.middleware :refer [set-descriptor!]]
        '[clojure.tools.nrepl.transport :as transport]
        '[clojure.tools.nrepl.middleware.session :refer [session]])
      (import '(clojure.tools.nrepl.transport Transport)))
  (do (require
        '[nrepl.middleware :refer [set-descriptor!]]
        '[nrepl.transport :as transport]
        '[nrepl.middleware.session :refer [session]])
      (import '(nrepl.transport Transport))))


(defn form-from-cursive? [form]
  (and (sequential? form)
       (symbol? (first form))
       (= "cursive.repl.runtime" (namespace (first form)))))


(defn read-string* [s]
  (when s
    (read-string s)))


(defrecord ReblTransport [transport handler-msg]
  Transport
  (recv [this timeout]
    (transport/recv transport timeout))
  (send [this {:keys [value] :as msg}]
    (transport/send transport msg)
    (let [code-form (read-string* (:code handler-msg))]
      (when (and (some? value)
                 (not (form-from-cursive? code-form)))
        (rebl/submit code-form value)))
    transport))


(defn wrap-rebl [handler]
  (rebl/ui)
  (fn [msg]
    (-> msg
        (update :transport ->ReblTransport msg)
        handler)))


(set-descriptor! #'wrap-rebl
                 {:requires #{}
                  :expects #{"eval"}
                  :handles {}})
