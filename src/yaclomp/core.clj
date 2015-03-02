(ns yaclomp.core
  (:require [instaparse.core :as insta]))

(def grammar "
  document = section topsection*
  headline = stars space+ uptonewline
  section = (blank-line | meta-line | uptonewline)*
  topsection = headline section
  meta-line = meta-hdr uptonewline
  meta-hdr = hash-plus word colon space
  word = #'[a-zA-Z]'+
  punct = #'\\.'
  space = ' '
  hash-plus = '#+'
  colon = ':'
  newline = '\n'
  blank-line = space* newline
  uptonewline = !(meta-hdr stars) #'[^\n]*'
  stars = #'\\*+'
")

(def parse-org (insta/parser grammar))
(count
 (insta/parses parse-org "
#+TITLE: All Your Org Mode Dox are Belong to Us...

A bit at the top.  

* Hey good lookin
 
* Another headline
* and another...

Here's some body copy.

*** with deeper nesting...
"))


;;=>
[:document
 [:section
  [:blank-line [:newline "\n"]]
  [:meta-line
   [:meta-hdr
    [:hash-plus "#+"]
    [:word "T" "I" "T" "L" "E"]
    [:colon ":"]
    [:space " "]]
   [:uptonewline "All Your Org Mode Dox are Belong to Us..."]]
  [:blank-line [:newline "\n"]]
  [:blank-line [:newline "\n"]]
  [:uptonewline "A bit at the top.  "]
  [:blank-line [:newline "\n"]]
  [:blank-line [:newline "\n"]]
  [:uptonewline "* Hey good lookin"]
  [:blank-line [:newline "\n"]]
  [:blank-line [:space " "] [:newline "\n"]]
  [:uptonewline "* Another headline"]
  [:blank-line [:newline "\n"]]
  [:uptonewline "* and another..."]
  [:blank-line [:newline "\n"]]
  [:blank-line [:newline "\n"]]
  [:uptonewline "Here's some body copy."]
  [:blank-line [:newline "\n"]]
  [:blank-line [:newline "\n"]]
  [:uptonewline "*** with deeper nesting..."]
  [:blank-line [:newline "\n"]]]]
