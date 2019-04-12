(ns nuid.transit
  (:require
   [cognitect.transit :as t]
   [nuid.bytes :as bytes])
  (:refer-clojure :exclude [read type]))

(defn read
  ([t] (read :utf8 :json nil t))
  ([opts t] (read :utf8 :json opts t))
  ([type opts t] (read :utf8 :json opts t))
  ([charset type opts t]
   #?(:clj (let [in (java.io.ByteArrayInputStream.
                     (bytes/from t charset))
                 reader (t/reader in type opts)]
             (t/read reader))
      :cljs (t/read (t/reader type opts) t))))

(defprotocol Wrappable
  "Wraps a value immediately prior to writing.
  This is useful in typing JS values to indicate
  how they should be written."
  (wrap [x]))

(defprotocol TransitWritable
  (rep [x]))

(defn write
  ([x] (write :utf8 :json nil x))
  ([opts x] (write :utf8 :json opts x))
  ([type opts x] (write :utf8 type opts x))
  ([charset type opts x]
   (let [x (if (satisfies? Wrappable x) (wrap x) x)]
     #?(:clj (let [out (java.io.ByteArrayOutputStream.)
                   writer (t/writer out type opts)]
               (t/write writer x)
               (bytes/str (.toByteArray out) charset))
        :cljs (t/write (t/writer type opts) x)))))

#?(:cljs (def exports #js {:write write :read read}))
