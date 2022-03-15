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

package uk.gov.hmrc.onestopshopreturnsstub.models.core

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import play.api.libs.json.{JsPath, JsResult, Json, JsonValidationError}

import java.time.{Instant, LocalDate, LocalDateTime, ZoneId}

class CoreExchangeRateRequestSpec extends AnyFreeSpec with Matchers  {

  "Core Exchange Rate Request" - {

    "should validate" in {

      val json = """{
                   |  "base" : "EUR",
                   |  "target" : "GBP",
                   |  "timestamp" : "2022-03-10T01:10:01.032Z",
                   |  "rates" : [
                   |  {
                   |    "publishedDate" : "2022-03-10",
                   |    "rate" : 0.4
                   |  },
                   |  {
                   |    "publishedDate" : "2022-03-09",
                   |    "rate" : 0.5
                   |  },
                   |  {
                   |    "publishedDate" : "2022-03-08",
                   |    "rate" : 0.6
                   |  } ]
                   |}""".stripMargin

      val validated: JsResult[CoreExchangeRateRequest] = Json.parse(json).validate[CoreExchangeRateRequest]

      val expected = CoreExchangeRateRequest(
        "EUR",
        "GBP",
        LocalDateTime.ofInstant(Instant.parse("2022-03-10T01:10:01.032Z"), ZoneId.systemDefault()),
        Seq(
          CoreRate(LocalDate.of(2022, 3, 10), BigDecimal(0.4)),
          CoreRate(LocalDate.of(2022, 3, 9), BigDecimal(0.5)),
          CoreRate(LocalDate.of(2022, 3, 8), BigDecimal(0.6))
      ))

      validated.isSuccess shouldBe true
      validated.get shouldBe expected
    }

    "should fail for timestamp without milliseconds" in {

      val json = """{
                   |  "base" : "EUR",
                   |  "target" : "GBP",
                   |  "timestamp" : "2022-03-10T01:10:01Z",
                   |  "rates" : [
                   |  {
                   |    "publishedDate" : "2022-03-10",
                   |    "rate" : 0.4
                   |  },
                   |  {
                   |    "publishedDate" : "2022-03-09",
                   |    "rate" : 0.5
                   |  },
                   |  {
                   |    "publishedDate" : "2022-03-08",
                   |    "rate" : 0.6
                   |  } ]
                   |}""".stripMargin

      val validated: JsResult[CoreExchangeRateRequest] = Json.parse(json).validate[CoreExchangeRateRequest]

      val either = validated.asEither

      either.isLeft shouldBe true
      val error = either.left.get.head

      error._1 shouldBe JsPath \ ("timestamp")
      error._2 shouldBe Seq(JsonValidationError("Not a valid date-time of format yyyy-MM-ddThh:mm:ss.SSSZ"))
    }

    "should fail for currencies that are not 3 characters" in {

      val json = """{
                   |  "base" : "EU",
                   |  "target" : "GBP",
                   |  "timestamp" : "2022-03-10T01:10:01.123Z",
                   |  "rates" : [
                   |  {
                   |    "publishedDate" : "2022-03-10",
                   |    "rate" : 0.4
                   |  },
                   |  {
                   |    "publishedDate" : "2022-03-09",
                   |    "rate" : 0.5
                   |  },
                   |  {
                   |    "publishedDate" : "2022-03-08",
                   |    "rate" : 0.6
                   |  } ]
                   |}""".stripMargin

      val validated: JsResult[CoreExchangeRateRequest] = Json.parse(json).validate[CoreExchangeRateRequest]

      val either = validated.asEither

      either.isLeft shouldBe true
      val error = either.left.get.head

      error._1 shouldBe JsPath \ ("base")
      error._2 shouldBe Seq(JsonValidationError("error.minLength", 3))
    }

    "handle multiple errors" in {

      val json = """{
                   |  "base" : "EUR",
                   |  "target" : "GB",
                   |  "timestamp" : "2022-03-10",
                   |  "rates" : [
                   |  {
                   |    "publishedDate" : "2022-03-10T01:10:01.123Z",
                   |    "rate" : 0.4
                   |  },
                   |  {
                   |    "publishedDate" : "2022-03-09",
                   |    "rate" : 0.5
                   |  },
                   |  {
                   |    "publishedDate" : "2022-03-08",
                   |    "rate" : 0.6
                   |  } ]
                   |}""".stripMargin

      val validated: JsResult[CoreExchangeRateRequest] = Json.parse(json).validate[CoreExchangeRateRequest]

      val either = validated.asEither

      either.isLeft shouldBe true
      val errors = either.left.get

      errors.head._1 shouldBe JsPath \ "target"
      errors.head._2 shouldBe Seq(JsonValidationError("error.minLength", 3))

      errors(1)._1 shouldBe JsPath \ "timestamp"
      errors(1)._2 shouldBe Seq(JsonValidationError("Not a valid date-time of format yyyy-MM-ddThh:mm:ss.SSSZ"))

      errors(2)._1 shouldBe JsPath \ "rates" \ 0 \ "publishedDate"
      errors(2)._2 shouldBe Seq(JsonValidationError("Not a valid date with format yyyy-MM-dd"))
    }
  }
}
