(ns ppb.common.page.header
  (:require [ppb.common.components :as comps]
            [ppb.common.router :as router]))

(defn header []
  [:header.header
   [:nav.navbar.navbar-expand-lg.navbar-light.bg-light
    [:div.container-fluid
     [comps/link {:href (router/uri ::router/home)
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
         [:span "Projects"]]]
       [:li.nav-item
        [comps/link {:href (router/uri ::router/service)
                     :class "nav-link"}
         [:span "Services"]]]
       [:li.nav-item
        [comps/link {:href  (router/uri ::router/about)
                     :class "nav-link"}
         [:span "About"]]]
       [:li.nav-item
        [comps/link {:href (router/uri ::router/contact)
                     :class "nav-link"}
         [:span "Contact"]]]
       [:li.nav-item.nav-flag
        [:a.nav-link {:href "#"} "VI"] "|"
        [:a.nav-link.active {:href "#"} "EN"]]]]]]])