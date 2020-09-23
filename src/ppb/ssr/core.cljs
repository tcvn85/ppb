(ns ppb.ssr.core
  (:require
    [ppb.common.router :as router]
    [re-frame.core :as re-frame]
    [reagent.dom.server :as server]
    [ppb.common.views :as views]
    [ppb.common.events]
    [ppb.ssr.layout :as layout]
    [ppb.common.log :refer-macros [debug]]
    [ppb.common.serial :as serial]
    [applied-science.js-interop :as j]))

(defn ^:export init []
  (router/init-routes))

(defn ^:export render [init-state]
  (let [{:keys [uri txt]
         :or {uri "/"}} (js->clj init-state)
        {:keys [panel] :as route} (router/uri->route uri)]
    (when (not= panel :route/not-found-panel)
      (re-frame/dispatch-sync [:common/initialize-db {:active-panel panel
                                                      :data (->> (serial/lines2items txt)
                                                                 (take serial/default-limit))}])
      ;(debug (pr-str @re-frame.db/app-db))
      ;(println (serial/lines2items txt))
      (->> (server/render-to-static-markup (layout/layout views/main-panel))
           (str "<!doctype html>")))))

(defn ^:export uri-to-txt-path [uri]
  (let [{:keys [txt-path]} (router/uri->route uri)]
    (cond
      (string? txt-path) txt-path
      (fn? txt-path) (txt-path uri)
      true nil)))

