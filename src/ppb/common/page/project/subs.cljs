(ns ppb.common.page.project.subs
  (:require [re-frame.core :as rf]
            [ppb.common.log :refer-macros [debug]]
            [ppb.common.router :as router]))

(rf/reg-sub
  ::projects
  (fn [db [_ category]]
    (if (nil? category)
      (get-in db [(router/uri ::router/projects) :data])
      (get-in db [(router/uri ::router/projects-cat {:category category}) :data]))))

(rf/reg-sub
  ::meta
  (fn [db [_ category]]
    (if (nil? category)
      (get-in db [(router/uri ::router/projects) :meta-data])
      (get-in db [(router/uri ::router/projects-cat {:category category}) :meta-data]))))

(rf/reg-sub
  ::prj-total
  :<- [::meta]
  (fn [meta [_ category]]
    (if (nil? category)
      (get meta "all")
      (get meta category))))

(rf/reg-sub
  ::prj-more?
  :<- [::projects]
  :<- [::prj-total]
  (fn [[prjs total] [_ category]]
    (< (count prjs) total)))