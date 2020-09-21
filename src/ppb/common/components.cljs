(ns ppb.common.components
  (:require [ppb.common.dom-util :as dom-util]))

(defn link [{:keys [href on-click]} content]
  [:a {:href     (or href "#")
       :on-click (if on-click
                   on-click
                   dom-util/click-href)}
   content])