(defproject ppb "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/clojurescript "1.10.773"
                  :exclusions [com.google.javascript/closure-compiler-unshaded
                               org.clojure/google-closure-library
                               org.clojure/google-closure-library-third-party]]
                 [thheller/shadow-cljs "2.11.0"]
                 [reagent "0.10.0"]
                 [re-frame "1.1.1"]
                 [day8.re-frame/tracing "0.6.0"]
                 [clj-commons/secretary "1.2.4"]
                 [appliedscience/js-interop "0.2.4"]

                 [binaryage/devtools "1.0.2"]
                 [day8.re-frame/re-frame-10x "0.7.0"]]

  :min-lein-version "2.9.0"

  :source-paths ["src"]

  :clean-targets ^{:protect false} ["public/js" "workers-site/worker" "workers-site/ppb-ssr" "dist"]
  )
