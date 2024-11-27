/*
 * Copyright 2023 HM Revenue & Customs
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
import play.api.http.HeaderNames.{ACCEPT, AUTHORIZATION, CONTENT_TYPE, DATE}
import play.api.libs.json.Json
import play.api.mvc.Headers
import play.api.test.{FakeRequest, Helpers}
import play.api.test.Helpers._
import uk.gov.hmrc.onestopshopreturnsstub.models.etmp.EtmpReturnCorrectionValue
import uk.gov.hmrc.onestopshopreturnsstub.models.{Period, Quarter}
import uk.gov.hmrc.onestopshopreturnsstub.utils.JsonSchemaHelper

import java.time._
import java.time.format.DateTimeFormatter
import java.util.{Locale, UUID}

class EtmpControllerSpec extends AnyFreeSpec with Matchers {

  private val vrn = "123456789"
  private val period = Period(2023, Quarter.Q3)
  private val country: String = "DE"

  private val dateTimeFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z")
    .withLocale(Locale.UK)
    .withZone(ZoneId.of("GMT"))
  private val stubClock: Clock = Clock.fixed(LocalDate.now.atStartOfDay(ZoneId.systemDefault).toInstant, ZoneId.systemDefault)
  private val jsonSchemaHelper = new JsonSchemaHelper(stubClock)
  private val controller = new EtmpController(Helpers.stubControllerComponents(), jsonSchemaHelper)
  private val validHeaders: Seq[(String, String)] = Seq(
    (AUTHORIZATION, ""),
    (ACCEPT, MimeTypes.JSON),
    ("X-Correlation-Id", UUID.randomUUID().toString),
    ("X-Forwarded-Host", ""),
    (CONTENT_TYPE, MimeTypes.JSON),
    (DATE, dateTimeFormatter.format(LocalDateTime.now())))

  val validFakeHeaders = new Headers(validHeaders)

  "GET /vec/ossreturns/returncorrection/v1/{VRN}/{MSCON}{PeriodKey}" - {

    val fakeRequest = FakeRequest(POST, routes.EtmpController.getReturnCorrection(vrn, country, period.toEtmpPeriodString).url)

    val etmpReturnCorrectionValue: EtmpReturnCorrectionValue =
      EtmpReturnCorrectionValue(
        maximumCorrectionValue = BigDecimal(0.0)
      )

    "must return OK with a successful payload" in {

      val fakeRequestWithBody = fakeRequest.withHeaders(validFakeHeaders)

      val result = controller.getReturnCorrection(vrn, country, period.toEtmpPeriodString)(fakeRequestWithBody)

      status(result) shouldBe Status.OK
      contentAsJson(result) shouldBe Json.toJson(etmpReturnCorrectionValue)
    }

    "must return Bad Request when headers are missing" in {

      val fakeRequestWithBody = fakeRequest

      val result = controller.getReturnCorrection(vrn, country, period.toEtmpPeriodString)(fakeRequestWithBody)

      status(result) shouldBe Status.BAD_REQUEST
    }
  }
}
