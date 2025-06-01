(ns app.core
  (:require [app.window :as window]
            [missionary.core :as m]
            [aleph.http :as ws]
            [org.httpkit.client :as client]
            [manifold.stream :as stream]
            [pjson.core :as pjson])
  (:import
   [org.lwjgl.opengl GL11])
  (:gen-class))

(set! *warn-on-reflection* true)

;; (defonce conn (atom nil))

;; (defn connect! [url]
;;   (http/web
;;    url
;;    {:on-recieve (fn [msg] (println "msg: " msg))
;;     :on-error (fn [e] (println "err: " e))
;;     :on-close (fn [status] (println "closed with status: " status))
;;     :on-connect (fn [ws]
;;                   (println "connected")
;;                   (reset! conn ws))}))

;; (connect! "wss://api.hyperliquid.xyz/ws")

;; (defn run-task [s]
;;   (m/via m/blk
;;          (Thread/sleep s)
;;          (println "done" s)
;;          (reduce + (range 1000000))))

;; (time (m/? (m/join vector
;;                    (run-task 500)
;;                    (run-task 250)
;;                    (run-task 100))))

;; (defn -main []
;;   (let [window (window/create-window)
;;         cnt (atom 0)
;;         red? (atom true)]
;;     (println "Running main loop")
;;     (window/run-render-loop
;;      (fn []
;;        (when (zero? (mod @cnt 60))
;;          (swap! red? not))
;;        (if @red? (GL11/glClearColor 1.0 0.0 0.0 1.0)
;;            (GL11/glClearColor 0.0 1.0 0.0 1.0))
;;        (swap! cnt inc)) window)
;;     (window/cleanup-window window)
;;     (println "Cleaned up")))

(defn print-ts-diff [book]
  (println book)
  (let [ts-server (:time (:data book))
        ts-local (System/currentTimeMillis)]
    (println ts-local ts-server)
    (println "diff: " (- ts-local ts-server))))

(defn print-bbo [book]
  (assert (= (:channel book) "bbo"))
  (let [bid (nth (:bbo (:data book)) 0)
        ask (nth (:bbo (:data book)) 1)]
    (println "bid: " bid " ask: " ask)))

(defn connect! []
  (let [uri "wss://api.hyperliquid.xyz/ws"]
    (println "Connecting to:" uri)
    (let [conn @(ws/websocket-client uri)]
      (println "Connected!")

      ;; Optionally send a message
      (let [subscribe-msg
            (pjson/write-str
             {:method "subscribe"
              :subscription {:type "bbo"
                             :coin "BTC"}})]

        (stream/put! conn subscribe-msg)
        (println subscribe-msg))

      ;; Print incoming messages
      (stream/consume
       (fn [msg]
         (let [parsed (pjson/read-str-keys msg)]
           ;; (println (time (pjson/read-str-keys msg)))
           (when (= (:channel parsed) "bbo")
             (print-bbo parsed))))
       conn)

      ;; Return the connection stream for future use
      conn)))

(def aa (connect!))

(stream/close! aa)

(defn -main [] (connect!)
  (@(promise)))

;; (def x @(client/post "https://api.hyperliquid.xyz/info" {:body (pjson/write-str {:type "spotMeta"}) :headers {"Content-Type" "application/json"}}))


