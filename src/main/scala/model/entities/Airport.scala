package com.lunatech.qnr.model.entities


/// Scala exclusive feature here, breaks Java compatibility
final object AirportKind extends Enumeration {
  val Closed, Heliport, SeaPlaneBase, Small, Medium, Large, Balloon = Value
}


///
final class Airport(a_id: Int,
  a_name: String,
  a_iso: String,
  a_country: String,
  a_municipality: String,
  a_kind: AirportKind.Value)
    extends BaseEntity(a_id) {
  require(a_id > 0, "The airport ident should be greater than zero (0).")

  val name      = a_name
  val kind      = a_kind
  val isoName   = a_iso
  val isoCountry    = a_country
  val municipality  = a_municipality

  override def toString() = s"Airport {id:$id, name:$name, kind:$kind, iso:$isoName}"
}
