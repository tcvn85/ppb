(ns ppb.common.util
  (:require [clojure.string :as string]
            [applied-science.js-interop :as j]))

(defn ext-html-to-txt [filename]
  (string/replace filename #"\.html" ".txt"))

(defn get-current-uri! []
  (let [uri (j/get js/location :pathname)]
    (if (or (nil? uri)
            (= uri "/"))
      "/index.html"
      uri)))