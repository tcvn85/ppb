(ns ppb.common.page.footer
  (:require [ppb.common.lang :as lang]))

(defn qq-button []
  [:div.quote-btn
   [:a.btn.btn-primary {:href "#"} [lang/tr-span :quick-quote]]])

(defn address []
  [:div.address-info
   [lang/tr-span :address-l1]
   [:br]
   [lang/tr-span :address-l2]])

(defn contact-address []
  [:div.contact-address
   [:a {:href "mailto:contact@pepper.builders"} "contact@pepper.builders"] [:br]
   [:a {:href "tel:+842838293940"} "(+84) 28 3829 3940"]])

(defn copyright []
  [:div.copyright "© Pepper 2019"])

(defn footer []
  [:footer.footer
   [:div.container-fluid
    [:div.row.footer-info
     [:div.col-md-4
      [address]]
     [:div.col-md-4
      [contact-address]]
     [:div.col-md-4
      [copyright]]]]])

(defn social-networks []
  [:div.socials
   [:a {:href "https://www.facebook.com/PepperBuilders/" :target "_blank"}
    [:img {:src "/img/facebook-brands.svg" :alt "facebook" :width "20"}]]
   [:a {:href "https://www.instagram.com/pepperbuildersmakers/" :target "_blank"}
    [:img {:src "/img/instagram-brands.svg" :alt "facebook" :width "20"}]]
   [:a {:href "#"} [:img {:src "/img/linkedin-brands.svg" :alt "" :target "_blank" :width "20"}]]])

(defn quick-quote []
  [:div.footer-content
   [qq-button]
   [:div.scroll-top-div
    [:a.scroll-top {:href "#"}
     [:img.mr-2 {:src "/img/angle-up.svg" :alt "" :width "10"}]
     [lang/tr-span :scroll-top]]]
   [social-networks]])