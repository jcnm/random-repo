package com.lunatech.qnr.model.entities

import com.lunatech.qnr.model.BaseEntity

final class Runway(givenId: Int, givenName: String, givenISO: String)
    extends BaseEntity(givenId) {
  val name = givenName
  val isoName = givenISO
  override def toString() = "Runway {id:$id, name:$name, iso:$isoName}"
  }
