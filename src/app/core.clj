(ns app.core
  (:require [app.window :as window])
  (:import
   [org.lwjgl.opengl GL11]))

(defn -main []
  (println "kek")
  (let [window (window/create-window)
        cnt (atom 0)
        red? (atom true)]
    (prn window)
    (println "Running main loop")
    (window/run-render-loop
     (fn []
       (when (zero? (mod @cnt 60))
         (swap! red? not))
       (if @red? (GL11/glClearColor 1.0 0.0 0.0 1.0)
           (GL11/glClearColor 0.0 1.0 0.0 1.0))
       (swap! cnt inc)) window)
    (window/cleanup-window window)
    (println "Cleaned up")))
