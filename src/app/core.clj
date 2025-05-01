(ns app.core
  (:require [org.httpkit.client :as http])
  (:gen-class))

(defn bybit-url []
  "https://api.bybit.com")

(defn fetch-tickers []
  @(http/get (str (bybit-url) "/v5/market/tickers")
             {:query-params {"category" "linear"}}))


(defn -main [& args]
  (time (let [resp (fetch-tickers)]))
  (time (let [resp (fetch-tickers)])))
