(ns ppb.common.model.project
  (:require [clojure.string :as string]
            [ppb.common.log :refer-macros [spy]]))

(def tags ["All" "Apartments" "Villas" "Hotels and Resorts" "Restaurants"])

#_["the nang suite"                                        ; slug, title, txt file, html file
   1
   "/assets/project-detail-1.jpg"
   [1]                                                     ; tags
   "2019"
   "Da Nang"
   "550 m2"
   "A block of Serviced condos built on a tube house ..."  ; description
   "/assets/project-detail-2.jpg"                          ; 3D render
   "/assets/project-detail-2.jpg"                          ; Reality
   "/assets/project-detail-3.jpg,/assets/project-detail-4.jpg,/assets/project-detail-5.jpg,/assets/project-detail-6.jpg"
   ]
(def columns
  (->> [::title
        ::category
        ::hero-image
        ::tags
        ::created-date
        ::location
        ::area
        ::description
        ::3d-image
        ::real-image
        ::images]
       (map-indexed (fn [idx k]
                      [k #(nth % idx)]))
       (into {})))

(defn title-id [title]
  (-> title
      string/lower-case
      (string/split #" ")
      (#(string/join "-" %))))

(defn slug [title]
  (str (title-id title) ".html"))

(defn txt-uri [title]
  (str (title-id title) ".txt"))

(defn tag-id2name [idx]
  (nth tags idx))

(defn get-column [row column]
  (let [col-fn (columns column)]
    (cond
      (= ::tags column) (->> (col-fn row)
                             (mapv tag-id2name))
      (= ::category column) (-> (col-fn row)
                                (tag-id2name))
      (some? col-fn) (col-fn row)
      (= ::slug column) (slug (get-column row ::title))
      (= ::txt-uri column) (txt-uri (get-column row ::title))
      true nil)))

