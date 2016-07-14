///
///
///
package com.lunatech.qnr

import com.lunatech.qnr.common._
import com.lunatech.qnr.config._
import com.lunatech.qnr.model.ProxyFactoryDataSource
import com.lunatech.qnr.model.entities._

object Lunatech extends App {

  override def main(args: Array[String]) = {

    val countriesProxy = new ProxyFactoryDataSource[Country](DataOrigin.File, DataKind.Country)
    val countries = countriesProxy.retrievedData()
    println(s"Please press [Q] for Quering and [P] for Reporting $countriesProxy")
    println(s"Countries Loaded : ${countries.size}")
  }
}
