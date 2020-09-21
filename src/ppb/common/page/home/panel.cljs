(ns ppb.common.page.home.panel
  (:require [re-frame.core :as re-frame]
            [ppb.common.subs :as subs]
            [ppb.common.components :as comps]))

(defn home-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1 (str "Hello from " @name ". This is the Home Page.")]

     [:div
      [comps/link {:href "/about"} "go to About Page"]]
     ]))