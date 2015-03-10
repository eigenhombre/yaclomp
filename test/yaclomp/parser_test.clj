(ns yaclomp.parser-test
  (:require [instaparse.core :as insta]
            [midje.sweet :refer :all]
            [yaclomp.parser :refer [parse-org]]))


(future-fact "A complex document parses unambiguously"
  (->> "#+TITLE: All Your Org Mode Dox are Belong to Us...

A bit at the top.  

* Hey good lookin
 
* Another headline
* and another...

Here's some body copy.

*** with deeper nesting...

#+META: a b c

doe a deer a female deer

* A topic
** Another topic
* A toplevel topic

More whitespace above.

Some more text -- with [[http://x.org][a link]].

"
       (insta/parses parse-org)
       count) => 1)


(fact "Links parse successfully..."
  (fact "Without trailing space..."
    (let [[p & ps :as x]
          (->> "[[http://johnj.com][a link]]\n"
               (insta/parses parse-org))]
      ps => nil ;; Unambiguous
      (get-in p [:content
                 0
                 :content
                 0
                 :content
                 0
                 :tag]) => :link))
  (fact "With trailing space..."
    (let [[p & ps :as x]
          (->> "[[http://johnj.com][a link]] \n"
               (insta/parses parse-org))]
      ps => nil ;; Unambiguous
      (get-in p [:content
                 0
                 :content
                 0
                 :content
                 0
                 :tag]) => :link)))



(fact "A complex document parses unambiguously"
  (->> "#+TITLE: All Your Org Mode Dox are Belong to Us...

A bit at the top.  

* Hey good lookin
 
* Another headline
* and another...
Here's some body copy.

*** with deeper nesting...

#+META: a b c

doe a deer a female deer

* A topic
** Another topic
* A toplevel topic

More whitespace above.

"
       (insta/parses parse-org)
       count) => 1)


(def p (insta/parser "
document = (vanilla | link)*
vanilla = !link #'.+'
link = '[[' link-target ('][' link-body)? ']]'
link-target = #'[^\\[]'+
link-body = #'[^\\[]'+
"))


(defn p-has [[r & rs] desired]
  (facts
    ;; Matches
    r =not=> nil
    ;; Not ambiguous
    rs => nil
    (println r)
    (let [ts
          (tree-seq coll? identity r)]
      (doseq [d desired]
        ts => (contains d)))))


(fact "Just plain stuff"
  (let [checker
        (fn [txt & tags]
          (p-has (insta/parses p txt) tags))]
    (checker "1 2" :document :vanilla)
    (checker "[[http://johnj.com]]" :link :link-target)
    (checker "[[http://johnj.com][a body]]"
             :link
             :link-target
             :link-body)))
