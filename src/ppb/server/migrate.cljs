(ns ppb.server.migrate
  (:require
    [ppb.common.serial :as serial]
    [ppb.common.model.project :as project]
    ["fs" :as fs]
    [applied-science.js-interop :as j]
    [clojure.string :as string]
    [ppb.common.log :refer-macros [spy]]
    [ppb.common.config :as config]))

(def assets-root-path "./public/assets")

(def project-list
  {:en [["the nang suite"
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
         ["penhouse d7", "villa 1"]]
        ["penhouse d7"
         "Residential"
         "/assets/img/project2.jpg"
         "2019"
         "Ho Chi Minh"
         "550 m2"
         "A block of Serviced condos built on a tube house site, featuring 8 rooms with bespoke interiors and furniture manufactured in our workshop 800km away. Our client wanted to diversify their business portfolio whilst creating a unique space with a personal touch."
         "/assets/img/project-detail-1.jpg"
         "/assets/img/project-detail-2.jpg"
         ["/assets/img/project-detail-3.jpg"
          "/assets/img/project-detail-4.jpg"
          "/assets/img/project-detail-5.jpg"
          "/assets/img/project-detail-6.jpg"]
         ["the nang suite"]]
        ]
   :vn [["the nang suite"                                        ; slug, title, txt file, html file
         "Hotels & resorts"                                      ; category
         "/assets/img/project1.jpg"
         "2019"
         "Đà Nẵng"
         "550 m2"
         "Biệt thự sang chảnh"
         "/assets/img/project-detail-1.jpg"                          ; 3D render
         "/assets/img/project-detail-2.jpg"                          ; Reality
         ["/assets/img/project-detail-3.jpg"
          "/assets/img/project-detail-4.jpg"
          "/assets/img/project-detail-5.jpg"
          "/assets/img/project-detail-6.jpg"]
         ["penhouse d7"]]
        ["penhouse d7"
         "Residential"
         "/assets/img/project2.jpg"
         "2019"
         "Hồ Chí Minh"
         "550 m2"
         "Căn hộ nóc"
         "/assets/img/project-detail-1.jpg"
         "/assets/img/project-detail-2.jpg"
         ["/assets/img/project-detail-3.jpg"
          "/assets/img/project-detail-4.jpg"
          "/assets/img/project-detail-5.jpg"
          "/assets/img/project-detail-6.jpg"]
         ["the nang suite"]]
        ]})

(defn spit [filename content]
  (fs/writeFile filename
                content
                (fn [err]
                  (when err
                    (js/console.log "write " filename " failed!")))))

(defn slurp [filename]
  (-> (fs/readFileSync filename ^:js {:encoding "utf8" :flag "r"})
      (.toString)))

(defn cp [from to]
  (fs/copyFileSync from to))

(defn mkdir-if-none [path]
  (if-not (fs/existsSync path)
    (fs/mkdirSync path ^:js {:recursive true})))



(defn txt-path [lang slug]
  (str assets-root-path (config/lang-prefix lang "/txt") slug))

(defn txt! []
  (doseq [[lang projects] project-list]
    (let [meta-line (atom {"all" 0})
          project-root-path "/projects"
          lines (mapv (fn [prj]
                        (let [line (serial/item2str prj)]
                          (swap! meta-line update (project/get-column prj ::project/category) inc)
                          (swap! meta-line update "all" inc)
                          line))
                      projects)]

      (mkdir-if-none (txt-path lang project-root-path))

      (doseq [prj projects]
        (let [slug    (str project-root-path "/" (project/get-column prj ::project/txt-uri))
              content (->> [@meta-line (serial/item2str prj)]
                           (string/join serial/line-separator))]
          (spit (txt-path lang slug) content)))

      (->> (concat [@meta-line] lines)
           (string/join serial/line-separator)
           (spit (txt-path lang "/projects.txt"))))))


(def csv-headers
  ["Language"
   "Project name"
   "Category"
   "Hero image"
   "Created date"
   "Location"
   "Area"
   "Description"
   "3D image"
   "Real image"
   "Gallery images"
   "Related projects"])
(def csv-headers-desc
  ["Ngôn ngữ. Giá trị: en|vn."
   "Tên dự án là k đ trùng trong cùng 1 ngôn ngữ"
   "Loại dự án. Loại giống nhau sẽ đc nhóm chung với nhau."
   "Hình tiêu đề. Chỉ 1 hình (image số ít). Copy hình zô thư mục img. Copy tên hình vào cột này. VD: img/hinh-a.jpg"
   "Ngày."
   "Vị trí"
   "Diện tích"
   "Mô tả"
   "Hình 3D. Chỉ 1 hình."
   "Hình thực tế. Chỉ 1 hình"
   "Tất cả hình liên quan của dự án. Cột này nhiều hình nên cần phải thêm dấu ; giữa mỗi hình."
   "Tên những dự án liên quan. Cột này cũng nhiều. Mỗi tên phải chính xác = tên dự án liên quan"])
(defn csv! []
  (let [csv-folder "./csv"
        image-root-path "/img"
        projects (->> project-list
                      (map (fn [[k v]]
                             (map #(cons (name k) %) v)))
                      (apply interleave))]
    (mkdir-if-none (str csv-folder image-root-path))
    (->> projects
         (map (fn [[lang & prj :as line]]
                (let [image-paths (->> (concat
                                         (project/get-column prj ::project/images)
                                         [(project/get-column prj ::project/image-hero)]
                                         [(project/get-column prj ::project/image-3d)]
                                         [(project/get-column prj ::project/image-real)])
                                       (map #(str "./public" %)))
                      line (mapv (fn [col]
                                   (if (vector? col)
                                     (string/join ";" col)
                                     col))
                                 line)]
                  (doseq [image-path image-paths]
                    (let [filename (-> (string/split image-path #"/")
                                       (last))
                          dest-path (str csv-folder image-root-path "/" filename)]
                      (cp image-path dest-path)))

                  ; return prj line
                  (-> (serial/item2csvline line)
                      (string/replace-all #"/assets/img/" "")))))
         (filter some?)
         (concat (->> [csv-headers csv-headers-desc]
                      (map serial/item2csvline)))
         (string/join serial/line-separator)
         (spit (str csv-folder "/projects.csv")))))

