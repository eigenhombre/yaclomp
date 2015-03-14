(defproject yaclomp "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :aliases {"autotest" ["midje" ":autotest"]}
  :profiles {:dev {:dependencies [[midje "1.6.3"]
                                  [speclj "3.2.0"]]
                   :plugins [[lein-midje "3.1.3"]
                             [speclj "3.2.0"]]}
             :uberjar {:aot :all}}
  :test-paths ["spec"]
  :main yaclomp.core
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [instaparse "1.3.5"]])
