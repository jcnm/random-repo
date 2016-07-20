package com.lunatech.qnr.controller

import com.lunatech.qnr.common._
import com.lunatech.qnr.config._
import com.lunatech.qnr.model.ProxyFactoryDataSource
import com.lunatech.qnr.model.entities._
import scala.util.{Try,Success,Failure}

object QueryController {

  var countriesProxy =
    new ProxyFactoryDataSource[Country](Instance.dataOrigin, DataKind.Country)
  var airportsProxy =
    new ProxyFactoryDataSource[Airport](Instance.dataOrigin, DataKind.Airport)
  var runwaysProxy =
    new ProxyFactoryDataSource[Runway](Instance.dataOrigin, DataKind.Runway)

  /**
    * Assumption
    *  a given string of lentgth 2 is considered as a country iso code
    *  a given string of length >= 3 is a part or full name of a country
    **/

  def isCode(str: String) = str.length == 2

  def initWith(countries: ProxyFactoryDataSource[Country] , airports:ProxyFactoryDataSource[Airport] , runways:ProxyFactoryDataSource[Runway]) = {
    countriesProxy= countries
    airportsProxy = airports
    runwaysProxy  = runways
  }

  def countryOf(query: String) = {
    require("" != query && " " != query,
      "Please provide a valide country code, country name or prefix name (e.g fra for France)")
    val lcQuery = query.toLowerCase
    val ucQuery = query.toUpperCase
    val msg = s"No data found for your query $query"
    Try((if (isCode(query)) {
      countriesProxy.find(c => c.code == ucQuery)
    } else {
      countriesProxy.find(c => c.name.toLowerCase.startsWith(lcQuery))
    }).getOrElse(throw new DataNotFoundException(msg)))
  }

  def airportsOf(query: String) = {
    countryOf(query) match {
      case Success(country) =>
        airportsProxy.filter(a => a.isoCountry == country.code)
      case Failure(exc) => throw exc
    }
  }

  def runwaysOf(query:String) = {
    val airports = airportsOf(query) // let throw exception early
    var runwayBy = collection.mutable.Map[String, List[Runway]]() // TODO: find a way to use immutable map with ref type
    def airportIndex(runway: Runway) =
      airports.find(airport => runway.airportRef == airport.ident) match
      {
        case Some(air) =>
          Some(air.asInstanceOf[Airport].name)
        case _ =>
          None
      }

    def rwMapGen(map: collection.mutable.Map[String, List[Runway]], runw: Runway):collection.mutable.Map[String, List[Runway]] =
    {
      val key = airportIndex(runw)
      (key match{
        case Some(k) =>
          map get k match {
            case Some(lst) => map += (k.asInstanceOf[String] -> (runw :: lst)) // cons the new runway to the list
            case None => map += (k.asInstanceOf[String] -> (runw :: List[Runway]())) // Is the first object
          }
        case None => map // returns the map as it is if not found
      }).asInstanceOf[collection.mutable.Map[String, List[Runway]]]
    }

    // Hash map computation
    runwaysProxy.foldLeft(runwayBy)(rwMapGen)
  }
}
