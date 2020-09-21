(ns ppb.common.page.about.panel
  (:require [ppb.common.components :as comps]))

(defn about-panel []
  [:div
   [:h1 "This is the About Page."]

   [:div
    [comps/link {:href "/"} "go Home"]]])