(ns ppb.common.dom-util
  (:require [applied-science.js-interop :as j]
            [ppb.common.log :refer-macros [debug]]
            [ppb.spa.nav :as nav]))

(defn click-href [e]
  (when (nav/valid?)
    (let [href (-> (j/get e :target)
                   (j/call :getAttribute "href"))]
      (j/call e :preventDefault)
      (nav/goto href))))

