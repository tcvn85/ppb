(ns ppb.server.server
  (:require
    [cljs.pprint :refer [pprint]]
    [applied-science.js-interop :as j]
    ["express" :as express]
    ["serve-static" :as serve-static]
    ["http" :as http]
    [ppb.ssr.core :as ssr]))


(defn handler [req res next]
  (let [uri (j/get req :path)
        page (ssr/render ^:js {:uri uri})]
    (if (nil? page)
      (j/call res :sendStatus 404)
      (j/call res :send page))))

(defn start! []
  (let [app-regex     #".*"
        app           (express)]

    (j/call app :use (j/call express :urlencoded #js{:extended true}))

    (j/call app :use (j/call express :json))

    (.use app (serve-static "public"))

    (j/call app :get app-regex handler)

    (let [http-server (j/call http :createServer app)]
      (j/call http-server :listen 3008)
      http-server)))

