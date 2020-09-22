(ns ppb.common.page.service.panel
  (:require
    [ppb.common.page.footer :refer [quick-quote]]))

(defn service-panel []
  [:main.main
   [:div.container-fluid
    [:section.main-service.mt-4
     [:div.row.mb-5
      [:div.col-md-5
       [:div.content-left
        [:h2 "WHAT WE DO"]]]
      [:div.col-md-7
       [:div.content-right
        [:p "Since 2010, Pepper Builders & Makers have been transforming reveries into reality for home and business owners across Vietnam."]
        [:p "We are an all-inclusive construction company, delivering exemplary design and quality builds to align with any budget.
                  We manage every stage of your project, all the while maintaining clear-cut communication each step of the way."]
        [:p "Forging a personalised partnership with you built on trust, responsiveness, and enthusiasm, we’ll work together to build something that’s tailor-made for you."]
        [:p "Workflow integration from start to finish ensures that the project runs seamlessly and uses resources efficiently, so that the experience is stress free for everyone involved."]]]]
     [:div.page-banner.mb-6
      [:img {:src "assets/service-banner.jpg" :alt ""}]]
     [:div.row.mb-5
      [:div.col-md-5
       [:div.content-left
        [:h4 "Our approach"]
        [:p "We treat every project as if it was our own."]]]
      [:div.col-md-7
       [:div.content-right
        [:ul.service-list
         [:li
          [:span.service-number "01."]
          [:p "If you’re shopping around you may find a quick quote useful. Fill out a short survey through our website and find out what’s best for you."]]
         [:li
          [:span.service-number "02."]
          [:p "Understanding and inspiring each other is the first and most crucial step to set a successful collaboration in motion. Meet your design lead, project manager and ask away. Bring all your ideas, aspirations and inspirations to the table and we’ll listen and work together to cultivate them."]]
         [:li
          [:span.service-number "03."]
          [:p "You’ll receive our detailed proposal which will include timelines, summaries of each project stage, list of deliverables and costs. If this all looks good, we’ll sign a contract and get to work!"]]
         [:li
          [:span.service-number "04."]
          [:p "Working in close collaboration with you we’ll make sure your vision for the architectural and interior design is accurately captured. Together we’ll formulate the design concept and put it to paper, so that it gets built exactly how you envisioned it."]]
         [:li
          [:span.service-number "05."]
          [:p "We’re hands on, on site and on task for every aspect of the build, keeping you up to date throughout. We clearly detail timelines and costs in an itemised budget. We obtain the necessary construction permits, enlist the build team and select the suppliers best suited to you.Throughout the build, we manage the team and any subcontractors, meticulously tracking the budget and progress against our original plans, and ensuring any changes are given the green light by you."]]
         [:li
          [:span.service-number "06."]
          [:p "Time to move in! We’ll give you a comprehensive handover pack which includes appliance warranty documents, instructions and care manuals. Once you’ve done a thorough inspection to make sure everything is up to scratch then both parties will sign the project completion form, and hopefully raise a toast!"]]]]]]
     [:div.row.mb-5
      [:div.col-md-5
       [:div.content-left
        [:h4 "Our belief"]]]
      [:div.col-md-7
       [:div.content-right
        [:h5 "Every client deserves more than ready-made templates."]
        [:p "We make custom interior designs and furniture tailored to personal taste."]
        [:h5 "The power of collaboration."]
        [:p "We maintain a constant line of communication between all parties during all stages of the project cycle."]
        [:h5 "Every penny counts."]
        [:p "We are committed to delivering the highest level of quality on time and within budget."]
        [:h5 "Little things can make a big difference."]
        [:p "We guarantee quality down to the very last detail."]
        [:h5 "Both aesthetics and practicality matters."]
        [:p "We integrate cutting-edge interior design with innovative functionality for an outstanding result."]
        [:h5 "A human-centric approach to interior design."]
        [:p "We seek to create an environment that people feel connected to and stimulated by."]]]]]
    [quick-quote]]])