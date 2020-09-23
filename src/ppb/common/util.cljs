(ns ppb.common.util
  (:require [clojure.string :as string]))

(defn ext-html-to-txt [filename]
  (string/replace filename #"\.html" ".txt"))