///
///
///
package com.lunatech.qnr

import com.lunatech.qnr.common._
import com.lunatech.qnr.config._
import com.lunatech.qnr.controller._

object Lunatech extends App {

  override def main(args: Array[String]) = {

    QueryController.currentQuery = "FR"
    QueryController.runwaysOf("FR")
    println(s"Please press [Q] for Quering and [P] for Reporting $QueryController")
    // println(s"Countries Loaded : ${countriesProxy.size}")
    // println(s"Airports Loaded : ${airportProxy.size}")
  }
}
