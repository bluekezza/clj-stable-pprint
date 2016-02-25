(ns clj-stable-pprint.core-test
  (:require
   [clojure.data]
   [clojure.edn]
   [clojure.java.io :as io]
   [clojure.pprint]
   [clojure.test :refer :all]
   [clj-stable-pprint.core :as spp]))

(defn ^:io from-file
  [path]
  (-> path
      io/file
      slurp
      clojure.edn/read-string))

(defn ^:io str-from-resource
  [path]
  (-> path
      io/resource
      io/file
      slurp))

(defn ^:io edn-from-resource
  [path]
  (-> path
      str-from-resource
      clojure.edn/read-string))

(defn diff-string
  [a b]
  (let [diff (->> (clojure.data/diff (seq a) (seq b))
                  (mapv #(apply str %)))]
    {:length (count (nth diff 0))
     :diff diff}))

(deftest stabilize-test-with-ever-increasing-maps
  (let [fixtures [{:in (edn-from-resource "resource/clj_stable_pprint/small.edn")
                   :out (str-from-resource "resource/clj_stable_pprint/small.txt")}
                  {:in (edn-from-resource "resource/clj_stable_pprint/medium.edn")
                   :out (str-from-resource "resource/clj_stable_pprint/medium.txt")}
                  {:in (edn-from-resource "resource/clj_stable_pprint/large.edn")
                   :out (str-from-resource "resource/clj_stable_pprint/large.txt")}
                  ]]
    (doseq [{in :in, out :out} fixtures]
      (let [actual-out (with-out-str (spp/pprint in))
            pass (= out actual-out)]
        (when (not pass)
          (clojure.pprint/pprint (diff-string out actual-out)))
        (is pass)))))
