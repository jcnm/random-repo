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

  override def main(args: Array[String])
  {
    var query = "X"
    var quit  = false
    do {
      print("Please press \n\t[Q] \tfor Quering\n\t[P] \tfor Reporting \n\t[X] \tto exit\nWhat to do: ")

      Console.readLine() match {
        case "Q" =>
          print("Please, enter your country query: ")
          query = Console.readLine()
          QueryView.showView(QueryController.runwaysOf(query))
        case "P" =>
          println("Reporting.")
          println("Top ten countries by airport number descendant:")
          ReportController.higherAirportNumberCountry(10)
          println("\nTop ten countries by airport number ascendant:")
          ReportController.lowerAirportNumberCountry(10)
          ReportController.runwayTypePerCountry.foreach{case (k, v) =>
            println(s"Country $k has runway type: $v")
          }
        case "X" => quit = true
        case _ => quit = false
      }
      println("\n")
    } while(!quit);
  }
}
