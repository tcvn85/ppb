(ns ppb.common.events
  (:require
    [ppb.common.log :refer-macros [debug]]
    [re-frame.core :as re-frame]
    [ppb.common.db :as db]
    [day8.re-frame.tracing :refer-macros [fn-traced]]
    [ppb.common.router :as router]))

(re-frame/reg-event-db
  :common/initialize-db
  (fn-traced [_ [_ init-state]]
   (merge db/default-db init-state)))

(re-frame/reg-event-db
 :common/route
 (fn-traced [db [_ uri]]
   (let [route (router/uri->route uri)]
     (assoc db :active-route route))))
