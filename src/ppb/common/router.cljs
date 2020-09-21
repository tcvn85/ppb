(ns ppb.common.router
  (:require-macros [secretary.core :refer [defroute]])
  (:import [goog History]
           [goog.history EventType Html5History])
  (:require
   [secretary.core :as secretary]
   [goog.events :as gevents]
   [re-frame.core :as re-frame]
   ))

(defn uri->route [uri]
  (secretary/dispatch! uri))

(def routes
  {:home {:path "/"
          :panel :route/home-panel}
   :about {:path "/about"
           :panel :route/about-panel}})

(defn uri [route-id args]
  (when-let [{:keys [path]} (routes route-id)]
    (secretary/render-route path args)))

(defn init-routes []
  (doall
    (for [{:keys [path panel]} (vals routes)]
      (defroute path path {:as params} {:path path
                                        :panel panel
                                        :params params})))
  (defroute "*" []
    :route/not-found-panel)
  )
