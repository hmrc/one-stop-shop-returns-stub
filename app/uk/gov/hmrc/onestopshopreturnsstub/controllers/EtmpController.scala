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

import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import uk.gov.hmrc.onestopshopreturnsstub.models.etmp.EtmpReturnCorrectionValue
import uk.gov.hmrc.onestopshopreturnsstub.controllers.TestData.obligationDetails
import uk.gov.hmrc.onestopshopreturnsstub.models.ObligationsDateRange
import uk.gov.hmrc.onestopshopreturnsstub.models.etmp.EtmpObligations
import uk.gov.hmrc.onestopshopreturnsstub.utils.JsonSchemaHelper

import javax.inject._
import scala.concurrent.Future

@Singleton
class EtmpController @Inject()(
                                cc: ControllerComponents,
                                jsonSchemaHelper: JsonSchemaHelper
                              )
  extends BaseController {

  override protected def controllerComponents: ControllerComponents = cc

  def getReturnCorrection(vrn: String, country: String, period: String): Action[AnyContent] = Action.async {
    implicit request =>

      jsonSchemaHelper.applySchemaHeaderValidation(request.headers) {
        val accumulativeCorrectionAmount = (vrn, country, period) match {
          case _ => BigDecimal(0)
        }

        val etmpReturnCorrectionValueResponse: EtmpReturnCorrectionValue =
          EtmpReturnCorrectionValue(
            maximumCorrectionValue = accumulativeCorrectionAmount
          )

        Future.successful(Ok(Json.toJson(etmpReturnCorrectionValueResponse)))
      }
  }

  def getObligations(idType: String, idNumber: String, regimeType: String, dateRange: ObligationsDateRange, status: Option[String]): Action[AnyContent] = Action.async {
    implicit request =>

      jsonSchemaHelper.applySchemaHeaderValidation(request.headers) {

        def generateObligations(idNumber: String): EtmpObligations = idNumber match {
          case _ =>
            obligationDetails
        }

        val obligationResponse = generateObligations(idNumber)

        Future.successful(Ok(Json.toJson(obligationResponse)))
      }
  }

}
