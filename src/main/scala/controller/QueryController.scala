package com.lunatech.qnr.controller

import com.lunatech.qnr.common._
import com.lunatech.qnr.config._
import com.lunatech.qnr.model.ProxyFactoryDataSource
import com.lunatech.qnr.model.entities._

final class QueryController {


/**
* Assumption
*  a given string of lentgth 2 is a code
*  a given string of length >= 3 is a part or full name of a country
**/
  def isCode(str: String) = str.length == 2

  val countriesProxy = new ProxyFactoryDataSource[Country](Instance.dataOrigin, DataKind.Country)
  val airportsProxy = new ProxyFactoryDataSource[Airport](Instance.dataOrigin, DataKind.Airport)
  val runwaysProxy = new ProxyFactoryDataSource[Runway](Instance.dataOrigin, DataKind.Runway)

  var currentQuery: String = ""

  def countryOf(q: String) = {
    if (isCode(q)) {
      countriesProxy.find(c => c.code == q.toUpperCase)
    } else {
      countriesProxy.find(c => c.name.toLowerCase.startsWith(q.toLowerCase))
    }
  }

  def airportsOf(q: String) = {
    countryOf(q) match {
      case Some(country) => airportsProxy.filter(a => a.isoCountry == country.code)
      case _ =>
      throw new DataNotFoundException(s"No data found for your query $q")
    }
  }

  def runwaysOf(q:String) = {
    val airports = airportsOf(q)
    val rgroup = runwaysProxy.groupBy(r =>
      airports.find(
        ai => r.airportRef == ai.ident) match
        {
          case Some(air) => println(air)
          air.asInstanceOf[Airport].name
          case _ => " "
        }
      )
      //println(s"Group $rgroup")
      rgroup.foreach({case (k, lst) => k match {
      case " " =>
      case str => println(str)
    }})

  }

}
