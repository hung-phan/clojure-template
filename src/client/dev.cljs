(ns client.dev
  (:require [devtools.core :as devtools]))

;; enable console log debugging using devtools
(when ^boolean js/goog.DEBUG
  (devtools.core/set-pref! :dont-detect-custom-formatters true)
  (devtools/install!))
