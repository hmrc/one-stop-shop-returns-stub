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

import play.api.libs.json.{OFormat, OWrites, Reads, __}

import java.time.{Instant, LocalDate, LocalDateTime}
import uk.gov.hmrc.mongo.play.json.formats.MongoJavatimeFormats


case class CoreRate(publishedDate: LocalDate, rate: BigDecimal)

object CoreRate {

  val reads: Reads[CoreRate] = {

    import play.api.libs.functional.syntax._

    (
      (__ \ "publishedDate").read[LocalDate] and
        (__ \ "rate").read[BigDecimal]
      ) (CoreRate.apply _)
  }

  val writes: OWrites[CoreRate] = {

    import play.api.libs.functional.syntax._

    (
      (__ \ "publishedDate").write[LocalDate] and
        (__ \ "rate").write[BigDecimal]
      ) (unlift(CoreRate.unapply))
  }

  implicit val format: OFormat[CoreRate] = OFormat(reads, writes)

}