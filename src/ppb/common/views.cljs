(ns ppb.common.views
  (:require
   [re-frame.core :as re-frame]
   [ppb.common.subs :as subs]
   [ppb.common.page.home.panel :refer [home-panel]]
   [ppb.common.page.about.panel :refer [about-panel]]
   ))

(defn- panels [panel-name]
  (case panel-name
    :route/home-panel [home-panel]
    :route/about-panel [about-panel]

    ; can't go here. It goes to other flow by the routes
    :route/not-found-panel [:div "Not found"]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [:div#app
     [show-panel @active-panel]]))
