(ns ppb.server.migrate
  (:require
    [ppb.common.serial :as serial]
    ["fs" :as fs]
    [applied-science.js-interop :as j]))

(def project-list
  [["the nang suite"                                        ; slug, title, txt file, html file
    "/assets/project-detail-1.jpg"
    [0]                                                     ; tags
    "2019"
    "Da Nang"
    "550 m2"
    "A block of Serviced condos built on a tube house site, featuring 8 rooms with bespoke interiors and furniture manufactured in our workshop 800km away. Our client wanted to diversify their business portfolio whilst creating a unique space with a personal touch."
    "/assets/project-detail-2.jpg"                          ; 3D render
    "/assets/project-detail-2.jpg"                          ; Reality
    ["/assets/project-detail-3.jpg"
     "/assets/project-detail-4.jpg"
     "/assets/project-detail-5.jpg"
     "/assets/project-detail-6.jpg"
     "/assets/project-detail-7.jpg"
     "/assets/project-detail-8.jpg"]]
   ["penhouse d7" "/assets/project-detail-1.jpg" [0] "2019" "Da Nang" "550 m2" "A block of Serviced condos built on a tube house site, featuring 8 rooms with bespoke interiors and furniture manufactured in our workshop 800km away. Our client wanted to diversify their business portfolio whilst creating a unique space with a personal touch." "/assets/project-detail-2.jpg" "/assets/project-detail-2.jpg" ["/assets/project-detail-3.jpg" "/assets/project-detail-4.jpg" "/assets/project-detail-5.jpg" "/assets/project-detail-6.jpg" "/assets/project-detail-7.jpg" "/assets/project-detail-8.jpg"]]
   ])

(defn spit [filename content]
  (fs/writeFile filename
                content
                (fn [err]
                  (when err
                    (js/console.log "write " filename " failed!")))))

(defn slurp [filename]
  (-> (fs/readFileSync filename ^:js {:encoding "utf8" :flag "r"})
      (.toString)))

(defn start []
  (->> project-list
       #_(map-indexed (fn [idx prj]
                      (->> (cons idx prj)
                           (into []))))
       (serial/items2strs)
       (spit "./public/txt/projects.txt")))

