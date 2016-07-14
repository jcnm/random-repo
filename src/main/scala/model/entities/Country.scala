package com.lunatech.qnr.model.entities

///
///
final class Country(a_id: Int, a_name: String, a_code: String)
    extends BaseEntity(a_id) {

  protected val name: String = a_name
  protected val code: String = a_code

  override def toString() = s"Country {id:$id, name:$name, code:$code}"
}
