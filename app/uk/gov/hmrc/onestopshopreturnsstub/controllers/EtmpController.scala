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

import play.api.Logging
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import uk.gov.hmrc.onestopshopreturnsstub.controllers.TestData._
import uk.gov.hmrc.onestopshopreturnsstub.models.ObligationsDateRange
import uk.gov.hmrc.onestopshopreturnsstub.models.etmp.{EtmpObligations, EtmpReturnCorrectionValue}
import uk.gov.hmrc.onestopshopreturnsstub.utils.JsonSchemaHelper

import javax.inject._
import scala.concurrent.Future

@Singleton
class EtmpController @Inject()(
                                cc: ControllerComponents,
                                jsonSchemaHelper: JsonSchemaHelper
                              )
  extends BaseController with Logging {

  override protected def controllerComponents: ControllerComponents = cc

  def getReturnCorrection(vrn: String, country: String, period: String): Action[AnyContent] = Action.async {
    implicit request =>

      jsonSchemaHelper.applySchemaHeaderValidation(request.headers) {
        val accumulativeCorrectionAmount = (vrn, country, period) match {
          case ("100000003", "HR", "21Q3") => BigDecimal(7469.13)
          case ("100000003", "FR", "21Q3") => BigDecimal(1234.00)
//          case ("100000003", "DE", "21Q3") => BigDecimal(1469.13)
//          case ("100000003", "FR", "21Q3") => BigDecimal(2469.13)
//          case ("100000003", "AT", "21Q3") => BigDecimal(1469.13)
//          case ("100000003", "LT", "21Q3") => BigDecimal(1469.13)
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
          case "600000011" =>
            twoOpenObligationsExcluded
          case "600000012" =>
            oneOpenObligationExcluded
          case "600000013" =>
            multipleOpenObligationsExcluded
          case "600000014" =>
            oneOpenObligationQuarantined
          case "600000018" =>
            oneOpenObligationExcludedFuture
          case "600000019" | "100000026" =>
            oneFulfilledObligationExcluded2024
          case "600000020" | "100000025" =>
            oneOpenObligationExcluded2024
          case "600000021" =>
            twoFulfilledObligationDetails2022
          case "600001212" =>
            oneFulfilledObligationExcluded
          case "444444444" =>
            twoFulfilledObligationDetails2023
          case "166666666" =>
            fulfilledObligationOver6YearsAgo
          case "100000004" =>
            twoFulfilledObligationDetails
          case "100000003" =>
            oneFulfilledObligationDetails
          case "100000002" | "100000006" =>
            firstPeriodNoCorrections
          case _ =>
            obligationDetails
        }

        val obligationResponse = generateObligations(idNumber)

        Future.successful(Ok(Json.toJson(obligationResponse)))
      }
  }

  def getEtmpReturn(vrn: String, period: String): Action[AnyContent] = Action.async {
    implicit request =>

      logger.info(s"Here's the request: ${request} ${request.headers} ${request.body}")

      jsonSchemaHelper.applySchemaHeaderValidation(request.headers) {

        val etmpVatReturn = (vrn, period) match {
//          case ("100000003", _) => etmpVatReturnWithCorrections(vrn, period)
          case ("100000003", _) => etmpVatReturnWithoutCorrections(vrn, period)
          case ("100000002", _) => nilEtmpVatReturn(vrn, period)
          case _ => basicEtmpVatReturn(vrn, period)
        }

        Future.successful(Ok(Json.toJson(etmpVatReturn)))
      }
  }
}
