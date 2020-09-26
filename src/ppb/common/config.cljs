(ns ppb.common.config)

(def debug?
  ^boolean goog.DEBUG)

(def lang #{:en :vn})

(defn lang-prefix [lang s]
  (cond->> s
           (not= lang :en) (str "/" (name lang))))

(def page-count 10)