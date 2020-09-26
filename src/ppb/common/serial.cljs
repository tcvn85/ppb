(ns ppb.common.serial
  (:require
    [ppb.common.model.project :as project]
    [clojure.string :as string]
    [clojure.edn :as edn]
    [applied-science.js-interop :as j]
    [ppb.common.log :refer-macros [debug]]))

(def line-separator "\r\n")

(def default-limit 10)

(defn item2str [item]
  (pr-str item))

(defn items2strs [items]
  (->> (mapv item2str items)
       (string/join line-separator)))

(defn str2item [s]
  (when-not (string/blank? s)
    (try
      (edn/read-string s)
      (catch js/Error e
        (debug "str2item error " e)))))

(defn lines2items [lines]
  (when-not (string/blank? lines)
    (->> (string/split lines line-separator)
         (mapv str2item))))


(defn item2csvline [row]
  (->> row
       (map item2str)
       (string/join ",")))