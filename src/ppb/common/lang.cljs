(ns ppb.common.lang
  (:require [re-frame.db :as db]))

(def langs #{:lang/en :lang/vi})

(def default-lang :lang/en)

(def translations
  {:lang/en {:projects "Projects"
             :services "Services"
             :about "About"
             :contact "Contact"
             :scroll-top "SCROLL TO TOP"
             :quick-quote "QUICK QUOTE"
             :address-l1 "19 Hoang Sa, Da Kao, District 1,"
             :address-l2 "Ho Chi Minh City, Vietnam"
             }
   :lang/vi {:projects "CÔNG TRÌNH"
             :services "DỊCH VỤ"
             :about "VỀ CHÚNG TÔI"
             :contact "LIÊN HỆ"
             :scroll-top "LÊN TRÊN"
             :quick-quote "BÁO GIÁ NHANH"
             :address-l1 "19 Hoàng Sa, Phường Đa Kao, Quận 1,"
             :address-l2 "TP. Hồ Chí Minh, Việt Nam"}})

(defn current-lang! []
  (:lang @db/app-db))

(defn tr
  ([tr-id]
   (tr tr-id nil))
  ([tr-id params]
   (assert (or (keyword? tr-id)
               (sequential? tr-id)))
   (let [lang    (current-lang!)
         tr-path (if (sequential? tr-id)
                   (cons lang tr-id)
                   [lang tr-id])
         tr-val (or (get-in translations tr-path)
                    (get-in translations (if (sequential? tr-id)
                                           (cons default-lang tr-id)
                                           [default-lang tr-id]))
                    "(Translating...)")]
     (if (fn? tr-val)
       (tr-val params)
       tr-val))))

(defn tr-span [tr-id params]
  [:span (tr tr-id params)])