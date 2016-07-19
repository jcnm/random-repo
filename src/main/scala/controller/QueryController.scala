package com.lunatech.qnr.controller

import com.lunatech.qnr.common._
import com.lunatech.qnr.config._
import com.lunatech.qnr.model.ProxyFactoryDataSource
import com.lunatech.qnr.model.entities._


object QueryController {


  /**
    * Assumption
    *  a given string of lentgth 2 is a code
    *  a given string of length >= 3 is a part or full name of a country
    **/
  def isCode(str: String) = str.length == 2

  val countriesProxy =
    new ProxyFactoryDataSource[Country](Instance.dataOrigin, DataKind.Country)
  val airportsProxy =
    new ProxyFactoryDataSource[Airport](Instance.dataOrigin, DataKind.Airport)
  val runwaysProxy =
    new ProxyFactoryDataSource[Runway](Instance.dataOrigin, DataKind.Runway)

  var currentQuery: String = ""

  def countryOf(query: String) = {
    val lcQuery = query.toLowerCase
    val ucQuery = query.toUpperCase
    if (isCode(query)) {
      countriesProxy.find(c => c.code == ucQuery)
    } else {
      countriesProxy.find(c => c.name.toLowerCase.startsWith(lcQuery))
    }
  }

  def airportsOf(query: String) = {
    countryOf(query) match {
      case Some(country) =>
        airportsProxy.filter(a => a.isoCountry == country.code)
      case _ =>
        val msg = s"No data found for your query $query"
        throw new DataNotFoundException(msg)
    }
  }

  def runwaysOf(query:String) = {
    var runwayBy = collection.mutable.Map[String, List[Runway]]() // TODO: find a way to use immutable map with ref type
    val airports = airportsOf(query)

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

    // To complet
    val rwfold = runwaysProxy.foldLeft(runwayBy)(rwMapGen)
    //println(s"Group $rgroup")
    rwfold.foreach(
      {
        case (k, lst) =>
          println(s"$k")
          lst.foreach(h => println (s"|___ $h"))
      }
    )
  }

}
