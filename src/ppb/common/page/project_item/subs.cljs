(ns ppb.common.page.project-item.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub
  ::current-prj
  (fn [db _]
    (-> db :project-item :data first)))

(rf/reg-sub
  ::related-projects
  (fn [db _]
    (->> db :project-item :meta-data)))