package com.lunatech.qnr.model.entities

///
///
final class Runway(a_id: Int, a_name: String, a_ISO: String)
    extends BaseEntity(a_id) {

  val name = a_name
  val isoName = a_ISO

  override def toString() = s"Runway {id:$id, name:$name, iso:$isoName}"
}
