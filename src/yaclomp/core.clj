(ns yaclomp.core
  (:require [instaparse.core :as insta]))


(def grammar "
  document = section topsection*
  topsection = headline section
  section = (blank-line | uptonewline)*
  <blank-line> = #'\\s*\n'
  <WS> = ' '*
  <uptonewline> = #'[^\n]*'
  headline = stars WS uptonewline
  stars = #'\\*+'
")

(def parse-org (insta/parser grammar))

(parse-org "
A bit at the top.  

* Hey good lookin'
 
* Another headline
* and another...

Here's some body copy.

*** with deeper nesting...
")

;;=>
[:document
 [:section "\n" "A bit at the top.  " "\n\n"]
 [:topsection
  [:headline [:stars "*"] " Hey good lookin'"]
  [:section "\n \n"]]
 [:topsection
  [:headline [:stars "*"] " Another headline"]
  [:section "\n"]]
 [:topsection
  [:headline [:stars "*"] " and another..."]
  [:section "\n\n" "Here's some body copy." "\n\n"]]
 [:topsection
  [:headline [:stars "***"] " with deeper nesting..."]
  [:section "\n"]]]
