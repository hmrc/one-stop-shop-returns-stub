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

/*
 * Copyright 2020 HM Revenue & Customs
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

package uk.gov.hmrc.onestopshopreturnsstub.utils

import com.github.fge.jsonschema.core.report.ProcessingReport
import com.github.fge.jsonschema.main.{JsonSchema, JsonSchemaFactory}
import play.api.libs.json.{Json, JsValue}
import com.fasterxml.jackson.databind.{JsonNode, ObjectMapper}
import com.fasterxml.jackson.core.JsonParser

import scala.concurrent.Future
import play.api.Logging
import play.api.mvc._
import play.api.mvc.Results._

import scala.util.{Failure, Success, Try}
import scala.io.Source
import play.api.libs.json._
import uk.gov.hmrc.onestopshopreturnsstub.models.core.CoreErrorResponse

import java.time.Instant
import java.util.UUID
import scala.concurrent._

object JsonSchemaHelper extends Logging {

  private final lazy val jsonMapper = new ObjectMapper()
  private final lazy val jsonFactory = jsonMapper.getFactory

  private final lazy val correlationIdRegex = "^[0-9a-fA-F]{8}[-][0-9a-fA-F]{4}[-][0-9a-fA-F]{4}[-][0-9a-fA-F]{4}[-][0-9a-fA-F]{12}$"

  def loadRequestSchema(requestSchema: JsValue): JsonSchema = {
    val schemaMapper = new ObjectMapper()
    val factory = schemaMapper.getFactory
    val schemaParser: JsonParser = factory.createParser(requestSchema.toString)
    val schemaJson: JsonNode = schemaMapper.readTree(schemaParser)
    JsonSchemaFactory.byDefault().getJsonSchema(schemaJson)
  }

  def validRequest(jsonSchema: JsValue, json: Option[JsValue]): Option[ProcessingReport] = {
    json.map { response =>
      val jsonParser = jsonFactory.createParser(response.toString)
      val jsonNode: JsonNode = jsonMapper.readTree(jsonParser)
      val result: ProcessingReport = loadRequestSchema(jsonSchema).validate(jsonNode)
      result
    }
  }

  def applySchemaValidation(schemaPath: String, jsonBody: Option[JsValue])(f: => Future[Result]): Future[Result] = {
    retrieveJsonSchema(schemaPath) match {
      case Success(schema) =>
        val validationResult = JsonSchemaHelper.validRequest(schema, jsonBody)
        validationResult match {
            case Some(res) if res.isSuccess => f
            case Some(res) =>
              logger.info(s"validation error ${res.toString}")
              Future.successful(BadRequest(Json.toJson(CoreErrorResponse(Instant.now(), UUID.randomUUID(), "OSS_400", "Bad Request"))))
            case _ =>
              Future.successful(BadRequest(Json.toJson(CoreErrorResponse(Instant.now(), UUID.randomUUID(), "OSS_400", "Missing Payload"))))
        }
      case Failure(e) =>
        logger.error(s"Error: ${e.getMessage}", e)
        Future.successful(InternalServerError(""))
    }
  }

  def retrieveJsonSchema(schemaPath: String): Try[JsValue] = {
    val jsonSchema = Try(Source.fromInputStream(getClass.getResourceAsStream(schemaPath)).mkString)
    jsonSchema.map(Json.parse)
  }

  def applySchemaHeaderValidation(headers: Headers)(f: => Future[Result])(implicit ec: ExecutionContext): Future[Result] = {
    val maybeCorrelationId: Option[String] = headers.get("X-Correlation-ID")

    maybeCorrelationId match {
      case Some(correlationId) if isValidCorrelationId(correlationId) => f.map(_.withHeaders("X-Correlation-ID" -> correlationId))
      case Some(_) => Future.successful(BadRequest(Json.toJson(CoreErrorResponse(Instant.now(), UUID.randomUUID(), "OSS_400", "Bad Request - invalid correlationId"))))
      case _ => f
    }


  }

  def isValidCorrelationId(correlationId: String): Boolean =
    correlationId.matches(correlationIdRegex)
}