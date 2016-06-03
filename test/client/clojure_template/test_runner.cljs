(ns clojure-template.test-runner
  (:require
   [doo.runner :refer-macros [doo-tests]]
   [clojure-template.core-test]))

(enable-console-print!)

(doo-tests 'clojure-template.core-test)
