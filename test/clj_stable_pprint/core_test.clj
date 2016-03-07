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

(deftest pprint-test
  (let [fixtures [{:name "small sized map"
                   :in (edn-from-resource "resource/clj_stable_pprint/small.edn")
                   :out (str-from-resource "resource/clj_stable_pprint/small.txt")}
                  {:name "medium size map"
                   :in (edn-from-resource "resource/clj_stable_pprint/medium.edn")
                   :out (str-from-resource "resource/clj_stable_pprint/medium.txt")}
                  {:name "map large enough for assoc to convert array-maps to hash-maps"
                   :in (edn-from-resource "resource/clj_stable_pprint/large.edn")
                   :out (str-from-resource "resource/clj_stable_pprint/large.txt")}
                  {:name "simple mixed key types"
                   :in {"b" 1, :a 0}
                   :out "{:a 0, \"b\" 1}\n"}
                  {:name "more mixed key types"
                   :in {3 2, "b" 1, :a 0}
                   :out "{3 2, :a 0, \"b\" 1}\n"}
                  {:name "homogenous set"
                   :in #{1 9 3 7 5}
                   :out "#{1 3 5 7 9}\n"}
                  {:name "heterogenous set"
                   :in #{1 "9" 3 :7 5}
                   :out "#{1 3 5 :7 \"9\"}\n"}
                  ]]
    (doseq [{scenario :name, in :in, out :out} fixtures]
      (testing scenario
        (let [actual-out (with-out-str (spp/pprint in))
              pass (= out actual-out)]
          (when (not pass)
            (clojure.pprint/pprint (diff-string out actual-out)))
          (is pass))))))
