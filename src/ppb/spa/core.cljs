(ns ppb.spa.core
  (:require
    [cljs.reader :as edn]
    [reagent.dom :as rdom]
    [ppb.common.events :as events]
    [ppb.common.router :as router]
    [ppb.common.views :as views]
    [ppb.common.config :as config]
    [ppb.common.nav :as nav]
    [ppb.common.util :as util]
    [ppb.common.log :refer-macros [debug]]
    [re-frame.core :as rf]))


(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  ;(re-frame/clear-subscription-cache!)

  ; app id is from the ppb.common.views
  (let [root-el (.getElementById js/document "app")]
    ;(rdom/unmount-component-at-node root-el)
    (rdom/render [views/main-panel] root-el)))

(defn read-init-state! []
  (edn/read-string {:keywordize-keys true} js/ssrEDN))

(defn init []
  (router/init-routes!)
  (nav/init!)
  (rf/dispatch-sync [:common/initialize-db (read-init-state!)])
  (mount-root)
  (dev-setup))
