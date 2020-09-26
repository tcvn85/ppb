(ns ppb.common.page.project-item.panel
  (:require
    [ppb.common.page.footer :refer [footer quick-quote]]))

(defn project-item-panel []
  [:main.main
   [:div.container-fluid
    [:div.project-detail.mt-4
     [:img.img-fluid.mb-5 {:src "/assets/img/project-detail-1.jpg" :alt ""}]
     [:h1.mb-4 "THE NANG SUITES"]
     [:div.row.mb-5
      [:div.col-md-5
       [:ul.project-pros
        [:li [:strong "CATEGORY:"] "Serviced apartments"]
        [:li [:strong "YEAR:"] "2019"]
        [:li [:strong "LOCATION:"] "Da Nang"]
        [:li [:strong "AREA:"] "550 m2"]]]
      [:div.col-md-7
       [:div.project-desc.mb-5
        [:p "A block of Serviced condos built on a tube house site, featuring 8 rooms with bespoke interiors and furniture manufactured in our workshop 800km away. Our client wanted to diversify their business portfolio whilst creating a unique space with a personal touch."]]]]
     [:div.row.img-render-title.mb-2
      [:div.col-md-6
       [:h4.text-center.img-render-title "3D RENDER"]]
      [:div.col-md-6
       [:h4.text-center.img-render-title "REALITY"]]]
     [:div.img-render.mb-5
      [:img {:src "/assets/img/project-detail-2.jpg" :alt ""}]]
     [:div.project-gallery.mb-5
      [:h2.mb-4 "REAL LIFE GALLERY"]
      [:div.row
       [:div.col-md-6
        [:img {:src "/assets/img/project-detail-3.jpg" :alt ""}]]
       [:div.col-md-6
        [:img {:src "/assets/img/project-detail-4.jpg" :alt ""}]]]
      [:div.row
       [:div.col-md-6
        [:img {:src "/assets/img/project-detail-5.jpg" :alt ""}]]
       [:div.col-md-6
        [:img {:src "/assets/img/project-detail-6.jpg" :alt ""}]]
       [:div.col-md-6
        [:img {:src "/assets/img/project-detail-5.jpg" :alt ""}]]
       [:div.col-md-6
        [:img {:src "/assets/img/project-detail-6.jpg" :alt ""}]]
       [:div.col-md-6
        [:img {:src "/assets/img/project-detail-5.jpg" :alt ""}]]
       [:div.col-md-6
        [:img {:src "/assets/img/project-detail-6.jpg" :alt ""}]]]]
     [:div.project-related
      [:h4.mb-5.text-center "OTHER PROJECTS"
       [:span [:img {:src "/img/arrow-down-solid.svg" :width "16" :alt ""}]]]
      [:div.row
       [:div.col-md-3
        [:div.project-related-item
         [:a {:href "#"} [:img {:src "/assets/img/project-detail-5.jpg" :alt ""}]]]]
       [:div.col-md-3
        [:div.project-related-item
         [:a {:href "#"} [:img {:src "/assets/img/project-detail-5.jpg" :alt ""}]]]]
       [:div.col-md-3
        [:div.project-related-item
         [:a {:href "#"} [:img {:src "/assets/img/project-detail-5.jpg" :alt ""}]]]]
       [:div.col-md-3
        [:div.project-related-item
         [:a {:href "#"} [:img {:src "/assets/img/project-detail-5.jpg" :alt ""}]]]]]]]
    [quick-quote]]])