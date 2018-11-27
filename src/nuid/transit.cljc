(ns nuid.transit
  (:require
   [cognitect.transit :as t]
   [nuid.utils :as utils]))

(def transit-read
  #?(:clj
     (fn
       ([s] (transit-read nil s))
       ([opts s]
        (let [in (java.io.ByteArrayInputStream. (.getBytes s "UTF-8"))
              reader (t/reader in :json opts)]
          (t/read reader))))
     :cljs
     (let [default-reader (t/reader :json)]
       (fn
         ([s] (t/read default-reader s))
         ([opts s] (t/read (t/reader :json opts) s))))))

(def transit-write
  #?(:clj
     (fn
       ([data] (transit-write nil data))
       ([opts data]
        (let [out (java.io.ByteArrayOutputStream.)
              writer (t/writer out :json opts)]
         (t/write writer data)
         (str out))))
     :cljs
     (let [default-writer (t/writer :json)]
       (fn
         ([data] (t/write default-writer data))
         ([opts data] (t/write (t/writer :json opts) data))))))

(defn hex-encode [data]
  (str "0x" (->> data transit-write utils/str->hex)))

(defn hex-decode [hex]
  (->> (subs hex 2) utils/hex->str transit-read))

#?(:cljs (def exports
           #js {:transit-write transit-write
                :transit-read transit-read
                :hex-encode hex-encode
                :hex-decode hex-decode}))
