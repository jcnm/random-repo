///
///
///
package com.lunatech.qnr

import com.lunatech.qnr.common._
import com.lunatech.qnr.config._
import com.lunatech.qnr.controller._
import com.lunatech.qnr.view.QueryView

object Lunatech extends App {

  override def main(args: Array[String]) = {

    var queries = QueryController.runwaysOf("fran")

    println(s"Please press [Q] for Quering and [P] for Reporting $QueryController")
    QueryView.showView(queries)
    // println(s"Countries Loaded : ${countriesProxy.size}")
    // println(s"Airports Loaded : ${airportProxy.size}")
  }
}
