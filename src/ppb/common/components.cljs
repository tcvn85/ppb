(ns ppb.common.components
  (:require [ppb.common.dom-util :as dom-util]
            [ppb.spa.nav :as nav]
            [applied-science.js-interop :as j]))

(defn link [{:keys [href class]} content]
  [:a (cond-> {:href     href
               :class    class
               :on-click (fn [e]
                           (j/call e :preventDefault)
                           (nav/goto href))}
              (some? class) (assoc :class class))
   content])