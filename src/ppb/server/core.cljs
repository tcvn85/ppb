(ns ppb.server.core
  (:require [applied-science.js-interop :as j]
            [cljs.nodejs :as nodejs]
            [ppb.server.server :as server]
            [ppb.server.migrate]
            [ppb.common.log :refer-macros [debug]]))

(nodejs/enable-util-print!)

(defonce server-instance (atom nil))

(defn start! []
  (let [server (server/start!)]
    (debug "start ...")
    (reset! server-instance server)))

(defn stop! [done]
  (when-some [srv @server-instance]
    (secretary.core/reset-routes!)
    (j/call srv :close (fn [err]
                         (debug "stop! HTTP server")
                         (done)))))

