(ns ppb.common.page.project-item.panel
  (:require
    [ppb.common.page.footer :refer [footer quick-quote]]
    [ppb.common.page.project-item.subs :as subs]
    [ppb.common.model.project :as project]
    [re-frame.core :as rf]
    [clojure.string :as string]
    [ppb.common.router :as router]
    [ppb.common.components :as comps]))

(defn project-item-panel []
  (let [row (first @(rf/subscribe [:common/data ::router/project-item]))]
    [:main.main
     [:div.container-fluid
      [:div.project-detail.mt-4
       [:img.img-fluid.mb-5 {:src (project/get-column row ::project/image-hero) :alt ""}]
       [:h1.mb-4 (some-> (project/get-column row ::project/title)
                         (string/upper-case))]
       [:div.row.mb-5
        [:div.col-md-5
         [:ul.project-pros
          [:li [:strong "CATEGORY:"] (project/get-column row ::project/category)]
          [:li [:strong "YEAR:"] (project/get-column row ::project/created-date)]
          [:li [:strong "LOCATION:"] (project/get-column row ::project/location)]
          [:li [:strong "AREA:"] (project/get-column row ::project/area)]]]
        [:div.col-md-7
         [:div.project-desc.mb-5
          [:p (project/get-column row ::project/description)]]]]
       [:div.row.img-render-title.mb-2
        [:div.col-md-6
         [:h4.text-center.img-render-title "3D RENDER"]]
        [:div.col-md-6
         [:h4.text-center.img-render-title "REALITY"]]]
       [:div.img-render.mb-5
        [:img {:src (project/get-column row ::project/image-real) :alt ""}]]
       [:div.project-gallery.mb-5
        [:h2.mb-4 "REAL LIFE GALLERY"]
        (let [[img1 img2 & imgs] (project/get-column row ::project/images)]
          [:<>
           [:div.row
            (map-indexed (fn [idx image-uri]
                           ^{:key idx} [:div.col-md-6
                                        [:img {:src image-uri :alt ""}]])
                         (->> [img1 img2]
                              (remove nil?)))]
           [:div.row
            (map-indexed (fn [idx image-uri]
                           ^{:key idx} [:div.col-md-6
                                        [:img {:src image-uri :alt ""}]])
                         imgs)]])
        ]
       [:div.project-related
        [:h4.mb-5.text-center "OTHER PROJECTS"
         [:span [:img {:src "/img/arrow-down-solid.svg" :width "16" :alt ""}]]]
        [:div.row
         (doall (for [[title asset-uri] @(rf/subscribe [:common/meta-data ::router/project-item])]
                  ^{:key title} [:div.col-md-3
                                 [:div.project-related-item
                                  [comps/link
                                   {:href (router/uri ::router/project-item {:slug (project/slug title)})}
                                   [:img {:src asset-uri :alt ""}]]]]))]]]
      [quick-quote]]]))