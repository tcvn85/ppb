(ns ppb.common.page.home.panel
  (:require [re-frame.core :as re-frame]
            [ppb.common.subs :as subs]
            [ppb.common.page.header :refer [header]]
            [ppb.common.page.footer :as footer]
            [ppb.common.lang :as lang]))

(defn home-panel []
  [:div.wrapper.home
   [header]
   [:main.main
    [:div.container-fluid
     [:h1 "WE BRING IDEAS" [:br] "TO LIFE."]]]
   [:footer.footer
    [:div.container-fluid
     [:div.footer-info
      [:div.row
       [:div.col-md-4
        [footer/address]]
       [:div.col-md-4
        [footer/contact-address]]
       [:div.col-md-4
        [footer/copyright]]]
      [:div.footer-content
       [footer/qq-button]
       [footer/social-networks]]]]]])