;; The contents of this file are subject to the LGPL License, Version 3.0.
;;
;; Copyright (C) 2013, Phillip Lord, Newcastle University
;;
;; This program is free software: you can redistribute it and/or modify it
;; under the terms of the GNU Lesser General Public License as published by
;; the Free Software Foundation, either version 3 of the License, or (at your
;; option) any later version.
;;
;; This program is distributed in the hope that it will be useful, but WITHOUT
;; ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
;; FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
;; for more details.
;;
;; You should have received a copy of the GNU Lesser General Public License
;; along with this program. If not, see http://www.gnu.org/licenses/.

(ns tawny.obo.pizza.pizza
  (:use [tawny.owl])
  (:require [tawny.obo]
            [tawny.read]))

(println "Starting ontology load")
(def start (System/currentTimeMillis))

(defontology pizzaontology
  :iri "http://www.ncl.ac.uk/pizza-obo"
  :prefix "piz:"
  :comment "An example pizza using OBO style ids"
  :versioninfo "Unreleased Version"
  :annotation (seealso "Manchester Version")
  :iri-gen tawny.obo/obo-iri-generate
  )

(tawny.obo/obo-restore-iri "./src/tawny/obo/pizza/pizza_iri.props")

(defclass CheeseTopping
  :label "Cheese Topping")
(defclass MeatTopping
  :label "Meat Topping")
(defclass FishTopping
  :label "Fish Topping")
(defclass FruitTopping
  :label "Fruit Topping")
(defclass NutTopping
  :label "Nut Topping")
(defoproperty hasTopping)


(defclass D)

(print "Adding all the classes:")
(time
 ;; some of these have permanent IDs and some do not.
 (doseq [n (map #(str "n" %) (range 1 20))]
   (owlclass n)
   ))

(tawny.obo/obo-store-iri "./src/tawny/obo/pizza/pizza_iri.props")
(tawny.obo/obo-report-obsolete)

(save-ontology "pizza-obo.omn" :omn)
(save-ontology "pizza-obo.owl" :owl)


(printf "Complete in %s millis\n"
        (- (System/currentTimeMillis) start))

(println "Listing all entities in signature")
(doseq [e (.getSignature pizzaontology)]
  (println "\t" e))

;; this could normally be used at release time; probably a lein plugin wold
;; make sense.
;; (tawny.obo/obo-generate-permanent-iri "./src/tawny/obo/pizza/pizza_iri.props" "http://www.ncl.ac.uk/pizza-obo/PIZZA_")
