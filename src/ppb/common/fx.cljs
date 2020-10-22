
(ns ppb.common.fx
  (:require [ppb.common.ajax :as ajax]
            [cljs.core.async :refer [alts! chan <! >! close! timeout put!] :refer-macros [go]]
            [re-frame.core :as re-frame]
            [day8.re-frame.tracing :refer-macros [fn-traced]]
            [applied-science.js-interop :as j]
            [ppb.common.nav :as nav]
            [ppb.common.log :refer-macros [debug]]
            ))

(re-frame/reg-fx
  ::xhr
  (fn-traced [{:as   request
               :keys [on-failure
                      on-success]}]
    (go
      (let [should-dispatch-fail? (-> on-failure nil? not)
            response (<! (ajax/async-call (merge request
                                                 {:respond-with-error? should-dispatch-fail?
                                                  :raw-response? true})))]
        (if (:failure response)
          (when should-dispatch-fail?
            (re-frame/dispatch (conj on-failure (:failure response))))
          (re-frame/dispatch (conj on-success response))
          )))))

(re-frame/reg-fx
  ::route
  (fn-traced [uri]
    (nav/goto! uri)))

(re-frame/reg-fx
  ::redirect
  (fn-traced [url]
    (j/assoc! js/window :location url)))

