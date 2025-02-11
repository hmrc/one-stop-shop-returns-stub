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

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import play.api.http.Status
import play.api.libs.json.JsSuccess
import play.api.test.Helpers._
import play.api.test.{FakeRequest, Helpers}
import uk.gov.hmrc.onestopshopreturnsstub.models.Quarter.Q3
import uk.gov.hmrc.onestopshopreturnsstub.models.{DateRange, FinancialDataResponse, FinancialTransaction, Item, Period}

import java.time.{Clock, LocalDate, ZonedDateTime, ZoneId}

class FinancialDataControllerSpec extends AnyWordSpec with Matchers {

  private val firstDateOfYear = LocalDate.of(2021, 1, 1)
  private val lastDateOfYear = LocalDate.of(2021, 12, 31)
  private val dateRange = DateRange(firstDateOfYear, lastDateOfYear)
  private val fakeRequest = FakeRequest("GET", routes.FinancialDataController.getFinancialData("", "0", "", dateRange).url)
  private val stubClock: Clock = Clock.fixed(LocalDate.now.atStartOfDay(ZoneId.systemDefault).toInstant, ZoneId.systemDefault)
  private val controller = new FinancialDataController(Helpers.stubControllerComponents(), stubClock)
  private val period = Period(2021, Q3)
  private val items = Seq(
    Item(
      amount = Some(BigDecimal(1000)),
      clearingReason = Some("01"),
      paymentReference = Some("a"),
      paymentAmount = Some(BigDecimal(500)),
      paymentMethod = Some("A")
    )
  )

  private val financialTransactions = Seq(
    FinancialTransaction(
      chargeType = Some("G Ret FR EU-OMS"),
      mainType = None,
      taxPeriodFrom = Some(period.firstDay),
      taxPeriodTo = Some(period.lastDay),
      originalAmount = Some(BigDecimal(1500)),
      outstandingAmount = Some(BigDecimal(500)),
      clearedAmount = Some(BigDecimal(1000)),
      items = Some(items)
    ))

  private val successfulResponse = FinancialDataResponse(
    idType = None,
    idNumber = None,
    regimeType = None,
    processingDate = ZonedDateTime.now(stubClock),
    financialTransactions = Some(financialTransactions)
  )

  "GET /financial-data" should {
    "return a successful FinancialDataResponse" in {
      val result = controller.getFinancialData(idType = "", idNumber = "012345678", regimeType = "", dateRange = dateRange)(fakeRequest)
      status(result) shouldBe Status.OK
      contentAsJson(result).validate[FinancialDataResponse] shouldBe JsSuccess(successfulResponse)
    }

    "return empty for wrong year" in {

      val result = controller.getFinancialData(idType = "", idNumber = "100000003", regimeType = "", dateRange = DateRange(LocalDate.of(2022, 1, 1), LocalDate.of(2022, 12, 31)))(fakeRequest)
      status(result) shouldBe Status.OK
      contentAsJson(result).validate[FinancialDataResponse] shouldBe
        JsSuccess(successfulResponse.copy(
          financialTransactions = Some(Seq.empty)))
    }

    "return a no vat owed when vat number is 500000003" in {

      val result = controller.getFinancialData(idType = "", idNumber = "500000003", regimeType = "", dateRange = dateRange)(fakeRequest)
      status(result) shouldBe Status.NOT_FOUND
    }
  }
}
