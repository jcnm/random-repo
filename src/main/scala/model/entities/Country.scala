package com.lunatech.qnr.model.entities

import com.lunatech.qnr.model.BaseEntity

final class Country(givenId:Int, givenName: String, givenCode: String)
    extends BaseEntity(givenId) {
  protected val name: String = givenName
  protected val code: String = givenCode
  protected var isoName = ""
  override def toString() = "Country {id:$id, name:$name, code:$code}"
  }
