package com.lunatech.qnr.model

import com.lunatech.qnr.common.DataSource
import com.lunatech.qnr.model.entities.Runway

/// Handle optional computation
import scala.util.Success
import scala.util.Failure
import scala.util.Try


import com.lunatech.qnr.common.BreakingDataSourceException
import com.lunatech.qnr.common.DataNotFoundException
import com.github.tototoshi.csv.CSVReader

final class RunwayFileSource(a_path: String)
    extends DataSource[Runway] {

  private var listMap:List[Map[String, String]] = null
  private val path = a_path
  base = buildData

/**
* Predicate used to select only interested key from raw data
*/
  private def predKeyName =
    {k:String =>
    k == "id" || k == "airport_ident" || k == "airport_ref" || k == "surface" || k == "le_ident" }

// aref = airport reference aid = airport identification
  protected def buildData = {
    require(listMap == null, "Call this method only to retrieve data")
    val reader = Try(CSVReader.open(s"$path/Runways.csv"))
    reader match {
      case Failure(exc) => throw exc
      case Success(r)   =>
        val rawData = r.asInstanceOf[CSVReader].allWithHeaders
        val emptyList = List[Map[String, String]]()
        listMap = rawData.foldLeft(emptyList)((l, m) => m.filterKeys(predKeyName) :: l)
        listMap.map(
          m =>
          (m.get("id"),
          m.get("airport_ident"),
          m.get("airport_ref"),
          m.get("surface"),
          m.get("le_ident")) match {
            case (Some(id),
            Some(aident),
            Some(aref),
            Some(surface),
            Some(leident)) =>
            runwayWith(id.toInt, aref.toInt, aident, surface, leident)
            case _ => throw new BreakingDataSourceException("Unable to build an Runway")
          })
    }
  }

  private def runwayWith(a_id: Int, a_aref: Int,
    a_aid: String, a_surface: String, a_leid: String) =
    new Runway(a_id, a_aref, a_aid, a_surface, a_leid)

}
//
