(ns ppb.common.log)

(defmacro info [& args]
  `(js/console.log ~@args))

(defmacro log [& args]
  `(js/console.log ~@args))

(defmacro debug [& args]
  `(when (true? goog.DEBUG)
     (js/console.log ~@args)))

(defmacro error [& args]
  `(js/console.error ~@args))

(defmacro spy [& args]
  `(when (true? goog.DEBUG)
     (js/console.log ~@args)
     ~@args))