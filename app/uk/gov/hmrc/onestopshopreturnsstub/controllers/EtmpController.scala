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
import uk.gov.hmrc.onestopshopreturnsstub.controllers.TestData.*
import uk.gov.hmrc.onestopshopreturnsstub.models.ObligationsDateRange
import uk.gov.hmrc.onestopshopreturnsstub.models.etmp.{EtmpObligations, EtmpReturnCorrectionValue}
import uk.gov.hmrc.onestopshopreturnsstub.utils.JsonSchemaHelper

import java.time.LocalDate
import javax.inject.*
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
          case ("100000003", "HR", "21C3") => BigDecimal(7469.13)
          case ("100000003", "FR", "21C3") => BigDecimal(1234.00)
          case ("100000003", "ES", "21C3") => BigDecimal(1000.00)
          case ("100000004", "FR", "21C3") => BigDecimal(5000.00)
          case ("100000004", "DK", "21C3") => BigDecimal(3271.20)
          case ("100000004", "HR", "21C4") => BigDecimal(750.00)
          case ("100000004", "FR", "21C4") => BigDecimal(1000.00)
          case ("100000004", "AT", "21C4") => BigDecimal(1000.00)
          case ("100000004", "BE", "22C1") => BigDecimal(2500.00)
          case ("100000004", "PL", "22C1") => BigDecimal(123.45)
          case ("100000004", "IE", "22C1") => BigDecimal(987.65)
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

        def generateObligations(idNumber: String): EtmpObligations =
          if (idNumber.startsWith("2220")) {
            oneFulfilledObligationDetails
          } else {
            idNumber match {
              case "600000006" =>
                openObligationsOverThreeYearsAgoExpiredVRN
              case "600000011" =>
                twoOpenObligationsExcluded
              case "600000012" =>
                oneOpenObligationExcluded
              case "600000013" =>
                multipleOpenObligationsExcluded
              case "600000014" =>
                oneOpenObligationQuarantined
              case "600001414" =>
                oneFulfilledObligationQuarantinedOverTwoYears
              case "600000018" =>
                oneOpenObligationExcludedFuture
              case "600000019" | "100000026" | "600000003" | "600000005" | "100000301" | "600000050"
                   | "500000002" | "333333222" | "333333111" | "333333666" | "333333444" | "333333777" | "333333555" =>
                oneFulfilledObligationExcluded2024
              case "600000020" | "100000025" | "600000002" | "600000004" =>
                oneOpenObligationExcluded2024
              case "600000021" | "777777771" =>
                twoFulfilledObligationDetails2022
              case "600001212" =>
                oneFulfilledObligationExcluded
              case "444444444" =>
                twoFulfilledObligationDetails2023
              case "166666666" =>
                fulfilledObligationOver6YearsAgo
              case "100000004" =>
                threeFulfilledObligationDetails
              case "100000003" =>
                oneFulfilledObligationDetails
              case "100000002" | "100000006" | "222222222" | "222222223" =>
                firstPeriodNoCorrections
              case "100000007" | "600000015" =>
                firstPeriodNoCorrections2023
              case "100000077" | "600001515" =>
                secondOpenPeriodPartialReturns2023
              case "600151515" =>
                fulfilledPeriodsPartialReturns2023
              case _ =>
                obligationDetails
            }
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
          case ("100000003", _) => etmpVatReturnWithoutCorrections(vrn, period)
          case ("100000004", "21C3") => etmpVatReturnQ1(vrn, period)
          case ("100000004", "21C4") => etmpVatReturnQ2(vrn, period)
          case ("100000004", "22C1") => etmpVatReturnQ3(vrn, period)
          case ("100000077", "23C2") => etmpVatReturnPartialDates(vrn, period, LocalDate.of(2023, 6, 9), LocalDate.of(2023, 6, 30))
          case ("600151515", "23C3") => etmpVatReturnPartialDates(vrn, period, LocalDate.of(2023, 7, 1), LocalDate.of(2023, 9, 8))
          case ("600000019", _) | ("100000026", _) | ("600000003", _) | ("600000005", _) |
               ("600000021", _) | ("777777771", _) | ("600001212", _) =>
            nilEtmpVatReturn(vrn, period)
          case _ => basicEtmpVatReturn(vrn, period)
        }

        Future.successful(Ok(Json.toJson(etmpVatReturn)))
      }
  }
}
