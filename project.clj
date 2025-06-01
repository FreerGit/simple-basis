(defproject app "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [io.github.spair/imgui-java-binding "1.89.0"]
                 [io.github.spair/imgui-java-lwjgl3 "1.89.0"]
                 [io.github.spair/imgui-java-natives-linux "1.89.0"]
                 [org.lwjgl/lwjgl "3.3.3"]
                 [org.lwjgl/lwjgl "3.3.3" :classifier "natives-linux"]
                 [org.lwjgl/lwjgl-glfw "3.3.3"]
                 [org.lwjgl/lwjgl-glfw "3.3.3" :classifier "natives-linux"]
                 [org.lwjgl/lwjgl-opengl "3.3.3"]
                 [org.lwjgl/lwjgl-opengl "3.3.3" :classifier "natives-linux"]
                 [http-kit "2.9.0-alpha6"]
                 [missionary "b.45"]
                 [aleph "0.8.3"]
                 [manifold "0.4.3"]
                 [pjson "1.0.0"]
                 [com.cnuernber/charred "1.037"]]
  :repl-options {:init-ns app.core}
  :main app.core
  :profiles {:uberjar {:aot :all}})
