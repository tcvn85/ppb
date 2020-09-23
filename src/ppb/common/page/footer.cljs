(ns ppb.common.page.footer)

(defn footer []
  [:footer.footer
   [:div.container-fluid
    [:div.row.footer-info
     [:div.col-md-4
      [:div.address-info "19 Hoang Sa, Da Kao, District 1," [:br] "Ho Chi Minh City, Vietnam"]]
     [:div.col-md-4
      [:div.contact-address
       [:a {:href "mailto:contact@pepper.builders"} "contact@pepper.builders"] [:br]
       [:a {:href "tel:+842838293940"} "(+84) 28 3829 3940"]]]
     [:div.col-md-4
      [:div.copyright "© Pepper 2019"]]]]])

(defn quick-quote []
  [:div.footer-content
   [:div.quote-btn
    [:a.btn.btn-primary {:href "#"} "QUICK QUOTE"]]
   [:div.scroll-top-div
    [:a.scroll-top {:href "#"}
     [:img.mr-2 {:src "/img/angle-up.svg" :alt "" :width "10"}]
     [:span "SCROLL TO TOP"]]]
   [:div.socials
    [:a {:href "https://www.facebook.com/PepperBuilders/" :target "_blank"}
     [:img {:src "/img/facebook-brands.svg" :alt "facebook" :width "20"}]]
    [:a {:href "https://www.instagram.com/pepperbuildersmakers/" :target "_blank"}
     [:img {:src "/img/instagram-brands.svg" :alt "facebook" :width "20"}]]
    [:a {:href "#"} [:img {:src "/img/linkedin-brands.svg" :alt "" :target "_blank" :width "20"}]]]])