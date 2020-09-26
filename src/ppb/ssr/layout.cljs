(ns ppb.ssr.layout)

(defn layout [body]
  [:html {:lang "en"}
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1, shrink-to-fit=no"}]
    [:title "Pepper Builders & Makers"]
    [:link {:rel "stylesheet" :href "/css/app.css"}]]
   [:body
    [:noscript "This site is a JavaScript app. Please enable JavaScript to continue."]
    [body]
    [:script {:src "/js/app.js"}]
    [:script {:dangerouslySetInnerHTML {:__html (str "ppb.spa.core.init();")}}]]])
