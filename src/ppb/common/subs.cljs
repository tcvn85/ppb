(ns ppb.common.subs
  (:require
    [re-frame.core :as rf]
    [ppb.common.router :as router]))

(rf/reg-sub
 ::name
 (fn [db]
   (:name db)))

(rf/reg-sub
  ::active-route
  (fn [db]
    (:active-route db)))

(rf/reg-sub
 ::active-panel
 :<- [::active-route]
 (fn [route _]
   (:panel route)))

(rf/reg-sub
  :common/uri
  (fn [db _]
    (:uri db)))

(rf/reg-sub
  :common/data
  (fn [db [_ router-id params]]
    (get-in db [(router/uri router-id params) :data])))

(rf/reg-sub
  :common/data-meta
  (fn [db [_ router-id params]]
    (get-in db [(router/uri router-id params) :meta-data])))