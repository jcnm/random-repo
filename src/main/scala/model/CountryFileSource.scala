package com.lunatech.qnr.model

import com.lunatech.qnr.common.DataSource
import com.lunatech.qnr.model.entities.Country

/// Handle optional computation
import scala.util.Try
  import scala.util.Success
import scala.util.Failure


import com.lunatech.qnr.common.BreakingDataSourceException
import com.lunatech.qnr.common.DataNotFoundException
import com.github.tototoshi.csv._

class CountryFileSource(a_path: String)
    extends DataSource[Country] {
  var index = 0
  var listMap:List[Map[String, String]] = null
  val path = a_path
  base = List[Country]()

  def retrievedData() = {
    if(listMap == null) {
      this.buildCountries
    }
    if (null == base) {
      (throw new DataNotFoundException("Unable to retrieve data"))
    }
    base
  }

  //  def nextData(n: Int) = {
  //    if(listMap == null) {
  //      this.buildCountries
  //    }
  //    if(null == base) {
  //      Try(throw new DataNotFoundException("Unable to retrieve data"))
  //    }
  //    val taken = base.slice(index, n)
  //     Try(taken)
  //  }

  private def buildCountries = {
    require(listMap == null, "Call this method only to retrieve data")
    val reader = Try(CSVReader.open(s"$path/countries.csv"))
    reader match {
      case Failure(msg) => println(msg)
      case Success(r)   => println(s"Success $r")
        val rawData = r.asInstanceOf[CSVReader].allWithHeaders
        val emptyList = List[Map[String, String]]()
        listMap = rawData.foldLeft(emptyList)((l, m) => m.filterKeys( p => p == "id" || p == "name" || p == "code") :: l)
        base = listMap.map(m =>
          (m.get("id"), m.get("name"), m.get("code")) match {
            case (Some(id), Some(name), Some(code)) =>
            countryWith(id.toInt, name, code)
            case _ => throw new BreakingDataSourceException("Unable to build Country")
          })
    }
  }

  private def countryWith(a_id: Int, a_name: String, a_code: String ) =
    new Country(a_id, a_name, a_code)

}
//
