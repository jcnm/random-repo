package com.lunatech.qnr.view

import com.lunatech.qnr.common._
import com.lunatech.qnr.config._
import com.lunatech.qnr.model.entities._


object QueryView {

  def showView(dataSource: collection.mutable.Map[String, List[Runway]]){
    require(null != dataSource, "Please provide a non null dataSource")
    dataSource.foreach
    {
      case (k, lst) =>
        println(s"$k")
        lst.foreach(h => println (s"\t\t|___ $h"))
    }
  }

}
