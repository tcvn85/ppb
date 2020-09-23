(ns ppb.common.serial
  (:require
    [ppb.common.model.project :as project]
    [clojure.string :as string]
    [clojure.edn :as edn]
    [applied-science.js-interop :as j]))

(def line-separator "\r\n")

(def default-limit 10)

(defn item2str [item]
  (pr-str item))

(defn items2strs [items]
  (->> (mapv item2str items)
       (string/join line-separator)))

(defn str2item [str]
  (edn/read-string str))

(defn lines2items [lines]
  (->> (string/split lines line-separator)
       (mapv str2item)))
