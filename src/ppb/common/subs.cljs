(ns ppb.common.subs
  (:require
   [re-frame.core :as rf]))

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
