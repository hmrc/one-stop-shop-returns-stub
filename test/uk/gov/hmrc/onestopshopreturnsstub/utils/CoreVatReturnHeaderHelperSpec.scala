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

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers.convertToAnyMustWrapper
import org.scalatest.matchers.should.Matchers
import play.api.http.HeaderNames.{ACCEPT, AUTHORIZATION, CONTENT_TYPE, DATE}
import uk.gov.hmrc.onestopshopreturnsstub.utils.CoreVatReturnHeaderHelper
class CoreVatReturnHeaderHelperSpec extends AnyFreeSpec with ScalaFutures with Matchers {

  "CoreVatReturnHeaderHelper#" - {

    "return true if all required headers are present" in {
      val headers: Seq[(String, String)] = Seq(
        (AUTHORIZATION, ""),
        (ACCEPT, ""),
        ("x-correlation-id", ""),
        ("X-Forwarded-Host", ""),
        (CONTENT_TYPE, ""),
        (DATE, ""))

      CoreVatReturnHeaderHelper.validateHeaders(headers) mustBe true

    }

    "return false if not all required headers are present" in {
      val headers: Seq[(String, String)] = Seq(
        (AUTHORIZATION, ""),
        (ACCEPT, ""),
        ("x-correlation-id", ""),
        (CONTENT_TYPE, ""),
        (DATE, ""))

      CoreVatReturnHeaderHelper.validateHeaders(headers) mustBe false
    }

    "return false if no headers are present" in {
      val headers: Seq[(String, String)] = Seq.empty

      CoreVatReturnHeaderHelper.validateHeaders(headers) mustBe false
    }
  }

}
