(ns ppb.ssr.core
  (:require
    [ppb.common.router :as router]
    [re-frame.core :as re-frame]
    [reagent.dom.server :as server]
    [ppb.common.views :as views]
    [ppb.common.events :as events]
    [ppb.ssr.layout :as layout]
    [ppb.common.log :refer-macros [debug]]
    [ppb.common.serial :as serial]
    [applied-science.js-interop :as j]))

(defn ^:export init []
  (router/init-routes!))

(defn prepend-doctype [s]
  (str "<!doctype html>" s))

(defn ^:export render [init-state]
  (let [{:keys [uri txt]
         :or   {uri "/index.html"}} (js->clj init-state)
        {:keys [panel] :as route} (router/uri->route uri)]
    (when (not= panel :route/not-found-panel)
      (events/init-data-sync! {:uri uri :txt txt :route route})
      (debug "render app-db" (pr-str @re-frame.db/app-db))
      (-> (layout/layout views/main-panel)
          (server/render-to-static-markup)
          (prepend-doctype))
      )))

