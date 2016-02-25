(ns clj-stable-pprint.core
  (:require [clojure.pprint]))

(defn ^:private fmap
  "implementation of fmap that ensures the return type is an array-map"
  [f m]
  (apply array-map
    (apply concat
      (for [[k v] m] [k (f v)]))))

(declare stabilize)

(defn ^:private stabilize-map*
  [a]
  (let [sorted-map (->> a
                        seq
                        (sort-by first)
                        (apply concat)
                        (apply array-map))]
    (fmap #(stabilize %) sorted-map)))

(defprotocol Stable
  "Provides a stable sorted implementation for maps and sets where their equality does not consider order"
  (stabilize [a] "creates a stable sorted version"))

(extend nil
  Stable
  {:stabilize identity})

(extend Object
  Stable
  {:stabilize identity})

(extend-protocol Stable
  java.util.Set
  (stabilize [a]
    (apply sorted-set a))

  java.util.List
  (stabilize [a]
    (mapv stabilize a))

  java.util.Map
  (stabilize [a]
    (stabilize-map* a))

  clojure.lang.PersistentHashMap
  (stabilize [a]
    (stabilize-map* a)))

(def pprint (comp clojure.pprint/pprint stabilize))
