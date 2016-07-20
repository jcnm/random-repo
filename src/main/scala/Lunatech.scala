///
///
///
package com.lunatech.qnr

import com.lunatech.qnr.common._
import com.lunatech.qnr.config._
import com.lunatech.qnr.controller._
import com.lunatech.qnr.model.ProxyFactoryDataSource
import com.lunatech.qnr.view.QueryView
import com.lunatech.qnr.model.entities.{Country, Airport, Runway}

object Lunatech extends App {

  override def main(args: Array[String]) = {

    val queries = QueryController.runwaysOf("fran")
    println(s"Please press [Q] for Quering and [P] for Reporting $QueryController")
    QueryView.showView(queries)
    // println(s"Countries Loaded : ${countriesProxy.size}")
    // println(s"Airports Loaded : ${airportProxy.size}")
  }
}
