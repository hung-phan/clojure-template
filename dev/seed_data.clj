(ns seed-data
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.data.generators :as generators]))

(defn seed-todos [database]
  (let [todos (repeatedly 10 (fn [] {:text     (generators/string)
                                     :complete (generators/boolean)}))]
    (println "Seeding todos data")

    ;; initialize seeding data
    (jdbc/with-db-connection
      [conn {:datasource (:datasource database)}]
      (let [rows (jdbc/insert-multi! conn :todos todos)]
        (print rows)))))
