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


(future-fact "Links parse successfully..."
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
