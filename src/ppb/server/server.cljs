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
    [ppb.common.router :as router]))

(def res-path "./public")

(defn handler [req res next]
  (let [uri (j/get req :path)
        uri (if (= uri "/")
              "/index.html"
              uri)]
    (if (string/ends-with? uri ".html")
      (let [_ (ssr/init)
            txt (some->> (router/uri-to-txt-path uri)
                         (str res-path)
                         (#(fs/readFileSync % ^:js {:encoding "utf8" :flag "r"}))
                         (.toString))
            page (ssr/render ^:js {:uri uri
                                   :txt txt})]
        (if (nil? page)
          (j/call res :sendStatus 404)
          (j/call res :send page)))
      (next))))

(defn start! []
  (let [app-regex     #".*"
        app           (express)]

    (j/call app :use (j/call express :urlencoded #js{:extended true}))

    (j/call app :use (j/call express :json))

    (.use app (serve-static res-path))

    (j/call app :get app-regex handler)

    (let [http-server (j/call http :createServer app)]
      (j/call http-server :listen 3008)
      http-server)))

