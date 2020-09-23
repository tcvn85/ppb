(ns ppb.common.page.about.panel
  (:require
    [ppb.common.page.header :refer [header]]
    [ppb.common.page.footer :refer [footer quick-quote]]))

(defn about-panel []
  [:main.main
   [:div.container-fluid
    [:div.main-about.mt-4
     [:div.row.mb-5
      [:div.col-md-5
       [:h2 "WHO WE ARE"]]
      [:div.col-md-7
       [:div.content-right
        [:p "Our dedicated team is comprised of highly talented designers, architects, engineers, project managers, artisans, and construction personnel. Together we collaborate to bring forth world-class design, quality workmanship, meticulous attention to detail and customised client care. Having completed over 110 projects for Vietnamese and foreign clients, we have a wealth of experience spanning residential, hospitality, restaurant and retail projects."]
        [:p "We aren’t just builders and makers. We’re listeners. We set ourselves apart with personalised service. Each and every project starts by immersing ourselves in every aspect of the client’s objectives and dreams, whether they are personal and/or business related. Our solutions are original, innovative, and creative but at the same time cost-effective and time-efficient."]]]]
     [:div.page-banner.mb-6
      [:img {:src "/assets/about.png" :alt ""}]]
     [:div.row.mb-5
      [:div.col-md-5
       [:div.content-left
        [:h2 "OUR BELIEFS"]]]
      [:div.col-md-7
       [:div.content-right
        [:ul.list-dot
         [:li "We believe every client deserves more than ready-made templates. We make custom interior designs and custom built furniture tailored to personal taste."]
         [:li "We believe in the power of collaboration. We maintain a constant line of communication between all parties throughout all stages of the project."]
         [:li "We believe every penny counts. We are committed to delivering the highest level of quality on time and within budget."]
         [:li "We believe little things can make a big difference. We guarantee quality down to the very last detail."]
         [:li "We believe both aesthetics and practicality matter. We integrate cutting-edge interior design with innovative functionality for an outstanding result."]
         [:li "We believe in a human-centric approach to interior design. We seek to create an environment that people feel connected to and stimulated by."]]]]]]
    [quick-quote]]])