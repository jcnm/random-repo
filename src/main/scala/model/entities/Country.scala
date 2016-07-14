package com.lunatech.qnr.model.entities


///
///
final class Country(a_id: Int, a_name: String, a_code: String)
    extends BaseEntity(a_id) {
  require(a_id > 0, "The country ident should be greater than zero (0).")

  val name: String = a_name
  val code: String = a_code

  override def toString() = s"Country {id:$id, name:$name, code:$code}"
}
