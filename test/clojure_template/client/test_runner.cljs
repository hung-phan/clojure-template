(ns clojure-template.client.test-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [clojure-template.client.core-test]))

(enable-console-print!)

(doo-tests 'clojure-template.client.core-test)
