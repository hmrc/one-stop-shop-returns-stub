package uk.gov.hmrc.onestopshopreturnsstub.utils

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.json.Json
import play.api.mvc.Results.{Accepted, BadRequest}
import play.api.test.Helpers._
import uk.gov.hmrc.onestopshopreturnsstub.models.core.{CoreCorrection, CoreErrorResponse, CoreMsconSupply, CoreMsestSupply, CorePeriod, CoreSupply, CoreTraderId, CoreVatReturn}
import uk.gov.hmrc.onestopshopreturnsstub.models.Period
import uk.gov.hmrc.onestopshopreturnsstub.models.Quarter.Q3

import java.time.{Clock, Instant, LocalDate, ZoneId}
import java.util.UUID
import scala.concurrent.Future

class JsonSchemaHelperSpec extends AnyFreeSpec with ScalaFutures with Matchers {

  private val stubClock: Clock = Clock.fixed(LocalDate.now.atStartOfDay(ZoneId.systemDefault).toInstant, ZoneId.systemDefault)

  private val jsonSchemaHelper = new JsonSchemaHelper(stubClock)

  "JsonSchemaHelper#applySchemaValidation" - {
    val validSchemaPath = "/resources/schemas/core_return.json"

    "return with a successful status" in {

      val now = Instant.now()
      val period = Period(2021, Q3)
      val coreVatReturn = CoreVatReturn(
        "XI/XI123456789/Q4.2021",
        now.toString,
        CoreTraderId(
          "123456789AAA",
          "XI"
        ),
        CorePeriod(
          2021,
          3
        ),
        period.firstDay,
        period.lastDay,
        now,
        BigDecimal(5000),
        List(CoreMsconSupply(
          "DE",
          BigDecimal(5000),
          BigDecimal(1000),
          BigDecimal(1000),
          BigDecimal(1000),
          List(CoreSupply(
            "GOODS",
            BigDecimal(10),
            "STANDARD",
            BigDecimal(10),
            BigDecimal(10)
          )),
          List(CoreMsestSupply(
            Some("DE"),
            None,
            List(CoreSupply(
              "GOODS",
              BigDecimal(10),
              "STANDARD",
              BigDecimal(10),
              BigDecimal(100)
            ))
          )),
          List(CoreCorrection(
            CorePeriod(2021, 2),
            BigDecimal(100)
          ))
        ))
      )

      val validJson = Json.toJson(coreVatReturn)

      val result = jsonSchemaHelper.applySchemaValidation(validSchemaPath, Some(validJson)) {
        Future.successful(Accepted(""))
      }

      status(result) shouldBe ACCEPTED
    }

    "return with a failure status" - {
      "when json is invalid" in {
        val invalidJsonString = "{}"

        val invalidJson = Json.toJson(invalidJsonString)

        val result = jsonSchemaHelper.applySchemaValidation(validSchemaPath, Some(invalidJson)) {
          Future.successful(Accepted(""))
        }

        status(result) shouldBe BAD_REQUEST
        contentAsJson(result) shouldBe Json.toJson(CoreErrorResponse(Instant.now(stubClock), None, "OSS_400", "Bad Request"))
      }

      "when no json is passed" in {
        val result = jsonSchemaHelper.applySchemaValidation(validSchemaPath, None) {
          Future.successful(Accepted(""))
        }

        status(result) shouldBe BAD_REQUEST
        contentAsJson(result) shouldBe Json.toJson(CoreErrorResponse(Instant.now(stubClock), None, "OSS_400", "Missing Payload"))
      }

      "when schema doesn't exist" in {
        val result = jsonSchemaHelper.applySchemaValidation("invalidPath", None) {
          Future.successful(Accepted(""))
        }

        status(result) shouldBe INTERNAL_SERVER_ERROR
      }
    }

  }

}
