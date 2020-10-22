(ns ppb.common.page.header
  (:require [ppb.common.components :as comps]
            [ppb.common.router :as router]
            [re-frame.core :as rf]
            [ppb.common.lang :as lang]
            [ppb.common.util :as util]))

(defn header []
  [:header.header
   [:nav.navbar.navbar-expand-lg.navbar-light.bg-light
    [:div.container-fluid
     [comps/link {:href  (router/uri ::router/home1)
                  :class "navbar-brand"}
      [:img {:src "/img/pepper-logo-2.svg" :width "200" :alt "" :loading "lazy"}]]
     [:button.navbar-toggler {:type "button" :data-toggle "collapse" :data-target "#navbarSupportedContent" :aria-controls "navbarSupportedContent" :aria-expanded "false" :aria-label "Toggle navigation"}
      [:span.first]
      [:span.second]
      [:span.third]]
     [:div#navbarSupportedContent.collapse.navbar-collapse
      [:ul.navbar-nav
       [:li.nav-item
        [comps/link {:href (router/uri ::router/projects)
                     :class "nav-link"}
         [:span [lang/tr-span :projects]]]]
       [:li.nav-item
        [comps/link {:href (router/uri ::router/service)
                     :class "nav-link"}
         [:span [lang/tr-span :services]]]]
       [:li.nav-item
        [comps/link {:href  (router/uri ::router/about)
                     :class "nav-link"}
         [:span [lang/tr-span :about]]]]
       [:li.nav-item
        [comps/link {:href (router/uri ::router/contact)
                     :class "nav-link"}
         [:span [lang/tr-span :contact]]]]
       (let [active-cx #(when (= % (lang/current-lang!))
                         ["active" "disabled"])
             click-fn #(rf/dispatch [:common/set-lang %])]
         [:li.nav-item.nav-flag
          [:a.nav-link {:href "#"
                        :class (active-cx :lang/en)
                        :on-click #(click-fn :lang/en)}
           "EN"]
          "|"
          [:a.nav-link {:href "#"
                        :class (active-cx :lang/vi)
                        :on-click #(click-fn :lang/vi)}
           "VN"]])]]]]])