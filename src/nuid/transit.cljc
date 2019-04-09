(ns nuid.transit
  (:require
   [cognitect.transit :as t]
   [nuid.bytes :as bytes])
  (:refer-clojure :exclude [read]))

(defn read
  ([s] (read nil :utf8 s))
  ([opts s] (read opts :utf8 s))
  ([opts charset s]
   #?(:clj (let [in (java.io.ByteArrayInputStream.
                     (bytes/from s charset))
                 reader (t/reader in :json opts)]
             (t/read reader))
      :cljs (t/read (t/reader :json opts) s))))

(defn write
  ([data] (write nil :utf8 data))
  ([opts data] (write opts :utf8 data))
  ([opts charset data]
   #?(:clj (let [out (java.io.ByteArrayOutputStream.)
                 writer (t/writer out :json opts)]
             (t/write writer data)
             (bytes/to (.toByteArray out) charset))
      :cljs (t/write (t/writer :json opts) data))))

#?(:cljs (def exports #js {:write write :read read}))
