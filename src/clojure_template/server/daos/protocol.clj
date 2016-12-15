(ns clojure-template.server.daos.protocol)

(defprotocol DAO
  "Data access object protocol"

  (all [this] "Get all data for the current dao"))