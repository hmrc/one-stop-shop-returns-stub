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

import play.api.libs.json.Json
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController
import play.api.mvc.{Action, AnyContent, ControllerComponents}
import uk.gov.hmrc.onestopshopreturnsstub.models.Quarter.Q3
import uk.gov.hmrc.onestopshopreturnsstub.models.{FinancialDataResponse, FinancialTransaction, Item, Period}

import java.time.{Clock, LocalDate, ZonedDateTime}
import javax.inject.{Inject, Singleton}
import scala.concurrent.Future

@Singleton()
class FinancialDataController @Inject()(
                                         cc: ControllerComponents,
                                         clock: Clock
                                       )
  extends BackendController(cc) {

  val period = Period(2021, Q3)
  val items = Seq(
    Item(
      amount = Some(BigDecimal(1000)),
      clearingReason = Some("01"),
      paymentReference = Some("a"),
      paymentAmount = Some(BigDecimal(500)),
      paymentMethod = Some("A")
    )
  )

  val financialTransactions = Seq(
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

  val allPaidItems = Seq(
    Item(
      amount = Some(BigDecimal(1500)),
      clearingReason = Some("01"),
      paymentReference = Some("a"),
      paymentAmount = Some(BigDecimal(1500)),
      paymentMethod = Some("A")
    )
  )

  val allPaidFinancialTransactions = Seq(
    FinancialTransaction(
      chargeType = Some("G Ret FR EU-OMS"),
      mainType = None,
      taxPeriodFrom = Some(period.firstDay),
      taxPeriodTo = Some(period.lastDay),
      originalAmount = Some(BigDecimal(1500)),
      outstandingAmount = Some(BigDecimal(0)),
      clearedAmount = Some(BigDecimal(1500)),
      items = Some(allPaidItems)
    )
  )
  val somePaidItems = Seq(
    Item(
      amount = Some(BigDecimal(1000)),
      clearingReason = Some("01"),
      paymentReference = Some("a"),
      paymentAmount = Some(BigDecimal(1000)),
      paymentMethod = Some("A")
    )
  )
  val somePaidFinancialTransactions = Seq(
    FinancialTransaction(
      chargeType = Some("G Ret FR EU-OMS"),
      mainType = None,
      taxPeriodFrom = Some(period.firstDay),
      taxPeriodTo = Some(period.lastDay),
      originalAmount = Some(BigDecimal(1500)),
      outstandingAmount = Some(BigDecimal(1000)),
      clearedAmount = Some(BigDecimal(500)),
      items = Some(somePaidItems)
    )
  )

  val notPaidFinancialTransactions = Seq(
    FinancialTransaction(
      chargeType = Some("G Ret FR EU-OMS"),
      mainType = None,
      taxPeriodFrom = Some(period.firstDay),
      taxPeriodTo = Some(period.lastDay),
      originalAmount = Some(BigDecimal(1500)),
      outstandingAmount = Some(BigDecimal(1500)),
      clearedAmount = Some(BigDecimal(0)),
      items = None
    )
  )

  val successfulResponse = FinancialDataResponse(
    idType = None,
    idNumber = None,
    regimeType = None,
    processingDate = ZonedDateTime.now(clock),
    financialTransactions = Some(financialTransactions)
  )

  def getFinancialData(idType: String, idNumber: String, regimeType: String): Action[AnyContent] = Action.async { implicit request =>
    val response = idNumber.head match {
      case '1' => successfulResponse.copy(financialTransactions = Some(allPaidFinancialTransactions))
      case '2' => successfulResponse.copy(financialTransactions = Some(somePaidFinancialTransactions))
      case '3' => successfulResponse.copy(financialTransactions = Some(notPaidFinancialTransactions))
      case _ => successfulResponse
    }
    Future.successful(Ok(Json.toJson(response)))
  }
}
