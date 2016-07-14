package com.lunatech.qnr.model.entities
import com.lunatech.qnr.model.Identifiable

protected class BaseEntity(givenId: Int) extends Identifiable {
  protected val id: Int = givenId
  def getIdent = id
}
