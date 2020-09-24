(ns ppb.common.router
  (:require-macros [secretary.core :refer [defroute]])
  (:import [goog History]
           [goog.history EventType Html5History])
  (:require
    [secretary.core :as secretary]
    [ppb.common.util :as util]
    ))

(defn uri->route [uri]
  (secretary/dispatch! uri))

(def routes
  {:home         {:id    :home
                  :path  "/"
                  :panel :route/home-panel}
   :home1        {:id    :home1
                  :path  "/index.html"
                  :panel :route/home-panel}
   :about        {:id    :about
                  :path  "/about.html"
                  :panel :route/about-panel}
   :contact      {:id    :contact
                  :path  "/contact.html"
                  :panel :route/contact-panel}
   :service      {:id    :service
                  :path  "/services.html"
                  :panel :route/service-panel}
   :projects     {:id    :projects
                  :path  "/projects.html"
                  :panel :route/projects-panel
                  :txt-path "projects.txt"}
   :project-item {:id    :project-item
                  :path  "/projects/:slug"
                  :panel :route/project-item-panel
                  :txt-path util/ext-html-to-txt}})

(defn uri
  ([route-id]
   (uri route-id nil))
  ([route-id params]
   (when-let [{:keys [path]} (routes route-id)]
     (secretary/render-route path params))))

(defn init-routes []
  (doall
    (for [{:as route :keys [path]} (vals routes)]
      (defroute path path {:as params} (assoc route :params params))))
  (defroute "*" [] {:panel :route/not-found-panel}))
