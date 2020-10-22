(ns ppb.common.model.project
  (:require [clojure.string :as string]
            [ppb.common.log :refer-macros [spy]]))

#_["the nang suite"
   "Hotels & resorts"
   "/assets/img/project1.jpg"
   "2019"
   "Da Nang"
   "550 m2"
   "A block of Serviced condos built on a tube house site, featuring 8 rooms with bespoke interiors and furniture manufactured in our workshop 800km away. Our client wanted to diversify their business portfolio whilst creating a unique space with a personal touch."
   "/assets/img/project-detail-1.jpg"                          ; 3D render
   "/assets/img/project-detail-2.jpg"                          ; Reality
   ["/assets/img/project-detail-3.jpg"
    "/assets/img/project-detail-4.jpg"
    "/assets/img/project-detail-5.jpg"
    "/assets/img/project-detail-6.jpg"]
   ["penhouse d7"]]

(def columns
  (->> [::title
        ::category
        ::image-hero
        ::created-date
        ::location
        ::area
        ::description
        ::image-3d
        ::image-real
        ::images
        ::related-projects]
       (map-indexed (fn [idx k]
                      [k #(nth % idx)]))
       (into {})))

(defn title-id [title]
  (some-> title
          string/lower-case
          (string/split #" ")
          (#(string/join "-" %))))

(defn slug [title]
  (str (title-id title) ".html"))

(defn txt-uri [title]
  (str (title-id title) ".txt"))

(defn get-column [row column]
  (when row
    (let [col-fn (columns column)]
      (cond
        (some? col-fn) (col-fn row)
        (= ::slug column) (slug (get-column row ::title))
        (= ::txt-uri column) (txt-uri (get-column row ::title))
        true nil))))

