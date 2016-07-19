import org.scalatest.FlatSpec

import com.lunatech.qnr.model.entities.{Airport, Runway, Country}
import com.lunatech.qnr.controller.QueryController
import scala.util.{Try,Success,Failure}
import com.lunatech.qnr.common._

class QueryControllerSpec extends FlatSpec {

  def fixtures = new {
    val emptyQuery = ""
    val blankQuery = " "
    val falseQuery = "NOXID"
    val faultQuery = "Fram" // Instead of Fran
    val validCodeQuery = "FR"
    val validPrefixQuery = "Fra"
    val validCountryQuery = "France"
  }

  // countryOf tests
  "An empty query for a country " should "throws IllegalArgumentException " in {
      intercept[java.lang.IllegalArgumentException] {
        QueryController.countryOf(fixtures.emptyQuery)
    }
  }

  "A blank query for a country " should "throws IllegalArgumentException " in {
    intercept[java.lang.IllegalArgumentException] {
      QueryController.countryOf(fixtures.blankQuery)
    }
  }

  "An inexistant query for a country " should " returns None " in {
    assert(QueryController.countryOf(fixtures.falseQuery).isFailure)
    assert(QueryController.countryOf(fixtures.faultQuery).isFailure)
  }

  "A valid country code query " should " returns Some country " in {
    assert(QueryController.countryOf(fixtures.validCodeQuery).isSuccess)
  }
  it should "returns Some country with the same given code " in {
    assert(
      QueryController.countryOf(fixtures.validCodeQuery) match {
        case Success(c) => c.code == fixtures.validCodeQuery
        case Failure(exc) => false
      }
    )
  }

  "A valid country prefix query " should " returns Some country " in {
    assert(QueryController.countryOf(fixtures.validPrefixQuery).isSuccess)
  }
  it should " returns Some country with the same given name " in {
    assert(
      QueryController.countryOf(fixtures.validPrefixQuery)  match {
        case Success(c) => c.name.startsWith(fixtures.validPrefixQuery)
        case Failure(exc) => false
      })
  }

  "A valid country name query " should " returns Some country " in {
    assert(QueryController.countryOf(fixtures.validCountryQuery).isSuccess)
  }

  // airportsOf tests
  "Requesting airports with an empty country query " should "throws IllegalArgumentException " in {
    intercept[java.lang.IllegalArgumentException] {
      QueryController.airportsOf(fixtures.emptyQuery)
    }
  }

  "Requesting airports with a blank query for a country " should "throws IllegalArgumentException " in {
    intercept[java.lang.IllegalArgumentException] {
      QueryController.airportsOf(fixtures.blankQuery)
    }
  }

  "Requesting airports with an inexistant country query " should "throws DataNotFoundException " in {
    intercept[DataNotFoundException] {
      QueryController.airportsOf(fixtures.falseQuery)
    }

    intercept[DataNotFoundException] {
      QueryController.airportsOf(fixtures.faultQuery)
    }
  }

  "Requesting airports with a valid country code query " should "returns a non empty airport list " in {
    assert(QueryController.airportsOf(fixtures.validCodeQuery).size > 0)
  }

  "Requesting airports with a valid country prefix query " should " returns a non empty airports list " in {
    assert(QueryController.airportsOf(fixtures.validPrefixQuery).size > 0)
  }

  "Requesting airports with a valid country name query " should " returns a non empty airports list " in {
    assert(QueryController.airportsOf(fixtures.validCountryQuery).size > 0)
  }


  /*
  "An Airport " should " have an ident greater than zero (0)" in {
    assert(fixtures.airportOne.ident > 0)
    assert(fixtures.airportTwo.ident > 0)
  }
 */

}
