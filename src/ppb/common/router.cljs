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
  {:home         {:path  "/"
                  :panel :route/home-panel}
   :home1        {:path  "/index.html"
                  :panel :route/home-panel}
   :about        {:path  "/about.html"
                  :panel :route/about-panel}
   :contact      {:path  "/contact.html"
                  :panel :route/contact-panel}
   :service      {:path  "/services.html"
                  :panel :route/service-panel}
   :projects     {:path  "/projects.html"
                  :panel :route/projects-panel}
   :project-item {:path  "/projects/:slug"
                  :panel :route/project-item-panel}})

(defn uri
  ([route-id]
   (uri route-id nil))
  ([route-id params]
   (when-let [{:keys [path]} (routes route-id)]
     (secretary/render-route path params))))

(defn init-routes []
  (doall
    (for [{:keys [path panel]} (vals routes)]
      (defroute path path {:as params} {:path   path
                                        :panel  panel
                                        :params params})))
  (defroute "*" []
    :route/not-found-panel)
  )
