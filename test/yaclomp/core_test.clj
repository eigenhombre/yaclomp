(ns yaclomp.core-test
  (:require [midje.sweet :refer :all]
            [yaclomp.core
             :refer [parse-org]
             :rename {parse-org po}]))


(facts "About parsing Org Mode"
  (po "") => [:document [:section]]

  (po "Just some text") =>
  [:document [:section "Just some text"]]

  (po "* Headline")
  => [:document
      [:section]
      [:topsection [:headline [:stars "*"] " Headline"] [:section]]])
