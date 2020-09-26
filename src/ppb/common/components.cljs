(ns ppb.common.components
  (:require
    [ppb.common.nav :as nav]
    [applied-science.js-interop :as j]
    [ppb.common.log :refer-macros [debug]]))

(defn link [{:keys [href class]} content]
  [:a (cond-> {:href     href
               :class    class
               :on-click (fn [e]
                           (j/call e :preventDefault)
                           ;(debug "click " href)
                           (nav/goto href))}
              (some? class) (assoc :class class))
   content])