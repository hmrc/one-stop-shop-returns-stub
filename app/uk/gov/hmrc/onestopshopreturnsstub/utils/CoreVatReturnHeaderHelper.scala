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

package uk.gov.hmrc.onestopshopreturnsstub.utils

import play.api.http.HeaderNames._
import play.api.http.MimeTypes

import java.time.ZoneId
import java.time.format.{DateTimeFormatter, DateTimeParseException}
import java.util.Locale
import scala.concurrent.{ExecutionContext, Future}

case object CoreVatReturnHeaderHelper {

  private val X_CORRELATION_ID = "X-Correlation-ID"
  private final lazy val correlationIdRegex = "^[0-9a-fA-F]{8}[-][0-9a-fA-F]{4}[-][0-9a-fA-F]{4}[-][0-9a-fA-F]{4}[-][0-9a-fA-F]{12}$"
  private val dateTimeFormatter = DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm:ss z")
    .withLocale(Locale.UK)
    .withZone(ZoneId.of("GMT"))

  private def validateCorrelationId(headers: Seq[(String, String)]) = {
    headers.find(_._1 == X_CORRELATION_ID).exists(_._2.matches(correlationIdRegex))
  }

  private def validateContentType(headers: Seq[(String, String)]) = {
    headers.find(_._1 == CONTENT_TYPE).exists(_._2 == MimeTypes.JSON)
  }

  private def validateAccept(headers: Seq[(String, String)]) = {
    headers.find(_._1 == ACCEPT).exists(_._2 == MimeTypes.JSON)
  }

  private def validateDate(headers: Seq[(String, String)]) = {
    val dateHeader = headers.find(_._1 == DATE)
    try {
      if (dateHeader.isDefined) {
        dateTimeFormatter.parse(dateHeader.get._2)
        true
      } else {
        false
      }
    }
    catch {
      case _: DateTimeParseException => false
    }
  }

  private def validateHost(headers:Seq[(String, String)]) = {
    headers.exists(_._1 == X_FORWARDED_HOST)
  }

  private def validateAuthorisation(headers: Seq[(String, String)]) = {
    headers.exists(_._1 == AUTHORIZATION)
  }

  def validateHeaders(headers: Seq[(String, String)]): Boolean = {
    validateHost(headers)&&
      validateDate(headers) &&
      validateAccept(headers) &&
      validateAuthorisation(headers) &&
      validateContentType(headers) &&
      validateCorrelationId(headers)
  }

}
