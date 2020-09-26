(ns ppb.spa.core
  (:require
    [reagent.dom :as rdom]
    [re-frame.core :as re-frame]
    [ppb.common.events :as events]
    [ppb.common.router :as router]
    [ppb.common.views :as views]
    [ppb.common.config :as config]
    [ppb.common.nav :as nav]
    [ppb.common.util :as util]
    [ppb.common.log :refer-macros [debug]]))


(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)

  ; app id is from the ppb.common.views
  (let [root-el (.getElementById js/document "app")]
    ;(rdom/unmount-component-at-node root-el)
    (rdom/render [views/main-panel] root-el)))

(defn init []
  (router/init-routes!)
  (nav/init!)
  (events/init-data-async! (util/get-current-uri!)
                           (fn [state]
                             (events/init-data-sync! state)
                             (mount-root)))
  (dev-setup))
