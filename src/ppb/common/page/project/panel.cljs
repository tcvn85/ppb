(ns ppb.common.page.project.panel
  (:require
    [ppb.common.page.footer :refer [footer quick-quote]]
    [re-frame.core :as rf]
    [ppb.common.page.project.subs :as subs]
    [ppb.common.page.project.events :as events]
    [ppb.common.model.project :as prj]
    [clojure.string :as string]
    [ppb.common.components :as comps]
    [applied-science.js-interop :as j]
    [ppb.common.log :refer-macros [debug]]
    [ppb.common.router :as router]
    [ppb.common.model.project-category :as pcat]))

(defn project [data]
  [:div.col-md-6.col-sm-6
   [:div.project-item
    [comps/link {:href (router/uri ::router/project-item {:slug (prj/get-column data ::prj/slug)})}
     [:div
      [:div.project-img-wrapper
       [:img.project-img {:src (prj/get-column data ::prj/image-hero) :alt (prj/get-column data ::prj/title)}]]
      [:div.project-caption
       [:h2 (-> (prj/get-column data ::prj/category)
                (string/upper-case))]
       [:h4 (str (-> (prj/get-column data ::prj/title)
                     (string/upper-case))
                 " | "
                 (prj/get-column data ::prj/area))]]]]]])

(defn cat-item [{:keys [uri active? prj-count category]}]
  [comps/link {:href uri
               :class [(when active?
                         "active")]}
   [:div (if (string? category)
           (string/capitalize category)
           "") [:span prj-count]]])

(defn projects-panel [category]
  [:main.main
   [:div.container-fluid
    [:div.projects-list
     [:div.filter.mt-4
      (let [[category prj-count] (first @(rf/subscribe [::subs/meta category]))
            uri (router/uri ::router/projects)
            active? (= uri @(rf/subscribe [:common/uri category]))]
        ^{:key category} [cat-item {:uri uri :active? active? :prj-count prj-count :category category}])
      (doall
        (map-indexed (fn [idx [category prj-count]]
                       (let [uri (router/uri ::router/projects-cat {:category (pcat/category-ids category)})
                             active? (= uri @(rf/subscribe [:common/uri]))]
                         ^{:key idx} [cat-item {:uri uri :active? active? :prj-count prj-count :category category}]))
                     (rest @(rf/subscribe [::subs/meta category]))))]
     [:div.row
      (map-indexed (fn [idx data]
                     ^{:key idx} [project data])
                   @(rf/subscribe [::subs/projects category]))]]
    (if @(rf/subscribe [::subs/prj-more? category])
      [:div.project-more.text-center.mt-3.mb-5
       [:button.btn.btn-outline-primary
        {:on-click (fn [e]
                     (j/call e :preventDefault)
                     (rf/dispatch [::events/load-more category]))}
        "VIEW MORE"]])
    [quick-quote]]])
