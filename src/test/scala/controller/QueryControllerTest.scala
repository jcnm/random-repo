import org.scalatest.{FlatSpec, BeforeAndAfter}

import com.lunatech.qnr.model.entities.{Airport, Runway, Country}
import com.lunatech.qnr.controller.QueryController
import scala.util.{Try,Success,Failure}
import com.lunatech.qnr.common._
import com.lunatech.qnr.model.ProxyFactoryDataSource
import com.lunatech.qnr.config._

class QueryControllerSpec extends FlatSpec with BeforeAndAfter {

  def fixtures = new {
    val emptyQuery = ""
    val blankQuery = " "
    val falseQuery = "NOXID"
    val faultQuery = "Fram" // Instead of Fran
    val validCodeQuery = "FR"
    val validPrefixQuery = "Fra"
    val validCountryQuery = "France"
  }

/*
  before {
    QueryController.initWith(fixtures.countriesProxy,
      fixtures.airportsProxy,
      fixtures.runwaysProxy)
  }
 */

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
  it should " returns Some country with the same given prefix name " in {
    assert(
      QueryController.countryOf(fixtures.validPrefixQuery)  match {
        case Success(c) => c.name.startsWith(fixtures.validPrefixQuery)
        case Failure(exc) => false
      })
  }

  "A valid country name query " should " returns Some country " in {
    assert(QueryController.countryOf(fixtures.validCountryQuery).isSuccess)
  }
  it should " returns Some country with the same given name " in {
    assert(
      QueryController.countryOf(fixtures.validPrefixQuery)  match {
        case Success(c) => c.name == fixtures.validCountryQuery
        case Failure(exc) => false
      })
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
  it should " returns a non empty airport list and all element has an isoCountry equal to the country code query " in {
    assert(QueryController.airportsOf(fixtures.validCodeQuery).forall(airport => airport.isoCountry.toLowerCase == fixtures.validCodeQuery.toLowerCase))
  }

  "Requesting airports with a valid country prefix query " should " returns a non empty airports list " in {
    assert(QueryController.airportsOf(fixtures.validPrefixQuery).size > 0)
  }

  "Requesting airports with a valid country name query " should " returns a non empty airports list " in {
    assert(QueryController.airportsOf(fixtures.validCountryQuery).size > 0)
  }

}
