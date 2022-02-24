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

case object CoreVatReturnHeaderHelper {
  type HeaderValidationResult = Either[HeaderError, Unit]
  private val X_CORRELATION_ID = "X-Correlation-ID"
  private final lazy val correlationIdRegex = "^[0-9a-fA-F]{8}[-][0-9a-fA-F]{4}[-][0-9a-fA-F]{4}[-][0-9a-fA-F]{4}[-][0-9a-fA-F]{12}$"
  private val dateTimeFormatter = DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm:ss z")
    .withLocale(Locale.UK)
    .withZone(ZoneId.of("GMT"))

  private def validateCorrelationId(headers: Seq[(String, String)]): HeaderValidationResult = {
    headers.find(_._1 == X_CORRELATION_ID)
      .map(correlation =>
        if(correlation._2.matches(correlationIdRegex)) Right()
        else Left(InvalidHeader(X_CORRELATION_ID))
    ).getOrElse(Left(MissingHeader(X_CORRELATION_ID)))
  }

  private def validateContentType(headers: Seq[(String, String)]): HeaderValidationResult = {
    headers.find(_._1 == CONTENT_TYPE)
      .map(content =>
        if(content._2 == MimeTypes.JSON) Right()
        else Left(InvalidHeader(CONTENT_TYPE))
      ).getOrElse(Left(MissingHeader(CONTENT_TYPE)))
  }

  private def validateAccept(headers: Seq[(String, String)]): HeaderValidationResult = {
    headers.find(_._1 == ACCEPT)
      .map(
        accept =>
          if(accept._2 == MimeTypes.JSON) Right()
          else Left(InvalidHeader(ACCEPT))
      ).getOrElse(Left(MissingHeader(ACCEPT)))
  }

  private def validateDate(headers: Seq[(String, String)]): HeaderValidationResult = {
    val dateHeader = headers.find(_._1 == DATE)
    try {
      if (dateHeader.isDefined) {
        dateTimeFormatter.parse(dateHeader.get._2)
        Right()
      } else {
        Left(MissingHeader(DATE))
      }
    }
    catch {
      case _: DateTimeParseException => Left(InvalidHeader(DATE))
    }
  }

  private def validateHost(headers:Seq[(String, String)]): HeaderValidationResult = {
    if(headers.exists(_._1 == X_FORWARDED_HOST)) Right()
    else Left(MissingHeader(X_FORWARDED_HOST))
  }

  private def validateAuthorisation(headers: Seq[(String, String)]): HeaderValidationResult = {
    if(headers.exists(_._1 == AUTHORIZATION)) Right()
    else Left(MissingHeader(AUTHORIZATION))
  }

  def validateHeaders(headers: Seq[(String, String)]): Either[HeaderError, Unit] = {
    val results = Seq(validateHost(headers),
      validateDate(headers),
      validateAccept(headers),
      validateAuthorisation(headers),
      validateContentType(headers),
      validateCorrelationId(headers))
    val invalidResults = results.filter(_.isLeft)
    if(invalidResults.isEmpty) Right()
    else {
      invalidResults.find {
        case Left(MissingHeader(_)) => true
        case _ => false
      }.getOrElse(
        invalidResults.find {
          case Left(InvalidHeader(_)) => true
          case _ => false
        }.getOrElse(Left(UnknownError()))
      )

    }
  }

}



trait HeaderError
case class MissingHeader(header: String) extends HeaderError
case class InvalidHeader(header: String) extends HeaderError
case class UnknownError() extends HeaderError