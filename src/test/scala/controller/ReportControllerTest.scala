import org.scalatest.FlatSpec

import com.lunatech.qnr.controller.ReportController

class ReportControllerSpec extends FlatSpec{

  // There are still country without airport => without runways connected to airport => N/A
  "Runway per country report map " should " have less or exactly the same length" in {
    assert(ReportController.runwayTypePerCountry.size <= ReportController.countriesProxy.size)
  }

}
