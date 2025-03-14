/*
 * Copyright 2024 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.onestopshopreturnsstub.controllers

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import play.api.http.{MimeTypes, Status}
import play.api.libs.json.Json
import play.api.mvc.Headers
import play.api.test.Helpers._
import play.api.test.{FakeRequest, Helpers}
import uk.gov.hmrc.onestopshopreturnsstub.models.Period
import uk.gov.hmrc.onestopshopreturnsstub.models.Quarter._
import uk.gov.hmrc.onestopshopreturnsstub.models.core._
import uk.gov.hmrc.onestopshopreturnsstub.utils.JsonSchemaHelper

import java.time.format.DateTimeFormatter
import java.time._
import java.time.temporal.ChronoUnit
import java.util.{Locale, UUID}

class CoreControllerSpec extends AnyFreeSpec with Matchers {

  private val dateTimeFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z")
    .withLocale(Locale.UK)
    .withZone(ZoneId.of("GMT"))
  private val fakeRequest = FakeRequest(POST, routes.CoreController.submitVatReturn().url)
  private val stubClock: Clock = Clock.fixed(LocalDate.now.atStartOfDay(ZoneId.systemDefault).toInstant, ZoneId.systemDefault)
  private val jsonSchemaHelper = new JsonSchemaHelper(stubClock)
  private val controller = new CoreController(Helpers.stubControllerComponents(), jsonSchemaHelper, stubClock)
  private val validHeaders: Seq[(String, String)] = Seq(
    (AUTHORIZATION, ""),
    (ACCEPT, MimeTypes.JSON),
    ("X-Correlation-Id", UUID.randomUUID().toString),
    ("X-Forwarded-Host", ""),
    (CONTENT_TYPE, MimeTypes.JSON),
    (DATE, dateTimeFormatter.format(LocalDateTime.now())))

  val validFakeHeaders = new Headers(validHeaders)

  "POST /oss/returns/v1/return" - {
    "Return accepted when valid payload" in {

      val now = Instant.now(stubClock).truncatedTo(ChronoUnit.MILLIS)
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
        )),
        LocalDateTime.now(stubClock)
      )

      val fakeRequestWithBody = fakeRequest.withJsonBody(Json.toJson(coreVatReturn)).withHeaders(validFakeHeaders)

      val result = controller.submitVatReturn()(fakeRequestWithBody)

      status(result) shouldBe Status.ACCEPTED
    }

    "Return missing registration error for vrn 222222222" in {

      val now = Instant.now()
      val period = Period(2021, Q3)
      val coreVatReturn = CoreVatReturn(
        "XI/XI222222222/Q4.2021",
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
        )),
        LocalDateTime.now(stubClock)
      )

      val fakeRequestWithBody = fakeRequest.withJsonBody(Json.toJson(coreVatReturn))
        .withHeaders(validFakeHeaders)

      val result = controller.submitVatReturn()(fakeRequestWithBody)

      status(result) shouldBe Status.BAD_REQUEST
    }

    "Return error when invalid payload" in {

      val coreVatReturn = """{"badJson":"bad"}""""

      val fakeRequestWithBody = fakeRequest.withJsonBody(Json.toJson(coreVatReturn)).withHeaders(validFakeHeaders)

      val result = controller.submitVatReturn()(fakeRequestWithBody)

      status(result) shouldBe Status.BAD_REQUEST

      val responseBody = contentAsString(result)
      responseBody.isEmpty shouldBe false
      val errorResponse = Json.parse(responseBody).validate[EisErrorResponse]
      errorResponse.isSuccess shouldBe true
    }

    "Return error when using more than two decimal digits" in {

      val coreVatReturn = s"""{
                            |  "vatReturnReferenceNumber" : "XI/XI123456789/Q4.2021",
                            |  "version" : "2022-03-07T14:58:07.374Z",
                            |  "traderId" : {
                            |    "vatNumber" : "123456789AAA",
                            |    "issuedBy" : "XI"
                            |  },
                            |  "period" : {
                            |    "year" : 2021,
                            |    "quarter" : 3
                            |  },
                            |  "startDate" : "2021-07-01",
                            |  "endDate" : "2021-09-30",
                            |  "submissionDateTime" : "2022-03-07T14:58:07.374Z",
                            |  "totalAmountVatDueGBP" : 5000.123456789,
                            |  "msconSupplies" : [ {
                            |    "msconCountryCode" : "DE",
                            |    "balanceOfVatDueGBP" : 5000.123456789,
                            |    "grandTotalMsidGoodsGBP" : 1000.123456789,
                            |    "grandTotalMsestGoodsGBP" : 1000.123456789,
                            |    "correctionsTotalGBP" : 1000.123456789,
                            |    "msidSupplies" : [ {
                            |      "supplyType" : "GOODS",
                            |      "vatRate" : 10,
                            |      "vatRateType" : "STANDARD",
                            |      "taxableAmountGBP" : 10.123456789,
                            |      "vatAmountGBP" : 10.123456789
                            |    } ],
                            |    "msestSupplies" : [ {
                            |      "countryCode" : "DE",
                            |      "supplies" : [ {
                            |        "supplyType" : "GOODS",
                            |        "vatRate" : 10,
                            |        "vatRateType" : "STANDARD",
                            |        "taxableAmountGBP" : 10.123456789,
                            |        "vatAmountGBP" : 100.123456789
                            |      } ]
                            |    } ],
                            |    "corrections" : [ {
                            |      "period" : {
                            |        "year" : 2021,
                            |        "quarter" : 2
                            |      },
                            |      "totalVatAmountCorrectionGBP" : 100.123456789
                            |    } ]
                            |  } ],
                            |  "changeDate" : "${LocalDateTime.now(stubClock)}"
                            |}"""".stripMargin

      val fakeRequestWithBody = fakeRequest.withJsonBody(Json.parse(coreVatReturn)).withHeaders(validFakeHeaders)

      val result = controller.submitVatReturn()(fakeRequestWithBody)

      status(result) shouldBe Status.BAD_REQUEST

      val responseBody = contentAsString(result)
      responseBody.isEmpty shouldBe false
      val errorResponse = Json.parse(responseBody).validate[EisErrorResponse]
      errorResponse.isSuccess shouldBe true
    }

    "Return accepted when using one decimal digit" in {

      val coreVatReturn = s"""{
                            |  "vatReturnReferenceNumber" : "XI/XI123456789/Q4.2021",
                            |  "version" : "2022-03-07T14:58:07.374Z",
                            |  "traderId" : {
                            |    "vatNumber" : "123456789AAA",
                            |    "issuedBy" : "XI"
                            |  },
                            |  "period" : {
                            |    "year" : 2021,
                            |    "quarter" : 3
                            |  },
                            |  "startDate" : "2021-07-01",
                            |  "endDate" : "2021-09-30",
                            |  "submissionDateTime" : "2022-03-07T14:58:07.374Z",
                            |  "totalAmountVatDueGBP" : 5000.1,
                            |  "msconSupplies" : [ {
                            |    "msconCountryCode" : "DE",
                            |    "balanceOfVatDueGBP" : 5000.1,
                            |    "grandTotalMsidGoodsGBP" : 1000.1,
                            |    "grandTotalMsestGoodsGBP" : 1000.1,
                            |    "correctionsTotalGBP" : 1000.1,
                            |    "msidSupplies" : [ {
                            |      "supplyType" : "GOODS",
                            |      "vatRate" : 10,
                            |      "vatRateType" : "STANDARD",
                            |      "taxableAmountGBP" : 10.1,
                            |      "vatAmountGBP" : 10.1
                            |    } ],
                            |    "msestSupplies" : [ {
                            |      "countryCode" : "DE",
                            |      "supplies" : [ {
                            |        "supplyType" : "GOODS",
                            |        "vatRate" : 10,
                            |        "vatRateType" : "STANDARD",
                            |        "taxableAmountGBP" : 10.1,
                            |        "vatAmountGBP" : 100.1
                            |      } ]
                            |    } ],
                            |    "corrections" : [ {
                            |      "period" : {
                            |        "year" : 2021,
                            |        "quarter" : 2
                            |      },
                            |      "totalVatAmountCorrectionGBP" : 100.1
                            |    } ]
                            |  } ],
                            |  "changeDate" : "${Instant.now(stubClock)}"
                            |}"""".stripMargin

      val fakeRequestWithBody = fakeRequest.withJsonBody(Json.parse(coreVatReturn)).withHeaders(validFakeHeaders)

      val result = controller.submitVatReturn()(fakeRequestWithBody)

      status(result) shouldBe Status.ACCEPTED
    }

    "Return accepted when using two decimal digits" in {

      val coreVatReturn = s"""{
                            |  "vatReturnReferenceNumber" : "XI/XI123456789/Q4.2021",
                            |  "version" : "2022-03-07T14:58:07.374Z",
                            |  "traderId" : {
                            |    "vatNumber" : "123456789AAA",
                            |    "issuedBy" : "XI"
                            |  },
                            |  "period" : {
                            |    "year" : 2021,
                            |    "quarter" : 3
                            |  },
                            |  "startDate" : "2021-07-01",
                            |  "endDate" : "2021-09-30",
                            |  "submissionDateTime" : "2022-03-07T14:58:07.374Z",
                            |  "totalAmountVatDueGBP" : 5000.10,
                            |  "msconSupplies" : [ {
                            |    "msconCountryCode" : "DE",
                            |    "balanceOfVatDueGBP" : 5000.10,
                            |    "grandTotalMsidGoodsGBP" : 1000.10,
                            |    "grandTotalMsestGoodsGBP" : 1000.10,
                            |    "correctionsTotalGBP" : 1000.10,
                            |    "msidSupplies" : [ {
                            |      "supplyType" : "GOODS",
                            |      "vatRate" : 10,
                            |      "vatRateType" : "STANDARD",
                            |      "taxableAmountGBP" : 10.10,
                            |      "vatAmountGBP" : 10.10
                            |    } ],
                            |    "msestSupplies" : [ {
                            |      "countryCode" : "DE",
                            |      "supplies" : [ {
                            |        "supplyType" : "GOODS",
                            |        "vatRate" : 10,
                            |        "vatRateType" : "STANDARD",
                            |        "taxableAmountGBP" : 10.10,
                            |        "vatAmountGBP" : 100.10
                            |      } ]
                            |    } ],
                            |    "corrections" : [ {
                            |      "period" : {
                            |        "year" : 2021,
                            |        "quarter" : 2
                            |      },
                            |      "totalVatAmountCorrectionGBP" : 100.10
                            |    } ]
                            |  } ],
                            |  "changeDate" : "${Instant.now(stubClock)}"
                            |}"""".stripMargin

      val fakeRequestWithBody = fakeRequest.withJsonBody(Json.parse(coreVatReturn)).withHeaders(validFakeHeaders)

      val result = controller.submitVatReturn()(fakeRequestWithBody)

      status(result) shouldBe Status.ACCEPTED
    }

    "Return accepted for negative correction" in {

      val coreVatReturn = s"""{
                            |  "vatReturnReferenceNumber" : "XI/XI123456789/Q4.2021",
                            |  "version" : "2022-03-07T14:58:07.374Z",
                            |  "traderId" : {
                            |    "vatNumber" : "123456789AAA",
                            |    "issuedBy" : "XI"
                            |  },
                            |  "period" : {
                            |    "year" : 2021,
                            |    "quarter" : 3
                            |  },
                            |  "startDate" : "2021-07-01",
                            |  "endDate" : "2021-09-30",
                            |  "submissionDateTime" : "2022-03-07T14:58:07.374Z",
                            |  "totalAmountVatDueGBP" : 0,
                            |  "msconSupplies" : [ {
                            |    "msconCountryCode" : "DE",
                            |    "balanceOfVatDueGBP" : -10,
                            |    "grandTotalMsidGoodsGBP" : 0,
                            |    "grandTotalMsestGoodsGBP" : 0,
                            |    "correctionsTotalGBP" : -10,
                            |    "msidSupplies" : [],
                            |    "msestSupplies" : [],
                            |    "corrections" : [ {
                            |      "period" : {
                            |        "year" : 2021,
                            |        "quarter" : 2
                            |      },
                            |      "totalVatAmountCorrectionGBP" : -10
                            |    } ]
                            |  } ],
                            |  "changeDate" : "${Instant.now(stubClock)}"
                            |}"""".stripMargin

      val fakeRequestWithBody = fakeRequest.withJsonBody(Json.parse(coreVatReturn)).withHeaders(validFakeHeaders)

      val result = controller.submitVatReturn()(fakeRequestWithBody)

      status(result) shouldBe Status.ACCEPTED
    }

    "Return error when vat rate is invalid" in {

      val coreVatReturn = s"""{
                            |  "vatReturnReferenceNumber" : "XI/XI123456789/Q4.2021",
                            |  "version" : "2022-03-07T14:58:07.374Z",
                            |  "traderId" : {
                            |    "vatNumber" : "123456789AAA",
                            |    "issuedBy" : "XI"
                            |  },
                            |  "period" : {
                            |    "year" : 2021,
                            |    "quarter" : 3
                            |  },
                            |  "startDate" : "2021-07-01",
                            |  "endDate" : "2021-09-30",
                            |  "submissionDateTime" : "2022-03-07T14:58:07.374Z",
                            |  "totalAmountVatDueGBP" : 5000.12,
                            |  "msconSupplies" : [ {
                            |    "msconCountryCode" : "DE",
                            |    "balanceOfVatDueGBP" : 5000.12,
                            |    "grandTotalMsidGoodsGBP" : 1000.12,
                            |    "grandTotalMsestGoodsGBP" : 1000.12,
                            |    "correctionsTotalGBP" : 1000.12,
                            |    "msidSupplies" : [ {
                            |      "supplyType" : "GOODS",
                            |      "vatRate" : 101,
                            |      "vatRateType" : "STANDARD",
                            |      "taxableAmountGBP" : 10.12,
                            |      "vatAmountGBP" : 10.12
                            |    } ],
                            |    "msestSupplies" : [ {
                            |      "countryCode" : "DE",
                            |      "supplies" : [ {
                            |        "supplyType" : "GOODS",
                            |        "vatRate" : 10.001,
                            |        "vatRateType" : "STANDARD",
                            |        "taxableAmountGBP" : 10.12,
                            |        "vatAmountGBP" : 100.12
                            |      } ]
                            |    } ],
                            |    "corrections" : [ {
                            |      "period" : {
                            |        "year" : 2021,
                            |        "quarter" : 2
                            |      },
                            |      "totalVatAmountCorrectionGBP" : 100.12
                            |    } ]
                            |  } ],
                            |  "changeDate" : "${LocalDateTime.now(stubClock)}"
                            |}"""".stripMargin

      val fakeRequestWithBody = fakeRequest.withJsonBody(Json.parse(coreVatReturn)).withHeaders(validFakeHeaders)

      val result = controller.submitVatReturn()(fakeRequestWithBody)

      status(result) shouldBe Status.BAD_REQUEST
    }

    "Return bad request when headers are missing" in {

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
        )),
        LocalDateTime.now(stubClock)
      )

      val fakeRequestWithBody = fakeRequest.withJsonBody(Json.toJson(coreVatReturn))

      val result = controller.submitVatReturn()(fakeRequestWithBody)

      status(result) shouldBe Status.BAD_REQUEST
    }
  }

  "POST /oss/referencedata/v1/exchangerate must" - {
    val timestamp = LocalDateTime.of(2022, 1, 1, 1, 1)
    val base = "EUR"
    val target = "GBP"
    val rate = CoreRate(
      timestamp.toLocalDate,
      BigDecimal(10))

    val exchangeRateRequest = CoreExchangeRateRequest(
      base,
      target,
      timestamp,
      Seq(rate))

    "return ok for valid json" in {

      val fakeRequestWithBody = fakeRequest.withJsonBody(Json.toJson(exchangeRateRequest)).withHeaders(validFakeHeaders)

      val result = controller.submitRates()(fakeRequestWithBody)

      status(result) shouldBe Status.OK
    }

    "return bad request for a timestamp without milliseconds" in {

      val badJson = """{
                      |  "base" : "EUR",
                      |  "target" : "GBP",
                      |  "timestamp" : "2022-01-01T01:01:00Z",
                      |  "rates" : [ {
                      |    "publishedDate" : "2022-01-10",
                      |    "rate" : 10
                      |  } ]
                      |}""".stripMargin

      val fakeRequestWithBody = fakeRequest.withJsonBody(Json.parse(badJson)).withHeaders(validFakeHeaders)

      val result = controller.submitRates()(fakeRequestWithBody)

      status(result) shouldBe Status.BAD_REQUEST
    }

    "return bad request for a published date that includes hours, minutes and seconds" in {

      val badJson = """{
                      |  "base" : "EUR",
                      |  "target" : "GBP",
                      |  "timestamp" : "2022-01-01T01:01:00.000Z",
                      |  "rates" : [ {
                      |    "publishedDate" : "2022-01-01T01:01:00.000Z",
                      |    "rate" : 10
                      |  } ]
                      |}""".stripMargin

      val fakeRequestWithBody = fakeRequest.withJsonBody(Json.parse(badJson)).withHeaders(validFakeHeaders)

      val result = controller.submitRates()(fakeRequestWithBody)

      status(result) shouldBe Status.BAD_REQUEST
    }

    "return bad request for an invalid published date" in {

      val badJson = """{
                      |  "base" : "EUR",
                      |  "target" : "GBP",
                      |  "timestamp" : "2022-01-01T01:01:00.000Z",
                      |  "rates" : [ {
                      |    "publishedDate" : "hello",
                      |    "rate" : 10
                      |  } ]
                      |}""".stripMargin

      val fakeRequestWithBody = fakeRequest.withJsonBody(Json.parse(badJson)).withHeaders(validFakeHeaders)

      val result = controller.submitRates()(fakeRequestWithBody)

      status(result) shouldBe Status.BAD_REQUEST

    }


    "return bad request for a invalid json" in {
      val fakeRequestWithBody = fakeRequest.withJsonBody(Json.toJson("invalid json")).withHeaders(validFakeHeaders)

      val result = controller.submitRates()(fakeRequestWithBody)

      status(result) shouldBe Status.BAD_REQUEST

    }

    "return Conflict for the 20th of the month" in {
      val fakeRequestWithBody = fakeRequest.withJsonBody(Json.toJson(exchangeRateRequest.copy(timestamp = LocalDateTime.of(2022, 1, 20, 1, 1)))).withHeaders(validFakeHeaders)

      val result = controller.submitRates()(fakeRequestWithBody)

      status(result) shouldBe Status.CONFLICT
    }

    "return bad request for missing headers" in {
      val fakeRequestWithBody = fakeRequest.withJsonBody(Json.toJson(exchangeRateRequest))

      val result = controller.submitRates()(fakeRequestWithBody)

      status(result) shouldBe Status.BAD_REQUEST
    }
  }

}
