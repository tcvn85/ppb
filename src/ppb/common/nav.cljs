(ns ppb.common.nav
  (:import [goog History]
           [goog.history EventType Html5History])
  (:require
    [ppb.common.log :refer-macros [debug info]]
    [goog.events :as gevents]
    [re-frame.core :as rf]
    [applied-science.js-interop :as j]))

(def history (atom nil))

(defn valid? []
  (some? @history))

(defn goto [uri]
  (when (valid?)
    (.setToken ^js @history uri)))

(defn init! []
  (let [hist (Html5History.)]
    (gevents/listen
      hist
      EventType/NAVIGATE
      (fn [event]
        (let [uri (.-token ^js event)]
          (rf/dispatch [:common/route uri]))))
    (doto hist
      (.setUseFragment false)
      (.setPathPrefix "")
      (.setEnabled true))
    (reset! history hist)

    hist))
