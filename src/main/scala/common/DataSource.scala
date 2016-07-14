package com.lunatech.qnr.common

import scala.util.Try

trait DataSource[E] extends Iterable[E] {
  var base = Iterable[E]()
  def length = base.size
  override def iterator = base.iterator
    /** Returns the allready retrieved data (memory cached data)
      *
      *  @return an iterable E data
      */
  def retrievedData(): Iterable[E]

  /** Try to retrieve less or exactly *n* data from the given source
    *  If max == 0 then will try to retrive every data from the given source
    *
    *  @param max the maximum data requiered
    *  @return a Success Iterable E data or Failure
    */
//  def nextData(n: Int): Try[Iterable[E]]
}
