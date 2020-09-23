(ns ppb.common.model.project)

(def tags
  ["Serviced apartments"])

#_["the nang suite"                                        ; slug, title, txt file, html file
   "/assets/project-detail-1.jpg"
   "0"                                                     ; tags
   "2019"
   "Da Nang"
   "550 m2"
   "A block of Serviced condos built on a tube house ..."  ; description
   "/assets/project-detail-2.jpg"                          ; 3D render
   "/assets/project-detail-2.jpg"                          ; Reality
   "/assets/project-detail-3.jpg,/assets/project-detail-4.jpg,/assets/project-detail-5.jpg,/assets/project-detail-6.jpg"
   ]
(def columns
  (->> [:title
        :hero-image
        :tags
        :created-date
        :location
        :area
        :description
        :3d-image
        :real-image
        :images]
       (map-indexed (fn [idx k]
                      [k #(nth % idx)]))))

(defn get-column [row column]
  (when-let [col-fn (columns column)]
    (col-fn row)))
