(ns lein-nrepl-rebl.core
  (:require [cognitect.rebl :as rebl]
            [nrepl.middleware :as nrepl]))


(defn wrap-rebl
  {::nrepl/descriptor {:requires #{#'eval}
                       :expects  #{}
                       :handles  {}}}
  [handler]
  (rebl/ui)
  (fn [{:keys [code values op] :as msg} & args]
    (println op code)
    (when (and (= op "eval") (some? code) #_(some? values))
      (do (println code)
          (try (rebl/submit (read-string code) (eval (read-string values))))))
    (handler msg)))


(nrepl/set-descriptor! #'wrap-rebl
                       {:requires #{#'eval}
                        :expects  #{}
                        :handles  {}})