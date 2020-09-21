(ns ppb.ssr.core
  (:require
    [ppb.common.router :as router]
    [re-frame.core :as re-frame]
    [reagent.dom.server :as server]
    [ppb.common.views :as views]
    [ppb.common.events]
    [ppb.ssr.layout :as layout]
    [ppb.common.log :refer-macros [debug]]
    ))

(defn ^:export render [init-state]
  (router/init-routes)
  (let [{:keys [uri]
         :or {uri "/"}
         :as init-state} (js->clj init-state)
        {:keys [panel]} (router/uri->route uri)]
    (when (not= panel :route/not-found-panel)
      (re-frame/dispatch-sync [:common/initialize-db (-> init-state
                                                         (assoc :active-panel panel))])
      (debug (pr-str @re-frame.db/app-db))
      (->> (server/render-to-static-markup (layout/layout views/main-panel))
           (str "<!doctype html>")))))