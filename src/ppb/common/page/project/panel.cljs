(ns ppb.common.page.project.panel
  (:require
    [ppb.common.page.footer :refer [footer quick-quote]]
    [re-frame.core :as rf]
    [ppb.common.page.project.subs :as subs]
    [ppb.common.model.project :as prj]
    [clojure.string :as string]))

(defn project [data]
  [:div.col-md-6.col-sm-6
   [:div.project-item
    [:a {:href (str "/projects/" (prj/get-column data ::prj/slug))}
     [:div.project-img-wrapper
      [:img.project-img {:src (prj/get-column data ::prj/hero-image) :alt (prj/get-column data ::prj/title)}]]
     [:div.project-caption
      [:h2 (-> (prj/get-column data ::prj/category)
               (string/upper-case))]
      [:h4 (str (-> (prj/get-column data ::prj/title)
                    (string/upper-case))
                " | "
                (prj/get-column data ::prj/area))]]]]])

(defn projects-panel []
  [:main.main
   [:div.container-fluid
    [:div.projects-list
     [:div.filter.mt-4
      [:a.active {:href "#"} "All" [:span @(rf/subscribe [::subs/prj-total])]]
      [:a {:href "#"} "Apartments" [:span @(rf/subscribe [::subs/tag-item-total 1])]]
      [:a {:href "#"} "Villas" [:span @(rf/subscribe [::subs/tag-item-total 2])]]
      [:a {:href "#"} "Hotels and Resorts" [:span @(rf/subscribe [::subs/tag-item-total 3])]]
      [:a {:href "#"} "Restaurants" [:span @(rf/subscribe [::subs/tag-item-total 4])]]
      ]
     [:div.row
      (map-indexed (fn [idx data]
                     ^{:key idx} [project data])
                   @(rf/subscribe [::subs/projects]))]]
    [:div.project-more.text-center.mt-3.mb-5
     [:a.btn.btn-outline-primary {:href "#"} "VIEW MORE"]]
    [quick-quote]]])
