package com.lunatech.qnr.view

import com.lunatech.qnr.common._
import com.lunatech.qnr.config._
import com.lunatech.qnr.model.entities._
import collection.mutable.{Set, Map}

object ReportView {

  def showOrderedCountries(orderedCountries: List[(Country, Int)]) = {
    orderedCountries.foreach({case (c, airct) => println(s"${c.name} has ${airct} airports")})
  }

  def showRunwayTypeByCountry(groupedCountries: Map[String, Set[String]]) = {
    groupedCountries.foreach({case (c, set) => println(s"${c} has ${set.size} runway types:")
      set.foreach({e => println(s"\t\t - $e")})
    })
  }

  // Def 10 most common le_ident value

  //
}
