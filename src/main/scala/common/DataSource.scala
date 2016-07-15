package com.lunatech.qnr.common


import com.lunatech.qnr.common.BreakingDataSourceException
import com.lunatech.qnr.common.DataNotFoundException
import com.lunatech.qnr.common.UnconsistentDataSourceException

import scala.util.Success
import scala.util.Failure
import scala.util.Try

trait DataSource[E] extends Iterable[E] {
  var base: Iterable[E] = null
  override def iterator = base.iterator
    /** Returns the allready retrieved data (memory cached data)
      *
      *  @return an iterable E data
      */
  def retrievedData(): Try[Iterable[E]] = {
    if(null == base) {
      base = buildData
    }
    if (null == base) {
      Try(throw new DataNotFoundException("Unable to retrieve data"))
    } else {
      Try(base)
    }
  }

  protected def buildData: Iterable[E]
}
