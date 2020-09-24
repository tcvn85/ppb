(ns ppb.spa.core
  (:require
    [reagent.dom :as rdom]
    [re-frame.core :as re-frame]
    [ppb.common.events]
    [ppb.common.router :as router]
    [ppb.common.views :as views]
    [ppb.common.config :as config]
    [ppb.spa.nav :as nav]
    [clojure.edn :as edn]))


(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)

  ; app id is from the ppb.common.views
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [views/main-panel] root-el)))

(defn read-init-state! []
  (edn/read-string js/window.initState))

(defn init []
  (router/init-routes)
  (re-frame/dispatch-sync [:common/initialize-db (read-init-state!)])
  (nav/hook-browser-navigation!)
  (dev-setup)
  (mount-root))

; init the app after the script is loaded
(when-not config/debug?
  (init))