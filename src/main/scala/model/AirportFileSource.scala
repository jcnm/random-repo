package com.lunatech.qnr.model

import com.lunatech.qnr.common.DataSource
import com.lunatech.qnr.model.entities.Airport
import com.lunatech.qnr.model.entities.AirportKind

/// Handle optional computation
import scala.util.Success
import scala.util.Failure
import scala.util.Try


import com.lunatech.qnr.common.BreakingDataSourceException
import com.lunatech.qnr.common.DataNotFoundException
import com.github.tototoshi.csv.CSVReader

final class AirportFileSource(a_path: String)
    extends DataSource[Airport] {

  private var listMap:List[Map[String, String]] = null
  private val path = a_path
  base = buildData


  private def predKeyName =
    {k:String =>
    k == "id" || k == "name" || k == "ident" || k == "type" || k == "iso_country" || k == "municipality" }

  protected def buildData = {
    require(listMap == null, "Call this method only to retrieve data")
    val reader = Try(CSVReader.open(s"$path/airports.csv"))
    reader match {
      case Failure(exc) => throw exc
      case Success(r)   =>
        val rawData = r.asInstanceOf[CSVReader].allWithHeaders
        val emptyList = List[Map[String, String]]()
        listMap = rawData.foldLeft(emptyList)((l, m) => m.filterKeys(predKeyName) :: l)
        listMap.map(
          m =>
          (m.get("id"),
          m.get("name"),
          m.get("ident"),
          m.get("type"),
          m.get("iso_country"),
          m.get("municipality")) match {
            case (Some(id),
            Some(name),
            Some(code),
            Some(typ),
            Some(country),
            Some(muni)) =>
            airportWith(id.toInt, name, code, country, muni, typ)
            case _ => throw new BreakingDataSourceException("Unable to build an Airport")
          })
    }
  }

  private def airportWith(a_id: Int,
    a_name: String,
    a_ident: String, a_country: String, a_municipality: String,
    a_sort: String) =
    new Airport(a_id, a_name, a_ident, a_country.toUpperCase, a_municipality, airportKind(a_sort))

  private def airportKind(str_kind:String): AirportKind.Value =
    str_kind match {
      case "balloonport" => AirportKind.Balloon
      case "heliport" => AirportKind.Heliport
      case "small_airport" => AirportKind.Small
      case "medium_airport" => AirportKind.Medium
      case "large_airport" => AirportKind.Large
      case "seaplane_base" => AirportKind.SeaPlaneBase
      case _ => AirportKind.Closed
    }
}
//
