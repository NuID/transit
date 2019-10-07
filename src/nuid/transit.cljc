(ns nuid.transit
  (:refer-clojure :exclude [read type])
  (:require
   [cognitect.transit :as t]
   [nuid.bytes :as bytes]))

(defn read
  ([t]                   (read :utf8 :json nil  t))
  ([opts t]              (read :utf8 :json opts t))
  ([type opts t]         (read :utf8 type  opts t))
  ([charset type opts t]
   #?(:clj
      (let [bs (bytes/from t charset)]
        (with-open [in (java.io.ByteArrayInputStream. bs)]
          (let [reader (t/reader in type opts)]
            (t/read reader))))
      :cljs
      (t/read (t/reader type opts) t))))

(defn write
  ([x]                   (write :utf8 :json nil  x))
  ([opts x]              (write :utf8 :json opts x))
  ([type opts x]         (write :utf8 type  opts x))
  ([charset type opts x]
   #?(:clj
      (with-open [out (java.io.ByteArrayOutputStream.)]
        (let [writer (t/writer out type opts)]
          (t/write writer x)
          (bytes/str (.toByteArray out) charset)))
      :cljs
      (t/write (t/writer type opts) x))))

#?(:cljs (def exports #js {}))
