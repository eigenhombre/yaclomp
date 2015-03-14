(ns yaclomp.parser-spec
  (:require [instaparse.core :as insta]
            [speclj.core :refer :all]))


(def my-little-parser (insta/parser "
document    = (hdr / link / content)*
content     = !link !hdr #'(?s).+'
link        = '[[' link-target ('][' link-body)? ']]'
link-target = #'[^\\[]'+
link-body   = #'[^\\[]'+
hdr         = #'^\\*+\\s+.+'
"))


(defn check [txt & desired-tags]
  (let [[r & rs] (insta/parses my-little-parser txt)
        ts (tree-seq coll? identity r)]
    (should-not-be-nil r)
    (should-be-nil rs)
    (doseq [d desired-tags]
      (if (map? d)
        (should= d
                 (-> ts
                     frequencies
                     (select-keys (keys d))))
        (should-contain d ts)))))


(describe "my-little-parser"
  (it "parses a line of plain text OK"
    (check "1 2" :document :content))
  (it "parses text with newline OK"
    (check "1 2\n3 4" :document :content))
  (it "parses a one-element link OK"
    (check "[[http://johnj.com]]" :link :link-target))
  (it "parses a link with body OK"
    (check "[[http://johnj.com][a body]]"
           :link
           :link-target
           :link-body))
  (it "parses a top-level header"
    (check "* header body" :hdr)
    (check "* header body\nWith more stuff"
           :hdr :content))
  (xit "parses a header with content in front"
    (check "a\n* b" :hdr))
  (it "handles multiple stars"
    (check "** a subsection"
           :hdr)
    (check "** subsection followed by\nsome body text"
           :hdr :content))
  (xit "handles sequential headers"
    (check "* a\n* b"
           {:hdr 2})))
