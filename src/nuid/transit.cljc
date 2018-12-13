(ns nuid.transit
  (:require
   [cognitect.transit :as t]
   [nuid.utils :as utils]))

(defn transit-read
  ([s] (transit-read nil s))
  ([opts s]
   #?(:clj (let [in (java.io.ByteArrayInputStream. (.getBytes s "UTF-8"))
                 reader (t/reader in :json opts)]
             (t/read reader))
      :cljs (t/read (t/reader :json opts) s))))

(defn transit-write
  ([data] (transit-write nil data))
  ([opts data]
   #?(:clj (let [out (java.io.ByteArrayOutputStream.)
                 writer (t/writer out :json opts)]
             (t/write writer data)
             (str out))
      :cljs (t/write (t/writer :json opts) data))))

(defn hex-encode
  ([data] (hex-encode nil data))
  ([opts data] (str "0x" (utils/str->hex (transit-write opts data)))))

(defn hex-decode
  ([hex] (hex-decode nil hex))
  ([opts hex] (transit-read opts (utils/hex->str (subs hex 2)))))

#?(:cljs (def exports
           #js {:transit-write transit-write
                :transit-read transit-read
                :hex-encode hex-encode
                :hex-decode hex-decode}))
