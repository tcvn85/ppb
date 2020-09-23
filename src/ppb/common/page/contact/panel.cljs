(ns ppb.common.page.contact.panel
  (:require
    [ppb.common.page.header :refer [header]]
    [ppb.common.page.footer :refer [footer quick-quote]]))

(defn contact-panel []
  [:main.main
   [:div.container-fluid
    [:div.main-contact.mt-4
     [:div.row.mb-5
      [:div.col-md-5
       [:div.content-left
        [:h2 "QUICK QUOTE"]]]
      [:div.col-md-7
       [:div.content-right
        [:p "Please complete this short survey and we will get back to you within 48 hours with a quick quote."]
        [:p [:a.btn.btn-outline-primary {:href "#"} "Quick quote"]]
        [:p "19 Hoang Sa Da Kao, District 1 Ho Chi Minh City, Vietnam"]
        [:p [:a {:href "mailto:contact@pepper.builders"} "contact@pepper.builders"]]
        [:p [:a {:href "tel:+842838293940"} "(+84) 28 3829 3940"]]
        [:p [:a {:href "https://goo.gl/maps/DCojBLGvqBFkVVQ8A" :target "_blank"} [:u "Map"]]]]]]
     [:div.row.mb-5
      [:div.col-md-5
       [:div.content-left
        [:h2 "FOLLOW US"]]]
      [:div.col-md-7
       [:div.content-right
        [:p [:a {:href "https://www.facebook.com/PepperBuilders/" :target "_blank"} [:u "Facebook"]] [:br]
         [:a {:href "https://www.instagram.com/pepperbuildersmakers/" :target "_blank"} [:u "Twitter"]]]]]]
     [:div.row.mb-5
      [:div.col-md-5
       [:div.content-left
        [:h2 "FURTHER REQUEST"]]]
      [:div.col-md-7
       [:div.content-right
        [:form.contact-form.mb-5 {:action "#"}
         [:div.form-group.error
          [:input#inputName.form-control {:type "text" :placeholder "Name*"}]
          [:small#emailHelp.form-text.text-danger "This field is required."]]
         [:div.form-group
          [:input#inputEmail.form-control {:type "email" :placeholder "Email*"}]]
         [:div.form-group
          [:input#inputPhone.form-control {:type "text" :placeholder "Phone*"}]]
         [:div.form-group.mb-3
          [:textarea#inputMessage.form-control {:rows "4" :placeholder "Email*"}]]
         [:div.form-group
          [:button.btn.btn-primary.btn-submit "Submit"]]]]]]
     [:div.row.mb-5
      [:div.col-md-5
       [:div.content-left
        [:h2 "CONTACT"]
        [:ul.list-unstyled.contact-info
         [:li
          [:span [:img {:src "/img/map-marker-alt-solid.svg" :width "20" :alt ""}]]
          [:a {:href "https://goo.gl/maps/DCojBLGvqBFkVVQ8A" :target "_blank"} "19 Hoang Sa, Da Kao Ward, District  1 - HCMC"]]
         [:li
          [:span [:img {:src "/img/map-marker-alt-solid.svg" :width "20" :alt ""}]]
          [:a {:href "https://goo.gl/maps/8jwMJhz3izhKdFG57" :target "_blank"} "26 Ly Tu Trong, 2nd floor, Room 23-24, Ben Nghe Ward, District 1 - HCMC"]]
         [:li
          [:span [:img {:src "/img/envelope.svg" :width "20" :alt ""}]]
          [:a {:href "mailto:info@pepper.builders" :target "_blank"} "info@pepper.builders"]]
         [:li
          [:span [:img {:src "/img/phone.svg" :width "20" :alt ""}]]
          [:a {:href "tel:+842839102028" :target "_blank"} "(+84) 28 3910 2028"]]
         [:li
          [:span [:img {:src "/img/phone.svg" :width "20" :alt ""}]]
          [:a {:href "tel:+84931888149" :target "_blank"} "(+84) 931 888 149"]]]]]
      [:div.col-md-7
       [:div.content-right
        [:div.maps
         [:iframe {:src "https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d62708.0773445698!2d106.704693!3d10.791784!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0xfe64eb005cbd14bc!2sPEPPER%20BUILDERS%20AND%20MAKERS!5e0!3m2!1svi!2sus!4v1600104429998!5m2!1svi!2sus"
                   :width "100%"
                   :height "380"
                   :frame-border "0"
                   :style {:border 0}
                   :allowFullScreen true
                   :aria-hidden false
                   :tab-index "0"}]]]]]]]
   [quick-quote]])