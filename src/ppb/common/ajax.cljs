(ns ppb.common.ajax
  (:require-macros [ppb.common.log :refer [info error debug]])
  (:require [cljs.core.async :refer [alts! chan <! >! close! timeout put!] :refer-macros [go]]
            [ajax.core :refer [GET POST DELETE json-request-format json-response-format raw-response-format]]
            [applied-science.js-interop :as j]))

(def method-fns
  {:get GET
   :post POST
   :delete DELETE})

(defn async-call [{:keys [method url params respond-with-error? raw-response?]
                   :or   {respond-with-error? false
                          raw-response?       false
                          method              :get
                          }}]
  (let [ch (chan)
        method-fn (method-fns method)]
    (method-fn url
               (cond-> {:params params

                        :handler (fn [response]
                                   (go
                                     (>! ch response)
                                     (close! ch)))

                        :error-handler (fn [err]
                                         (go
                                           (do
                                             (error url "async-call - something went wrong" err)
                                             (when (and (true? respond-with-error?)
                                                        (some? err))
                                               (>! ch err))
                                             (close! ch))))

                        :headers (cond-> {})
                        :response-format (if (or raw-response? (= method :delete))
                                           (raw-response-format)
                                           (json-response-format {:keywords? true}))}))
    ch))