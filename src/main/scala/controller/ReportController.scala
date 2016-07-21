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

  def initWith(countries: ProxyFactoryDataSource[Country] ,
    airports:ProxyFactoryDataSource[Airport] ,
    runways:ProxyFactoryDataSource[Runway]) = {
    countriesProxy= countries
    airportsProxy = airports
    runwaysProxy  = runways
  }
  /**
    Important to acknowledge that
    - We could pre-precess these computation instead of mapping every time we need them
    - We could change the data model using relational data base or no sql model to keep data organized
    - Even simple persistent struct like a structured file acording to our architecture could done the stuff

    **/
  /*Naive way*/
  def orderedAirportNumberCountry(limit: Int, f: ((Country, Int), (Country, Int)) => Boolean) = {
    val countriesWithAirportCount = countriesProxy.map( c => (c, airportsProxy.filter(a => a.isoCountry == c.code).size))
    countriesWithAirportCount.toList.sortWith(f).take(limit)
  }

  def higherAirportNumberCountry(limit: Int) = {
    orderedAirportNumberCountry(limit, (a: (Country, Int), b: (Country, Int)) => a._2 > b._2)
  }

  def lowerAirportNumberCountry(limit: Int) = {
    orderedAirportNumberCountry(limit, (a: (Country, Int), b: (Country, Int)) => a._2 < b._2)
  }

  /**
    *
    */
  def runwayTypePerCountry = {
    def reduceTypeToSet(hsh: collection.mutable.Map[Int, collection.mutable.Set[String]], r: Runway) =
    {
      if (!r.surface.isEmpty) {
        hsh get r.airportRef match {
          case Some(set) => hsh += (r.airportRef -> (set += r.surface))
          // add new
          case None => hsh += (r.airportRef -> collection.mutable.Set[String](r.surface))
        }
      } else hsh
    }
    val runwTypeMap = runwaysProxy.foldLeft(collection.mutable.Map[Int, collection.mutable.Set[String]]())(reduceTypeToSet)

    airportsProxy.foldLeft(collection.mutable.Map[String, collection.mutable.Set[String]]())((hsh, a) =>
      hsh get a.isoCountry match {
        case Some(set) => hsh += (a.isoCountry -> (set ++= runwTypeMap.get(a.ident).getOrElse(collection.mutable.Set[String]())))
          // add new
        case None => hsh += (a.isoCountry -> runwTypeMap.get(a.ident).getOrElse(collection.mutable.Set[String]()))
      })

    //val countriesWithRunwayType = countriesProxy.map(c => (c, airportsProxy.filter(a => a.isoCountry == c.code).size))
    //orderedAirportNumberCountry(limit, (a: (Country, Int), b: (Country, Int)) => a._2 < b._2).foreach({case (a, b) => println(s"${a.name} has ${b} airports")})
  }

  /* TODO use a optimized way using on or implicit object definition ?

   */
}
