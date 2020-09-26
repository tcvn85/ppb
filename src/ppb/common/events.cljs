(ns ppb.common.events
  (:require
    [ppb.common.log :refer-macros [debug]]
    [cljs.core.async :refer [alts! chan <! >! close! timeout put!] :refer-macros [go]]
    [re-frame.core :as re-frame]
    [re-frame.db :as rf.db]
    [ppb.common.db :as db]
    [day8.re-frame.tracing :refer-macros [fn-traced]]
    [ppb.common.router :as router]
    [ppb.common.serial :as serial]
    [ppb.common.ajax :as ajax]
    [applied-science.js-interop :as j]
    [ppb.common.util :as util]))

(re-frame/reg-event-db
  :common/initialize-db
  (fn-traced [_ [_ init-state]]
   (merge db/default-db init-state)))

(defn no-data? [route-id]
  (nil? (get-in @rf.db/app-db [route-id])))

(defn txt->state [txt]
  (let [[meta-data & lines-data :as data] (serial/lines2items txt)]
    (when (some? data)
      {:meta-data meta-data
       :data lines-data})))

(defn init-data-sync! [{:keys [txt uri route]
                        {:keys [id]} :route}]
  (let [init-state (-> {:active-route route
                        :uri uri}
                       (assoc id (txt->state txt)))]
    (re-frame/dispatch-sync [:common/initialize-db init-state])
    init-state))

(defn init-data-async! [uri callback]
  (let [{:keys [txt-path id] :as route} (router/uri->route uri)]
    (if (and (some? txt-path)
             (no-data? id))
      (go
        (debug "init-data-async! and refresh data" route)
        (let [txt (<! (ajax/async-call {:url (router/uri-to-txt-path uri) :raw-response? true}))]
          (assert (some? txt))
          (when (fn? callback)
            (callback {:txt txt :route route :uri uri})))))))

(re-frame/reg-event-db
  :common/set-init-data
  (fn-traced [db [_ route-id txt]]
    (assoc-in db [route-id] (txt->state txt))))

(re-frame/reg-event-fx
  :common/route
  (fn-traced [{db :db} [_ uri]]
    (let [{:keys [id]
           :as route} (router/uri->route uri)]
      (when (no-data? id)
        (init-data-async! uri (fn [{:keys [txt]}]
                                (re-frame/dispatch [:common/set-init-data id txt]))))
      {:db (assoc db :active-route route)})))