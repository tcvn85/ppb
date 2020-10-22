(ns ppb.common.events
  (:require
    [ppb.common.log :refer-macros [debug]]
    [cljs.core.async :refer [alts! chan <! >! close! timeout put!] :refer-macros [go]]
    [re-frame.core :as rf]
    [re-frame.db :as rf.db]
    [ppb.common.db :as db]
    [ppb.common.fx :as fx]
    [day8.re-frame.tracing :refer-macros [fn-traced]]
    [ppb.common.router :as router]
    [ppb.common.serial :as serial]
    [ppb.common.ajax :as ajax]
    [ppb.common.config :as config]))

(rf/reg-event-db
  :common/initialize-db
  (fn-traced [_ [_ init-state]]
    (merge db/default-db init-state)))

(defn no-data? [uri]
  (nil? (get-in @rf.db/app-db [uri])))

(defn txt->state [txt]
  (let [[meta-data & lines-data :as data] (serial/lines2items txt)]
    (when (some? data)
      {:meta-data meta-data
       :data (into [] lines-data)})))

(defn init-data-async! [db new-uri]
  (let [{:keys [txt-path id] :as route} (router/uri->route new-uri)]
    (when (and (some? txt-path)
               (no-data? new-uri))
      (go
        (let [txt (<! (ajax/async-call {:url (router/txt-path-options new-uri config/page-count)
                                        :raw-response? true}))]
          (assert (some? txt))
          (rf/dispatch [:common/set-init-data new-uri txt]))))))

(rf/reg-event-db
  :common/set-init-data
  (fn-traced [db [_ uri txt]]
    (assoc-in db [uri] (txt->state txt))))

(rf/reg-event-fx
  :common/set-route
  (fn-traced [{db :db} [_ uri]]
    (let [{:keys [lang]
           :as route} (router/uri->route uri)]
      (init-data-async! db uri)
      {:db (assoc db
             :active-route route
             :lang lang
             :uri uri)})))

(rf/reg-event-fx
  :common/set-lang
  (fn-traced [{db :db} [_ new-lang]]
    (assert (some? (#{:lang/en :lang/vi} new-lang)))
    (let [{{:keys [id params]} :active-route} db
          uri (router/uri id params new-lang)]
      {::fx/route uri})))