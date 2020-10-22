(ns ppb.common.router
  (:require-macros [secretary.core :refer [defroute]])
  (:import [goog History]
           [goog.history EventType Html5History])
  (:require
    [secretary.core :as secretary]
    [ppb.common.util :as util]
    [clojure.string :as string]
    [ppb.common.lang :as language]))

(defn uri->route [uri]
  (secretary/dispatch! uri))

(def routes (atom {}))

(def base-routes {::home         {:id    ::home
                                  :path  "/"
                                  :panel :route/home-panel}
                  ::home1        {:id    ::home1
                                  :path  "/index.html"
                                  :panel :route/home-panel}
                  ::about        {:id    ::about
                                  :path  "/about.html"
                                  :panel :route/about-panel}
                  ::contact      {:id    ::contact
                                  :path  "/contact.html"
                                  :panel :route/contact-panel}
                  ::service      {:id    ::service
                                  :path  "/services.html"
                                  :panel :route/service-panel}
                  ::projects     {:id       ::projects
                                  :path     "/projects.html"
                                  :panel    :route/projects-panel
                                  :txt-path "/projects.txt"}
                  ::projects-cat {:id       ::projects-cat
                                  :path     "/projects-category/:category"
                                  :panel    :route/projects-cat
                                  :txt-path util/ext-html-to-txt}
                  ::project-item {:id       ::project-item
                                  :path     "/projects/:slug"
                                  :panel    :route/project-item-panel
                                  :txt-path util/ext-html-to-txt}})

(defn merge-lang-kw
  ([lang kw]
   (merge-lang-kw lang
                  kw
                  :ppb.common.router))
  ([lang kw merge-ns]
   (let [[l k] (mapv (fn [i]
                       (-> (name i)
                           (string/split #"\.")
                           (last)))
                     [lang kw])
         lk (str l "." k)]
     (keyword merge-ns lk))))

(defn to-lang-path [lang path]
  (let [l (name lang)]
    (str "/" l path)))

(defn uri
  ([route-id]
   (uri route-id nil nil))
  ([route-id params]
   (uri route-id params nil))
  ([route-id params lang]
   (when-let [{:keys [path]} (get @routes (merge-lang-kw (or lang
                                                             (language/current-lang!))
                                                         route-id))]
     (let [ret (secretary/render-route path params)
           html-tail? (string/ends-with? ret ".html")]
       (cond-> ret
               (not html-tail?) (str ".html"))))))

(defn init-routes! []
  (->> (for [{:keys [path id panel txt-path]} (vals base-routes)
             lang                             language/langs
             :let [default-lang? (= lang language/default-lang)
                   lang-path (if default-lang?
                               path
                               (to-lang-path lang path))
                   lang-id (merge-lang-kw lang id :ppb.common.router)
                   lang-route (if default-lang?
                                {:id       lang-id
                                 :path     path
                                 :panel    panel
                                 :txt-path txt-path
                                 :lang     lang}
                                {:id       lang-id
                                 :path     lang-path
                                 :panel    (merge-lang-kw lang panel :route)
                                 :txt-path (if (string? txt-path)
                                             (to-lang-path lang txt-path)
                                             txt-path)
                                 :lang     lang})]]
         (do
           (secretary/add-route! lang-path #(merge
                                              lang-route
                                              {:params   %}))
           [lang-id lang-route]))
       (into {})
       (reset! routes))
  (defroute "*" [] {:panel :route/not-found-panel}))

(defn ^:export uri-to-txt-path [uri]
  (let [{:keys [txt-path]} (uri->route uri)]
    (if (some? txt-path)
      (->> (cond
             (string? txt-path) txt-path
             (fn? txt-path) (txt-path uri)
             true (assert false))
           (str "/assets/txt")))))

(defn txt-path-options [uri limit]
  (some-> (uri-to-txt-path uri)
          (str "/limit/" limit)))