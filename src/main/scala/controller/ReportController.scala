package com.lunatech.qnr.controller

import com.lunatech.qnr.common._
import com.lunatech.qnr.config._
import com.lunatech.qnr.model.ProxyFactoryDataSource
import com.lunatech.qnr.model.entities._
import scala.util.{Try,Success,Failure}
import com.lunatech.qnr.config._

object ReportController {

  var countriesProxy =
    new ProxyFactoryDataSource[Country](Instance.dataOrigin, DataKind.Country)
  var airportsProxy =
    new ProxyFactoryDataSource[Airport](Instance.dataOrigin, DataKind.Airport)
  var runwaysProxy =
    new ProxyFactoryDataSource[Runway](Instance.dataOrigin, DataKind.Runway)

  def initWith(countries: ProxyFactoryDataSource[Country] , airports:ProxyFactoryDataSource[Airport] , runways:ProxyFactoryDataSource[Runway]) = {
    countriesProxy= countries
    airportsProxy = airports
    runwaysProxy  = runways
  }

  /*Naive way*/
  def orderedAirportNumberCountry(limit: Int, f: ((Country, Int), (Country, Int)) => Boolean) = {
    val countriesWithAirportCount = countriesProxy.map( c => (c, airportsProxy.filter(a => a.isoCountry == c.code).size))
    countriesWithAirportCount.toList.sortWith(f).take(limit)
  }

  def higherAirportNumberCountry(limit: Int) = {
    orderedAirportNumberCountry(limit, (a: (Country, Int), b: (Country, Int)) => a._2 > b._2).foreach({case (a, b) => println(s"${a.name} has ${b} airports")})
  }


  def lowerAirportNumberCountry(limit: Int) = {
    orderedAirportNumberCountry(limit, (a: (Country, Int), b: (Country, Int)) => a._2 < b._2).foreach({case (a, b) => println(s"${a.name} has ${b} airports")})
  }

  /* TODO use a optimized way using on or implicit object definition ?

   */
}
