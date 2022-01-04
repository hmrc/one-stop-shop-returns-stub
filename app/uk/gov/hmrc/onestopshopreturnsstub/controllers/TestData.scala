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

import uk.gov.hmrc.onestopshopreturnsstub.models.{FinancialTransaction, Item, Period}
import uk.gov.hmrc.onestopshopreturnsstub.models.Quarter.{Q1, Q2, Q3, Q4}

object TestData {

  val period = Period(2021, Q3)
  val period2 = Period(2021, Q4)
  val period3 = Period(2022, Q1)
  val period4 = Period(2022, Q2)
  val period5 = Period(2022, Q3)
  val period6 = Period(2022, Q4)
  val items = Seq(
    Item(
      amount = Some(BigDecimal(1000)),
      clearingReason = Some("01"),
      paymentReference = Some("a"),
      paymentAmount = Some(BigDecimal(500)),
      paymentMethod = Some("A")
    )
  )

  val financialTransactions = Seq(
    FinancialTransaction(
      chargeType = Some("G Ret FR EU-OMS"),
      mainType = None,
      taxPeriodFrom = Some(period.firstDay),
      taxPeriodTo = Some(period.lastDay),
      originalAmount = Some(BigDecimal(1500)),
      outstandingAmount = Some(BigDecimal(500)),
      clearedAmount = Some(BigDecimal(1000)),
      items = Some(items)
    ))

  val allPaidItems = Seq(
    Item(
      amount = Some(BigDecimal(1500)),
      clearingReason = Some("01"),
      paymentReference = Some("a"),
      paymentAmount = Some(BigDecimal(1500)),
      paymentMethod = Some("A")
    )
  )

  val allPaidFinancialTransactions = Seq(
    FinancialTransaction(
      chargeType = Some("G Ret FR EU-OMS"),
      mainType = None,
      taxPeriodFrom = Some(period.firstDay),
      taxPeriodTo = Some(period.lastDay),
      originalAmount = Some(BigDecimal(1500)),
      outstandingAmount = Some(BigDecimal(0)),
      clearedAmount = Some(BigDecimal(1500)),
      items = Some(allPaidItems)
    )
  )

  val somePaidItems = Seq(
    Item(
      amount = Some(BigDecimal(500)),
      clearingReason = Some("01"),
      paymentReference = Some("a"),
      paymentAmount = Some(BigDecimal(500)),
      paymentMethod = Some("A")
    )
  )
  val somePaidFinancialTransactions = Seq(
    FinancialTransaction(
      chargeType = Some("G Ret FR EU-OMS"),
      mainType = None,
      taxPeriodFrom = Some(period.firstDay),
      taxPeriodTo = Some(period.lastDay),
      originalAmount = Some(BigDecimal(1500)),
      outstandingAmount = Some(BigDecimal(1000)),
      clearedAmount = Some(BigDecimal(500)),
      items = Some(somePaidItems)
    )
  )

  val multipleItemsNotPaidItems = Seq(
    Item(
      amount = Some(BigDecimal(1000)),
      clearingReason = Some("01"),
      paymentReference = Some("a"),
      paymentAmount = Some(BigDecimal(1000)),
      paymentMethod = Some("A")
    )
  )
  val multipleItemsNotPaidFinancialTransactions = Seq(
    FinancialTransaction(
      chargeType = Some("G Ret FR EU-OMS"),
      mainType = None,
      taxPeriodFrom = Some(period.firstDay),
      taxPeriodTo = Some(period.lastDay),
      originalAmount = Some(BigDecimal(1500)),
      outstandingAmount = Some(BigDecimal(1000)),
      clearedAmount = Some(BigDecimal(500)),
      items = Some(somePaidItems)
    ),
    FinancialTransaction(
      chargeType = Some("G Ret AT EU-OMS"),
      mainType = None,
      taxPeriodFrom = Some(period2.firstDay),
      taxPeriodTo = Some(period2.lastDay),
      originalAmount = Some(BigDecimal(1500)),
      outstandingAmount = Some(BigDecimal(1500)),
      clearedAmount = Some(BigDecimal(0)),
      items = None
    ),
    FinancialTransaction(
      chargeType = Some("G Ret ES EU-OMS"),
      mainType = None,
      taxPeriodFrom = Some(period5.firstDay),
      taxPeriodTo = Some(period5.lastDay),
      originalAmount = Some(BigDecimal(2500.99)),
      outstandingAmount = Some(BigDecimal(2500.99)),
      clearedAmount = Some(BigDecimal(0)),
      items = None
    )
  )

  val multipleItemsAllPaidItems = Seq(
    Item(
      amount = Some(BigDecimal(1000)),
      clearingReason = Some("01"),
      paymentReference = Some("a"),
      paymentAmount = Some(BigDecimal(1000)),
      paymentMethod = Some("A")
    )
  )
  val multipleItemsAllPaidFinancialTransactions = Seq(
    FinancialTransaction(
      chargeType = Some("G Ret FR EU-OMS"),
      mainType = None,
      taxPeriodFrom = Some(period.firstDay),
      taxPeriodTo = Some(period.lastDay),
      originalAmount = Some(BigDecimal(1500)),
      outstandingAmount = Some(BigDecimal(0)),
      clearedAmount = Some(BigDecimal(1500)),
      items = Some(allPaidItems)
    ),
    FinancialTransaction(
      chargeType = Some("G Ret AT EU-OMS"),
      mainType = None,
      taxPeriodFrom = Some(period2.firstDay),
      taxPeriodTo = Some(period2.lastDay),
      originalAmount = Some(BigDecimal(1500)),
      outstandingAmount = Some(BigDecimal(0)),
      clearedAmount = Some(BigDecimal(1500)),
      items = Some(allPaidItems)
    ),
    FinancialTransaction(
      chargeType = Some("G Ret ES EU-OMS"),
      mainType = None,
      taxPeriodFrom = Some(period5.firstDay),
      taxPeriodTo = Some(period5.lastDay),
      originalAmount = Some(BigDecimal(2500.99)),
      outstandingAmount = Some(BigDecimal(2500.99)),
      clearedAmount = Some(BigDecimal(0)),
      items = Some(allPaidItems)
    )
  )

  val multipleItemsForSamePeriodSomePaidItems = Seq(
    Item(
      amount = Some(BigDecimal(2500)),
      clearingReason = Some("01"),
      paymentReference = Some("a"),
      paymentAmount = Some(BigDecimal(2500)),
      paymentMethod = Some("A")
    )
  )
  val multipleItemsForSamePeriodSomePaidFinancialTransactions = Seq(
    FinancialTransaction(
      chargeType = Some("G Ret FR EU-OMS"),
      mainType = None,
      taxPeriodFrom = Some(period.firstDay),
      taxPeriodTo = Some(period.lastDay),
      originalAmount = Some(BigDecimal(1500)),
      outstandingAmount = Some(BigDecimal(0)),
      clearedAmount = Some(BigDecimal(1500)),
      items = Some(allPaidItems)
    ),
    FinancialTransaction(
      chargeType = Some("G Ret ET EU-OMS"),
      mainType = None,
      taxPeriodFrom = Some(period.firstDay),
      taxPeriodTo = Some(period.lastDay),
      originalAmount = Some(BigDecimal(2500)),
      outstandingAmount = Some(BigDecimal(0)),
      clearedAmount = Some(BigDecimal(2500)),
      items = Some(multipleItemsForSamePeriodSomePaidItems)
    ),
    FinancialTransaction(
      chargeType = Some("G Ret AT EU-OMS"),
      mainType = None,
      taxPeriodFrom = Some(period2.firstDay),
      taxPeriodTo = Some(period2.lastDay),
      originalAmount = Some(BigDecimal(1500)),
      outstandingAmount = Some(BigDecimal(0)),
      clearedAmount = Some(BigDecimal(1500)),
      items = Some(allPaidItems)
    ),
    FinancialTransaction(
      chargeType = Some("G Ret ES EU-OMS"),
      mainType = None,
      taxPeriodFrom = Some(period5.firstDay),
      taxPeriodTo = Some(period5.lastDay),
      originalAmount = Some(BigDecimal(2500.99)),
      outstandingAmount = Some(BigDecimal(2500.99)),
      clearedAmount = Some(BigDecimal(0)),
      items = None
    )
  )

  val notPaidFinancialTransactions = Seq(
    FinancialTransaction(
      chargeType = Some("G Ret FR EU-OMS"),
      mainType = None,
      taxPeriodFrom = Some(period.firstDay),
      taxPeriodTo = Some(period.lastDay),
      originalAmount = Some(BigDecimal(1500)),
      outstandingAmount = Some(BigDecimal(1500)),
      clearedAmount = Some(BigDecimal(0)),
      items = None
    )
  )

}
