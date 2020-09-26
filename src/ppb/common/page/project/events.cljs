(ns ppb.common.page.project.events
  (:require [re-frame.core :as rf]
            [day8.re-frame.tracing :refer-macros [fn-traced]]
            [ppb.common.page.project.subs :as subs]
            [ppb.common.events :as events]
            [ppb.common.fx :as fx]
            [ppb.common.router :as router]
            [ppb.common.config :as config]
            [ppb.common.util :as util]))

(rf/reg-event-fx
  ::load-more
  (fn-traced [{db :db} _]
    (let [prj-count (-> @(rf/subscribe [::subs/projects])
                        (count))
          uri (util/get-current-uri!)]
      (cond-> {:db db}
              @(rf/subscribe [::subs/prj-more?])
              (assoc ::fx/xhr {:url        (router/txt-path-options uri (+ prj-count config/page-count))
                               :on-success [::load-more-ok]
                               :on-failure [::load-more-fail]})))))

(rf/reg-event-db
  ::load-more-ok
  (fn-traced [db [_ response]]
    (assoc db :projects (events/txt->state response))))

