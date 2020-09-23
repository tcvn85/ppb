(ns ppb.common.page.home.panel
  (:require [re-frame.core :as re-frame]
            [ppb.common.subs :as subs]
            [ppb.common.page.header :refer [header]]))

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
        [:div.address-info "19 Hoang Sa, Da Kao, District 1," [:br] "Ho Chi Minh City, Vietnam"]]
       [:div.col-md-4
        [:div.contact-address
         [:a {:href "mailto:contact@pepper.builders"} "contact@pepper.builders"] [:br]
         [:a {:href "tel:+842838293940"} "(+84) 28 3829 3940"]]]
       [:div.col-md-4
        [:div.copyright "© Pepper 2019"]]]
      [:div.row.footer-content
       [:div.quote-btn
        [:a.btn.btn-primary {:href "#"} "QUICK QUOTE"]]
       [:div.socials
        [:a {:href "https://www.facebook.com/PepperBuilders/" :target "_blank"}
         [:img {:src "/img/facebook-brands.svg" :alt "facebook" :width "20"}]]
        [:a {:href "https://www.instagram.com/pepperbuildersmakers/" :target "_blank"}
         [:img {:src "/img/instagram-brands.svg" :alt "facebook" :width "20"}]]
        [:a {:href "#"} [:img {:src "/img/linkedin-brands.svg" :alt "" :target "_blank" :width "20"}]]]]]]]])