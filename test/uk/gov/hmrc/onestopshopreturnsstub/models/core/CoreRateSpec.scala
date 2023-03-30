/*
 * Copyright 2023 HM Revenue & Customs
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

import java.time.LocalDate

class CoreRateSpec extends AnyFreeSpec with Matchers  {

  "Core Rate" - {
    "should validate" in {

      val json = """{
                   |  "publishedDate" : "2022-03-10",
                   |  "rate" : 0.5
                   |}""".stripMargin

      val validated: JsResult[CoreRate] = Json.parse(json).validate[CoreRate]

      val expected: CoreRate = CoreRate(LocalDate.of(2022, 3, 10), BigDecimal(0.5))

      validated.isSuccess shouldBe true
      validated.get shouldBe expected
    }

    "should fail for date in unexpected format" in {

      val json = """{
                   |  "publishedDate" : "2022-03-10T10:01:10.123Z",
                   |  "rate" : 0.5
                   |}""".stripMargin

      val validated: JsResult[CoreRate] = Json.parse(json).validate[CoreRate]

      val either = validated.asEither

      either.isLeft shouldBe true
      val error = either.left.toOption.get.head

      error._1 shouldBe JsPath \ ("publishedDate")
      error._2 shouldBe Seq(JsonValidationError("Not a valid date with format yyyy-MM-dd"))
    }

    "should fail for invalid date" in {

      val json = """{
                   |  "publishedDate" : "2022-03-34",
                   |  "rate" : 0.5
                   |}""".stripMargin

      val validated: JsResult[CoreRate] = Json.parse(json).validate[CoreRate]

      val either = validated.asEither

      either.isLeft shouldBe true
      val error = either.left.toOption.get.head

      error._1 shouldBe JsPath \ ("publishedDate")
      error._2 shouldBe Seq(JsonValidationError("Not a valid date with format yyyy-MM-dd"))
    }
  }
}
