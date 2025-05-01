(defproject app "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [thheller/shadow-cljs "2.19.1"]
                 [http-kit "2.9.0-alpha6"]]
  :repl-options {:init-ns app.core}
  :main app.core
  :profiles {:uberjar {:aot :all}})
