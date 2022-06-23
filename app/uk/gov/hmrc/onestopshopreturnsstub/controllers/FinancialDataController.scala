/*
 * Copyright 2022 HM Revenue & Customs
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
import play.api.mvc.{Action, AnyContent, ControllerComponents}
import uk.gov.hmrc.onestopshopreturnsstub.models.{DateRange, FinancialDataResponse}
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController

import java.time.{Clock, ZonedDateTime}
import javax.inject.{Inject, Singleton}
import scala.concurrent.Future

@Singleton()
class FinancialDataController @Inject()(
                                         cc: ControllerComponents,
                                         clock: Clock
                                       )
  extends BackendController(cc) {


  val successfulResponse = FinancialDataResponse(
    idType = None,
    idNumber = None,
    regimeType = None,
    processingDate = ZonedDateTime.now(clock),
    financialTransactions = Some(TestData.financialTransactions)
  )

  def getFinancialData(idType: String, idNumber: String, regimeType: String, dateRange: DateRange): Action[AnyContent] = Action.async {

    val (responseStatus, maybeFinancialTransactions) = idNumber.head match {
      case '1' => (Ok, Some(TestData.allPaidFinancialTransactions))
      case '2' => (Ok, Some(TestData.somePaidFinancialTransactions))
      case '3' => (Ok, Some(TestData.notPaidFinancialTransactions))
      case '4' => (Ok, Some(TestData.multipleItemsNotPaidFinancialTransactions))
      case '5' => (NotFound, None)
      case _ => (Ok, successfulResponse.financialTransactions)
    }

    val filteredFinancialTransactions = maybeFinancialTransactions.map { financialTransactions =>
      val requestedYear = dateRange.toDate.getYear

      financialTransactions.filter { financialTransaction =>
        financialTransaction.taxPeriodTo.map(_.getYear).contains(requestedYear)
      }
    }

    val response = successfulResponse.copy(financialTransactions = filteredFinancialTransactions)

    Future.successful(responseStatus match {
      case Ok => Ok(Json.toJson(response))
      case NotFound => NotFound
    })
  }
}
