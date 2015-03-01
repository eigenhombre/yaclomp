(ns yaclomp.core-test
  (:require [midje.sweet :refer :all]
            [yaclomp.core
             :refer [parse-org]
             :rename {parse-org po}]))


(facts "About parsing Org Mode"
  (po "") => [{:t :pl
               :b ""}]
  (po "Just some text") => [{:t :pl
                             :b "Just some text"}]
  (future-fact (po "* Headline") => [{:t :hd
                                      :b "Headline"}]))
