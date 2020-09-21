(ns ppb.spa.nav
  (:import [goog History]
           [goog.history EventType Html5History])
  (:require
    [ppb.common.log :refer-macros [debug info]]
    [secretary.core :as secretary]
    [goog.events :as gevents]
    [re-frame.core :as rf]
    [applied-science.js-interop :as j]))

(def history (atom nil))

(defn valid? []
  (some? @history))

(defn goto [uri]
  (if (valid?)
    (.setToken ^js @history uri)))

(defn hook-browser-navigation! []
  (let [hist (Html5History.)]
    (gevents/listen
      hist
      EventType/NAVIGATE
      (fn [event]
        (let [panel (secretary/dispatch! (.-token ^js event))]
          (rf/dispatch [:common/route (.-token ^js event)]))))
    (doto hist
      (.setUseFragment false)
      (.setPathPrefix "")
      (.setEnabled true))
    (reset! history hist)

    hist))
