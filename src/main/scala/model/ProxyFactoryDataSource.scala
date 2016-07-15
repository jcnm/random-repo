package com.lunatech.qnr.model

import com.lunatech.qnr.common.DataSourceException
import com.lunatech.qnr.common.UnconsistentDataSourceException
import com.lunatech.qnr.common.BreakingDataSourceException
import com.lunatech.qnr.common.DataNotFoundException
import com.lunatech.qnr.common.DataSource
import com.lunatech.qnr.config._
import com.lunatech.qnr.model.entities._

import scala.util.Success
import scala.util.Failure
import scala.util.Try

/**
  *  Given a data kind DK and a data origin DO
  *  the proxy wile try to build and object DKDOSource
  *  Example DK = Airport, DO = File result in a proxy on AirportFileSource
  *
  */
class ProxyFactoryDataSource[T <: Identifiable](a_origin: DataOrigin.Value, a_kind: DataKind.Value)
    extends DataSource[T] with Iterable[T] {
  val origin   = a_origin
  val kind     = a_kind
  var source: DataSource[T] = null
  // Set to true when source reach the end of file
  var ended: Boolean = false
  val sourcePath = "resources"
  base = buildData

  override def retrievedData() = {
    source.retrievedData() match {
      case Success(b) => base = b
        Success(b)
      case Failure(exc) => Try(throw exc)
    }
  }

/*  def nextData(n: Int) = {
    if (null == source) {
      (a_origin, a_kind) match {
        case (DataOrigin.File, DataKind.Country) =>
          val countriesSource = new CountryFileSource(sourcePath)
          source = countriesSource.asInstanceOf[DataSource[T]]
          base = source.base.asInstanceOf[Iterable[T]]
        case (DataOrigin.File, DataKind.Airport) =>
        case (DataOrigin.File, DataKind.Runways) =>
        case (_, _) =>
          Try(throw new UnconsistentDataSourceException("Data origin or Data kind not yet supported"))
      }
    }
    source.nextData(n)
  }
 */
  override def buildData =
    {
      if (null == source) {
        (a_origin, a_kind) match {
          case (DataOrigin.File, DataKind.Country) =>
            val countriesSource = new CountryFileSource(sourcePath)
            source = countriesSource.asInstanceOf[DataSource[T]]
            source.base.asInstanceOf[Iterable[T]]
          case (DataOrigin.File, DataKind.Airport) =>
            val airportsSource = new AirportFileSource(sourcePath)
            source = airportsSource.asInstanceOf[DataSource[T]]
            source.base.asInstanceOf[Iterable[T]]
          case (DataOrigin.File, DataKind.Runway) =>
          val runwaysSource = new RunwayFileSource(sourcePath)
          source = runwaysSource.asInstanceOf[DataSource[T]]
          source.base.asInstanceOf[Iterable[T]]
          case (_, _) =>
            throw new UnconsistentDataSourceException("Data origin or Data kind not yet supported")
    }
  } else {
    source.base
  }
}

  override def toString = s"Proxing a source of $kind from $origin"
  }
