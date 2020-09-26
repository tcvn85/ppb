(ns ppb.common.page.project.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub
  ::projects
  (fn [db _]
    (get-in db [:projects :data])))

(rf/reg-sub
  ::meta
  (fn [db _]
    (get-in db [:projects :meta-data])))

(rf/reg-sub
  ::prj-total
  :<- [::meta]
  (fn [meta _]
    (get meta "all")))

