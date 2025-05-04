(ns app.core
  (:require [org.httpkit.client :as http]
            [charred.api :as json])
  (:gen-class))

(defn bybit-url []
  "https://api.bybit.com")

(defn fetch-tickers []
  (-> (http/get (str (bybit-url) "/v5/market/tickers")
                {:query-params {"category" "linear"}})
      deref
      :body
      (json/read-json :key-fn keyword)
      :result
      :list
      (#(map :symbol %))))

(pprint (fetch-tickers))

(def exchanges [fetch-tickers fetch-tickers])

(defn get-all-tickers []
  (->> exchanges
       (map #(future {:result (time (%))}))
       (map deref))) ; blocks until each future resolves

(get-all-tickers)

(fetch-tickers)
