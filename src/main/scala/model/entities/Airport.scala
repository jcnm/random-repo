package com.lunatech.qnr.model.entities

import com.lunatech.qnr.mode.BaseEntity

final class Airport(givenId: Int, givenName: String, givenISO: String)
    extends BaseEntity(givenId) {
  val name = givenName
  val isoName = givenISO
  override def toString() = "Airport {id:$id, name:$name, iso:$isoName}"

  }
