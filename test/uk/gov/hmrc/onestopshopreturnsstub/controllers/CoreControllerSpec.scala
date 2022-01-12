/*
 * Copyright 2021 HM Revenue & Customs
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
import play.api.http.Status
import play.api.libs.json.Json
import play.api.test.{FakeRequest, Helpers}
import play.api.test.Helpers._
import uk.gov.hmrc.onestopshopreturnsstub.models.core._
import uk.gov.hmrc.onestopshopreturnsstub.models.Period
import uk.gov.hmrc.onestopshopreturnsstub.models.Quarter._
import uk.gov.hmrc.onestopshopreturnsstub.utils.JsonSchemaHelper

import java.time.{Clock, Instant, LocalDate, ZoneId}

class CoreControllerSpec extends AnyFreeSpec with Matchers {


  private val fakeRequest = FakeRequest(POST, routes.CoreController.submitVatReturn().url)
  private val stubClock: Clock = Clock.fixed(LocalDate.now.atStartOfDay(ZoneId.systemDefault).toInstant, ZoneId.systemDefault)
  private val jsonSchemaHelper = new JsonSchemaHelper(stubClock)
  private val controller = new CoreController(Helpers.stubControllerComponents(), jsonSchemaHelper)

  "POST /oss/returns/v1/return" - {
    "Return ok when valid payload" in {

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

      val fakeRequestWithBody = fakeRequest.withJsonBody(Json.toJson(coreVatReturn))

      val result = controller.submitVatReturn()(fakeRequestWithBody)

      status(result) shouldBe Status.ACCEPTED
    }

    "Return error when invalid payload" in {

      val coreVatReturn = """{"badJson":"bad"}""""

      val fakeRequestWithBody = fakeRequest.withJsonBody(Json.toJson(coreVatReturn))

      val result = controller.submitVatReturn()(fakeRequestWithBody)

      status(result) shouldBe Status.BAD_REQUEST
    }
  }

}
