(ns ppb.ssr.layout)

(defn layout [body]
  [:html {:lang "en"}
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:name "viewport" :content "width=device-width,initial-scale=1"}]
    [:title "ppb"]
    [:link {:rel "stylesheet" :href "css/style.css"}]]
   [:body
    [:noscript "This site is a JavaScript app. Please enable JavaScript to continue."]
    [body]
    [:script {:src "js/app.js"}]]])