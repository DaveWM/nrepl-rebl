(ns nrepl-rebl.core-test
  (:require [clojure.test :refer :all]
            [nrepl-rebl.core :refer :all]))


(deftest form-from-cursive?-test
  (is (not (form-from-cursive? 1)))
  (is (not (form-from-cursive? '(+ 1 1))))
  (is (not (form-from-cursive? nil)))
  (is (form-from-cursive? '(cursive.repl.runtime/completions 'clojure.pprint))))


(deftest read-string*-test
  (is (nil? (read-string* nil)))
  (is 1 (read-string* "1")))


(deftest wrap-rebl-has-meta
  (is (some-fn [:nrepl.middleware/descriptor :clojure.tools.nrepl.middleware/descriptor]
               (meta #'wrap-rebl))))
