(ns client.test-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [client.core-test]))

(enable-console-print!)

(doo-tests 'client.core-test)
