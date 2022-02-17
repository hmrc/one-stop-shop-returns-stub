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

import play.api.Logging
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import uk.gov.hmrc.onestopshopreturnsstub.models.core.CoreErrorResponse
import uk.gov.hmrc.onestopshopreturnsstub.utils.JsonSchemaHelper
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController

import java.time.{Clock, Instant}
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton()
class CoreController  @Inject()(
                                 cc: ControllerComponents,
                                 jsonSchemaHelper: JsonSchemaHelper,
                                 headerHelper: CoreVatReturnHeaderHelper,
                                 clock: Clock
                               )
  extends BackendController(cc) with Logging {

  implicit val ec: ExecutionContext = cc.executionContext

  def submitVatReturn(): Action[AnyContent] = Action.async { implicit request =>

    logger.info(s"Here's the request: ${request} ${request.headers} ${request.body}")
    val jsonBody: Option[JsValue] = request.body.asJson
    jsonSchemaHelper.applySchemaHeaderValidation(request.headers) {
      jsonSchemaHelper.applySchemaValidation("/resources/schemas/core_return.json", jsonBody) {


        val rawValue = jsonBody.map(body => (body \ "vatReturnReferenceNumber").as[String])
        if(rawValue.exists(_.contains("222222222"))) {
          logger.info("Resource not found: Registration")
          Future.successful(BadRequest(Json.toJson(CoreErrorResponse(Instant.now(clock), None, "OSS_009", "Resource not found: Registration"))))
        }else{
          logger.info("Successfully submitted vat return")
          Future.successful(Accepted(""))
        }

      }
    }
  }

}
