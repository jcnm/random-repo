package com.lunatech.qnr.common

/* Our universal base exception type*/
abstract class DataSourceException(msg: String) extends Exception(msg) {
}

/* Our existing exceptions type */
/// Throwing when data is missing when try to retrieve new ones
case class DataNotFoundException(msg:String)  extends DataSourceException(msg)
/// Throwing when the given source of data does not respond or freezes
case class UnconsistentDataSourceException(msg:String)  extends Exception(msg)
/// Throwing when something wrong happend during data loading
case class BreakingDataSourceException(msg:String)  extends Exception(msg)
