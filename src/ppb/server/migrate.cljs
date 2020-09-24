(ns ppb.server.migrate
  (:require
    [ppb.common.serial :as serial]
    [ppb.common.model.project :as project]
    ["fs" :as fs]
    [applied-science.js-interop :as j]
    [clojure.string :as string]
    [ppb.common.log :refer-macros [spy]]))

(def txt-root-path "./public/txt")
(defn txt-path [slug]
  (str txt-root-path "/" slug))

(def project-list
  [
   ; first line is meta-data
   [30 10 12 4 2]
   ["the nang suite"                                        ; slug, title, txt file, html file
    1
    "/assets/project-detail-1.jpg"
    [1]                                                     ; tags
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
   ["penhouse d7" 1 "/assets/project-detail-1.jpg" [1] "2019" "Da Nang" "550 m2" "A block of Serviced condos built on a tube house site, featuring 8 rooms with bespoke interiors and furniture manufactured in our workshop 800km away. Our client wanted to diversify their business portfolio whilst creating a unique space with a personal touch." "/assets/project-detail-2.jpg" "/assets/project-detail-2.jpg" ["/assets/project-detail-3.jpg" "/assets/project-detail-4.jpg" "/assets/project-detail-5.jpg" "/assets/project-detail-6.jpg" "/assets/project-detail-7.jpg" "/assets/project-detail-8.jpg"]]
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
  (let [meta-line (atom nil)]
    (->> project-list
         (mapv (fn [prj]
                 (if (some? @meta-line)
                   (let [line (serial/item2str prj)
                         slug (->> (project/get-column prj ::project/txt-uri)
                                   (str "/projects/"))
                         content (->> [@meta-line line]
                                      (string/join serial/line-separator))]
                     (spit (txt-path slug) content)
                     line)
                   (let [line (serial/item2str prj)]
                     (reset! meta-line line)
                     line))))
         (string/join serial/line-separator)
         (spit (txt-path "projects.txt")))))

