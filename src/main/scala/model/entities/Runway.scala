package com.lunatech.qnr.model.entities

///
///
final class Runway(a_id: Int, a_aref: Int, a_aid: String,
    a_surface: String, a_leident: String)
    extends BaseEntity(a_id) {

  require(a_id > 0, "The runway ident should be greater than zero (0).")

  val airportRef  = a_aref
  val airportId   = a_aid
  val surface     = a_surface
  val leident     = a_leident

  override def toString() = s"Runway {id:$id, aiport(id:$airportRef, iso:$airportId), surface $surface, le ident: $leident}"

}
