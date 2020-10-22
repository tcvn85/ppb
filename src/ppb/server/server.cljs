(ns ppb.server.server
  (:require
    [cljs.pprint :refer [pprint]]
    [applied-science.js-interop :as j]
    ["express" :as express]
    ["serve-static" :as serve-static]
    ["http" :as http]
    [ppb.ssr.core :as ssr]
    ["fs" :as fs]
    [clojure.string :as string]
    [ppb.common.serial :as serial]
    [ppb.common.log :refer-macros [debug]]
    [ppb.common.router :as router]
    [ppb.common.config :as config]))

(def res-path "./public")

(defn read-file [path limit]
  (let [content (-> (fs/readFileSync path ^:js {:encoding "utf8" :flag "r"})
                    (.toString)
                    (string/split serial/line-separator))]
    (some->> content
             (take (inc limit)) ; +1 due to meta-data
             (string/join serial/line-separator))))

(defn handler [req res next]
  (let [uri (j/get req :path)
        uri (-> (if (= uri "/")
                  "/index.html"
                  uri)
                (js/decodeURIComponent))]
    (if (string/ends-with? uri ".html")
      (let [_ (ssr/init)
            txt (some-> (router/uri-to-txt-path uri)
                        (#(str res-path %))
                        (read-file config/page-count))
            page (ssr/render ^:js {:uri uri
                                   :txt txt})]
        (if (nil? page)
          (j/call res :sendStatus 404)
          (j/call res :send page)))
      (next))))

(defn txt-limit-handler [req res next]
  (let [uri (-> (j/get req :path)
                (js/decodeURIComponent))
        [txt-path limit] (string/split uri #"/limit/")
        limit    (-> (int limit)
                     inc)
        txt-path (str res-path txt-path)]
    (if (fs/existsSync txt-path)
      (j/call res :send (some->> txt-path
                                 (#(fs/readFileSync % ^:js {:encoding "utf8" :flag "r"}))
                                 (.toString)
                                 (#(string/split % serial/line-separator))
                                 (take limit)
                                 (string/join serial/line-separator)))
      (j/call res :sendStatus 404))))

(defn start! []
  (let [app-regex     #".*"
        app           (express)]

    (j/call app :use (j/call express :urlencoded #js{:extended true}))

    (j/call app :use (j/call express :json))

    (.use app (serve-static res-path))

    (j/call app :get #".txt/limit/[0-9]+" txt-limit-handler)

    (j/call app :get app-regex handler)

    (let [http-server (j/call http :createServer app)]
      (j/call http-server :listen 3008)
      http-server)))

