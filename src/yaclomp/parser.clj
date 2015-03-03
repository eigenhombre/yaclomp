(ns yaclomp.parser
  (:require [instaparse.core :as insta]))


(def parse-org (insta/parser (clojure.java.io/resource "grammar.bnf")))
