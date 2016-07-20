import org.scalatest.FlatSpec

import com.lunatech.qnr.model.entities._
import com.lunatech.qnr.model.entities.Airport

class AirportSpec extends FlatSpec {

  def fixtures  =
    new {
      val airportOne   =
        new Airport(6524, "Lowel", "00AK", "US", "Villa", AirportKind.Small)
      val airportTwo  =
        new Airport(6538, "Bailey", "00II", "CM", "Something", AirportKind.Small)
    }

  "Airport with id less or equals to zero (0)" should " throw IllegalArgumentException " in {
    intercept[IllegalArgumentException] {
      val airportZero =
        new Airport(0, "fictive id zero","No code","CD","Muni",AirportKind.Closed)
    }

    intercept[IllegalArgumentException]{
      val airportMinusOne =
        new Airport(-1, "fictive minus one","","CT","Minicipality",AirportKind.Closed)
    }
  }

  "An Airport " should " have an ident greater than zero (0)" in {
    assert(fixtures.airportOne.ident > 0)
    assert(fixtures.airportTwo.ident > 0)
  }

}
