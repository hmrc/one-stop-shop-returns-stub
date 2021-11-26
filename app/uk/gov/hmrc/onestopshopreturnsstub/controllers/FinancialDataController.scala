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
import play.api.mvc.{Action, AnyContent, ControllerComponents}
import uk.gov.hmrc.onestopshopreturnsstub.models.FinancialDataResponse
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

  def getFinancialData(idType: String, idNumber: String, regimeType: String): Action[AnyContent] = Action.async { implicit request =>
    val response = idNumber.head match {
      case '1' => successfulResponse.copy(financialTransactions = Some(TestData.allPaidFinancialTransactions))
      case '2' => successfulResponse.copy(financialTransactions = Some(TestData.somePaidFinancialTransactions))
      case '3' => successfulResponse.copy(financialTransactions = Some(TestData.notPaidFinancialTransactions))
      case '4' => successfulResponse.copy(financialTransactions = Some(TestData.multipleItemsNotPaidFinancialTransactions))
      case '5' => successfulResponse.copy(financialTransactions = None)
      case _ => successfulResponse
    }
    Future.successful(Ok(Json.toJson(response)))
  }
}
