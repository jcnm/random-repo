package com.lunatech.qnr.model

/// Handle optional computation
import scala.util.{Success, Failure, Try}

import com.lunatech.qnr.model.entities.Country
import com.lunatech.qnr.common.{DataSource, BreakingDataSourceException, DataNotFoundException, UnconsistentDataSourceException}
import com.github.tototoshi.csv.CSVReader

final class CountryFileSource(a_path: String)
    extends DataSource[Country] {

  private var listMap:List[Map[String, String]] = null
  private val path = a_path
  base = buildData


  private def predKeyName =
    {k:String =>  k == "id" || k == "name" || k == "code" }

  protected def buildData: Iterable[Country] = {
    require(listMap == null, "Call this method only to retrieve data")
    val reader = Try(CSVReader.open(s"$path/countries.csv"))
    reader match {
      case Failure(exc) => throw exc
      case Success(r)   =>
        val rawData = r.asInstanceOf[CSVReader].allWithHeaders
        val emptyList = List[Map[String, String]]()
        listMap = rawData.foldLeft(emptyList)((l, m) => m.filterKeys(predKeyName) :: l)
        listMap.map(m =>
          (m.get("id"), m.get("name"), m.get("code")) match {
            case (Some(id), Some(name), Some(code)) =>
            countryWith(id.toInt, name, code)
            case _ => throw new BreakingDataSourceException("Unable to build Country")
          }).dropRight(1) // remove the "Unknown or unassigned country" element
    }
  }

  private def countryWith(a_id: Int, a_name: String, a_code: String ) =
    new Country(a_id, a_name, a_code.toUpperCase)

}
//
