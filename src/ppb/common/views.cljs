(ns ppb.common.views
  (:require
    [re-frame.core :as re-frame]
    [ppb.common.subs :as subs]
    [ppb.common.page.home.panel :refer [home-panel]]
    [ppb.common.page.about.panel :refer [about-panel]]
    [ppb.common.page.contact.panel :refer [contact-panel]]
    [ppb.common.page.service.panel :refer [service-panel]]
    [ppb.common.page.project.panel :as prj]
    [ppb.common.page.project-item.panel :refer [project-item-panel]]
    [ppb.common.page.header :refer [header]]
    [ppb.common.page.footer :refer [footer quick-quote]]
    [re-frame.core :as rf]))

(defn- panels [panel-name]
  (case panel-name
    :route/home-panel [home-panel]
    :route/about-panel [about-panel]
    :route/contact-panel [contact-panel]
    :route/service-panel [service-panel]
    :route/projects-panel [prj/projects-panel nil]
    :route/project-item-panel [project-item-panel]
    :route/projects-cat [prj/projects-panel (-> @(rf/subscribe [::subs/active-route])
                                                :params
                                                :category)]

    ; can't go here. It goes to other flow by the routes
    :route/not-found-panel [:div "Not found"]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn nowrap-panel? [p]
  (-> (#{:route/home-panel} p)
      some?))

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [:div#app
     (if (nowrap-panel? @active-panel)
       [show-panel @active-panel]
       [:div.wrapper
        [header]
        [show-panel @active-panel]
        [footer]])]
    ))
