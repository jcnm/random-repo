package com.lunatech.qnr.config


/**
 * Combining the two following type should
 * allow to automatically build the wanted Data source
 *
 */
// Define existing Data Origin from file, database, uperstream etc.
object DataOrigin extends Enumeration {
  val File = Value
}
// Define Data Sort
object DataKind extends Enumeration {
  val Country, Runway, Airport = Value
}


object Instance {
  val dataOrigin      = DataOrigin.File
  val defaultMaxBach  = 100
}
