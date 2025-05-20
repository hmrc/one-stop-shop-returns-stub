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

import uk.gov.hmrc.onestopshopreturnsstub.models.Quarter.{Q1, Q2, Q3, Q4}
import uk.gov.hmrc.onestopshopreturnsstub.models.etmp._
import uk.gov.hmrc.onestopshopreturnsstub.models.{FinancialTransaction, Item, Period}

import java.time.{LocalDate, LocalDateTime}

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

  val lastYear = LocalDate.now().minusYears(1).toString.substring(2, 4)
  val fourYearsAgo = LocalDate.now().minusYears(4).toString.substring(2, 4)
  val threeYearsAgo = LocalDate.now().minusYears(3).toString.substring(2, 4)
  val twoYearsAgo = LocalDate.now().minusYears(2).toString.substring(2, 4)

  val singleOutstandingPayment = Seq(
    FinancialTransaction(
      chargeType = Some("G Ret FR EU-OMS"),
      mainType = None,
      taxPeriodFrom = Some(period.firstDay),
      taxPeriodTo = Some(period.lastDay),
      originalAmount = Some(BigDecimal(9703.13)),
      outstandingAmount = Some(BigDecimal(9703.13)),
      clearedAmount = Some(BigDecimal(0)),
      items = Some(items)
    )
  )

  val onePaidOnePartialOneOutstanding = Seq(
    FinancialTransaction(
      chargeType = Some("G Ret FR EU-OMS"),
      mainType = None,
      taxPeriodFrom = Some(period.firstDay),
      taxPeriodTo = Some(period.lastDay),
      originalAmount = Some(BigDecimal(8703.13)),
      outstandingAmount = Some(BigDecimal(0)),
      clearedAmount = Some(BigDecimal(8703.13)),
      items = Some(items)
    ),
    FinancialTransaction(
      chargeType = Some("G Ret FR EU-OMS"),
      mainType = None,
      taxPeriodFrom = Some(period2.firstDay),
      taxPeriodTo = Some(period2.lastDay),
      originalAmount = Some(BigDecimal(6500.50)),
      outstandingAmount = Some(BigDecimal(5500.50)),
      clearedAmount = Some(BigDecimal(1000.00)),
      items = Some(items)
    ),
    FinancialTransaction(
      chargeType = Some("G Ret FR EU-OMS"),
      mainType = None,
      taxPeriodFrom = Some(period3.firstDay),
      taxPeriodTo = Some(period3.lastDay),
      originalAmount = Some(BigDecimal(3611.10)),
      outstandingAmount = Some(BigDecimal(3611.10)),
      clearedAmount = Some(BigDecimal(0)),
      items = Some(items)
    )
  )

  val twoOutstandingPayments = Seq(
    FinancialTransaction(
      chargeType = Some("G Ret FR EU-OMS"),
      mainType = None,
      taxPeriodFrom = Some(Period(2023, Q1).firstDay),
      taxPeriodTo = Some(Period(2023, Q1).lastDay),
      originalAmount = Some(BigDecimal(2525.25)),
      outstandingAmount = Some(BigDecimal(2525.25)),
      clearedAmount = Some(BigDecimal(0)),
      items = Some(items)
    ),
    FinancialTransaction(
      chargeType = Some("G Ret FR EU-OMS"),
      mainType = None,
      taxPeriodFrom = Some(Period(2023, Q2).firstDay),
      taxPeriodTo = Some(Period(2023, Q2).lastDay),
      originalAmount = Some(BigDecimal(6000.50)),
      outstandingAmount = Some(BigDecimal(6000.50)),
      clearedAmount = Some(BigDecimal(0)),
      items = Some(items)
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

  val obligationDetails: EtmpObligations = EtmpObligations(obligations = Seq(EtmpObligation(
    obligationDetails = Seq(
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = "22C1"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = "22C2"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Open,
        periodKey = "22C3"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Open,
        periodKey = "21C3"
      )
    )
  )))

  val oneFulfilledObligationDetails: EtmpObligations = EtmpObligations(obligations = Seq(EtmpObligation(
    obligationDetails = Seq(
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = s"${twoYearsAgo}C3"
      )
    )
  )))

  val threeFulfilledObligationDetails: EtmpObligations = EtmpObligations(obligations = Seq(EtmpObligation(
    obligationDetails = Seq(
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = s"${twoYearsAgo}C3"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = s"${twoYearsAgo}C4"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = s"${lastYear}C1"
      )
    )
  )))

  val twoFulfilledObligationDetails2023: EtmpObligations = EtmpObligations(obligations = Seq(EtmpObligation(
    obligationDetails = Seq(
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = "23C1"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = "23C2"
      )
    )
  )))

  val twoFulfilledObligationDetails2022: EtmpObligations = EtmpObligations(obligations = Seq(EtmpObligation(
    obligationDetails = Seq(
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = s"${lastYear}C1"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = s"${lastYear}C2"
      )
    )
  )))

  val twoOpenObligationsExcluded2023: EtmpObligations = EtmpObligations(obligations = Seq(EtmpObligation(
    obligationDetails = Seq(
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Open,
        periodKey = "23C1"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Open,
        periodKey = "23C2"
      )
    )
  )))

  val twoOpenObligationsExcluded: EtmpObligations = EtmpObligations(obligations = Seq(EtmpObligation(
    obligationDetails = Seq(
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Open,
        periodKey = s"${lastYear}C1"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Open,
        periodKey = s"${lastYear}C2"
      )
    )
  )))

  val openObligationsOverThreeYearsAgoExpiredVRN: EtmpObligations = EtmpObligations(obligations = Seq(EtmpObligation(
    obligationDetails = Seq(
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Open,
        periodKey = s"${fourYearsAgo}C1"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Open,
        periodKey = s"${fourYearsAgo}C2"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Open,
        periodKey = s"${fourYearsAgo}C3"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Open,
        periodKey = s"${fourYearsAgo}C4"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Open,
        periodKey = s"${threeYearsAgo}C1"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Open,
        periodKey = s"${threeYearsAgo}C2"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Open,
        periodKey = s"${threeYearsAgo}C3"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Open,
        periodKey = s"${threeYearsAgo}C4"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Open,
        periodKey = s"${twoYearsAgo}C1"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Open,
        periodKey = s"${twoYearsAgo}C2"
      ),
    )
  )))

  val fulfilledObligationsOverThreeYearsAgo: EtmpObligations = EtmpObligations(obligations = Seq(EtmpObligation(
    obligationDetails = Seq(
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = s"${fourYearsAgo}C1"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = s"${fourYearsAgo}C2"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = s"${fourYearsAgo}C3"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = s"${fourYearsAgo}C4"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = s"${threeYearsAgo}C1"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = s"${threeYearsAgo}C2"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = s"${threeYearsAgo}C3"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = s"${threeYearsAgo}C4"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = s"${twoYearsAgo}C1"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = s"${twoYearsAgo}C2"
      ),
    )
  )))

  val oneOpenObligationExcluded: EtmpObligations = EtmpObligations(obligations = Seq(EtmpObligation(
    obligationDetails = Seq(
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Open,
        periodKey = s"${lastYear}C1"
      )
    )
  )))

  val oneOpenObligationExcludedFuture: EtmpObligations = EtmpObligations(obligations = Seq(EtmpObligation(
    obligationDetails = Seq(
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Open,
        periodKey = "24C1"
      )
    )
  )))

  val oneFulfilledObligationQuarantinedOverTwoYears: EtmpObligations = EtmpObligations(obligations = Seq(EtmpObligation(
    obligationDetails = Seq(
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = "22C2"
      )
    )
  )))

  val oneOpenObligationQuarantined: EtmpObligations = EtmpObligations(obligations = Seq(EtmpObligation(
    obligationDetails = Seq(
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Open,
        periodKey = "24C2"
      )
    )
  )))

  val multipleOpenObligationsExcluded: EtmpObligations = EtmpObligations(obligations = Seq(EtmpObligation(
    obligationDetails = Seq(
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Open,
        periodKey = "21C4"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Open,
        periodKey = "22C1"
      )
    )
  )))

  val oneFulfilledObligationExcluded: EtmpObligations = EtmpObligations(obligations = Seq(EtmpObligation(
    obligationDetails = Seq(
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = s"${lastYear}C1"
      )
    )
  )))

  val oneFulfilledObligationExcluded2024: EtmpObligations = EtmpObligations(obligations = Seq(EtmpObligation(
    obligationDetails = Seq(
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = "24C1"
      )
    )
  )))

  val oneOpenObligationExcluded2024: EtmpObligations = EtmpObligations(obligations = Seq(EtmpObligation(
    obligationDetails = Seq(
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Open,
        periodKey = "24C1"
      )
    )
  )))

  val firstPeriodNoCorrections: EtmpObligations = EtmpObligations(obligations = Seq(EtmpObligation(
    obligationDetails = Seq(
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Open,
        periodKey = "21C3"
      )
    )
  )))

  val firstPeriodNoCorrections2023: EtmpObligations = EtmpObligations(obligations = Seq(EtmpObligation(
    obligationDetails = Seq(
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Open,
        periodKey = "23C2"
      )
    )
  )))

  val secondOpenPeriodPartialReturns2023: EtmpObligations = EtmpObligations(obligations = Seq(EtmpObligation(
    obligationDetails = Seq(
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = "23C2"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Open,
        periodKey = "23C3"
      )
    )
  )))

  val fulfilledPeriodsPartialReturns2023: EtmpObligations = EtmpObligations(obligations = Seq(EtmpObligation(
    obligationDetails = Seq(
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = "23C2"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = "23C3"
      )
    )
  )))


  val fulfilledObligationOver6YearsAgo: EtmpObligations = EtmpObligations(obligations = Seq(EtmpObligation(
    obligationDetails = Seq(
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = "18C1"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = "18C2"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = "18C3"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = "18C4"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = "19C1"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = "19C2"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = "19C3"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = "19C4"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = "20C1"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = "20C2"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = "20C3"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = "20C4"
      ),

    )
  )))


  def basicEtmpVatReturn(vrn: String, period: String): EtmpVatReturn = {

    EtmpVatReturn(
      returnReference = generateReference(vrn, period),
      returnVersion = LocalDateTime.of(2024, 1, 2, 0, 0, 0),
      periodKey = period,
      returnPeriodFrom = LocalDate.of(2023, 4, 1),
      returnPeriodTo = LocalDate.of(2023, 12, 31),
      goodsSupplied = Seq.empty,
      totalVATGoodsSuppliedGBP = BigDecimal(0),
      goodsDispatched = Seq(
        EtmpVatReturnGoodsDispatched(
          msOfConsumption = "DE",
          msOfEstablishment = "FR",
          vatRateType = EtmpVatRateType.StandardVatRate,
          taxableAmountGBP = BigDecimal(12345.67),
          vatAmountGBP = BigDecimal(1000.00)
        ),
        EtmpVatReturnGoodsDispatched(
          msOfConsumption = "FR",
          msOfEstablishment = "ES",
          vatRateType = EtmpVatRateType.ReducedVatRate,
          taxableAmountGBP = BigDecimal(23973.03),
          vatAmountGBP = BigDecimal(1000.00)
        ),
      ),
      totalVATAmountPayable = BigDecimal(2000.00),
      totalVATAmountPayableAllSpplied = BigDecimal(2000.00),
      correctionPreviousVATReturn = Seq.empty,
      totalVATAmountFromCorrectionGBP = BigDecimal(0.00),
      balanceOfVATDueForMS = Seq(
        EtmpVatReturnBalanceOfVatDue(
          msOfConsumption = "DE",
          totalVATDueGBP = BigDecimal(1000.00)
        ),
        EtmpVatReturnBalanceOfVatDue(
          msOfConsumption = "FR",
          totalVATDueGBP = BigDecimal(1000.00)
        )
      ),
      totalVATAmountDueForAllMSGBP = BigDecimal(2000.00),
      paymentReference = generateReference(vrn, period)
    )
  }

  def etmpVatReturnWithCorrections(vrn: String, period: String): EtmpVatReturn = {

    EtmpVatReturn(
      returnReference = generateReference(vrn, period),
      returnVersion = LocalDateTime.of(2024, 1, 2, 0, 0, 0),
      periodKey = period,
      returnPeriodFrom = LocalDate.of(2023, 12, 1),
      returnPeriodTo = LocalDate.of(2023, 12, 31),
      goodsSupplied = Seq(
        EtmpVatReturnGoodsSupplied(
          msOfConsumption = "FR",
          vatRateType = EtmpVatRateType.StandardVatRate,
          taxableAmountGBP = BigDecimal(12345.67),
          vatAmountGBP = BigDecimal(1000.00)
        ),
        EtmpVatReturnGoodsSupplied(
          msOfConsumption = "ES",
          vatRateType = EtmpVatRateType.ReducedVatRate,
          taxableAmountGBP = BigDecimal(23973.03),
          vatAmountGBP = BigDecimal(1000.00)
        )
      ),
      totalVATGoodsSuppliedGBP = BigDecimal(2000.00),
      goodsDispatched = Seq(
        EtmpVatReturnGoodsDispatched(
          msOfConsumption = "DE",
          msOfEstablishment = "MT",
          vatRateType = EtmpVatRateType.StandardVatRate,
          taxableAmountGBP = BigDecimal(12345.67),
          vatAmountGBP = BigDecimal(1000.00)
        ),
        EtmpVatReturnGoodsDispatched(
          msOfConsumption = "DE",
          msOfEstablishment = "AT",
          vatRateType = EtmpVatRateType.ReducedVatRate,
          taxableAmountGBP = BigDecimal(5231.03),
          vatAmountGBP = BigDecimal(1565.46)
        ),
        EtmpVatReturnGoodsDispatched(
          msOfConsumption = "LT",
          msOfEstablishment = "LV",
          vatRateType = EtmpVatRateType.ReducedVatRate,
          taxableAmountGBP = BigDecimal(5231.03),
          vatAmountGBP = BigDecimal(1565.46)
        )
      ),
      totalVATAmountPayable = BigDecimal(2000.00),
      totalVATAmountPayableAllSpplied = BigDecimal(2000.00),
      correctionPreviousVATReturn = Seq(
        EtmpVatReturnCorrection(
          periodKey = "21Q3",
          periodFrom = LocalDate.of(2021, 7, 1).toString,
          periodTo = LocalDate.of(2021, 9, 30).toString,
          msOfConsumption = "DE",
          totalVATAmountCorrectionGBP = BigDecimal(-1500.00)
        ),
        EtmpVatReturnCorrection(
          periodKey = "21Q3",
          periodFrom = LocalDate.of(2021, 7, 1).toString,
          periodTo = LocalDate.of(2021, 9, 30).toString,
          msOfConsumption = "FR",
          totalVATAmountCorrectionGBP = BigDecimal(-3500.00)
        ),
        EtmpVatReturnCorrection(
          periodKey = "21Q4",
          periodFrom = LocalDate.of(2021, 10, 1).toString,
          periodTo = LocalDate.of(2021, 12, 31).toString,
          msOfConsumption = "ES",
          totalVATAmountCorrectionGBP = BigDecimal(-2500.00)
        ),
        EtmpVatReturnCorrection(
          periodKey = "21Q4",
          periodFrom = LocalDate.of(2021, 10, 1).toString,
          periodTo = LocalDate.of(2021, 12, 31).toString,
          msOfConsumption = "LV",
          totalVATAmountCorrectionGBP = BigDecimal(-1800.00)
        ),
        EtmpVatReturnCorrection(
          periodKey = "22Q1",
          periodFrom = LocalDate.of(2022, 1, 1).toString,
          periodTo = LocalDate.of(2021, 3, 31).toString,
          msOfConsumption = "LT",
          totalVATAmountCorrectionGBP = BigDecimal(-1800.00)
        )
      ),
      totalVATAmountFromCorrectionGBP = BigDecimal(0.00),
      balanceOfVATDueForMS = Seq(
        EtmpVatReturnBalanceOfVatDue(
          msOfConsumption = "DE",
          totalVATDueGBP = BigDecimal(1000.00)
        ),
        EtmpVatReturnBalanceOfVatDue(
          msOfConsumption = "FR",
          totalVATDueGBP = BigDecimal(1000.00)
        )
      ),
      totalVATAmountDueForAllMSGBP = BigDecimal(2000.00),
      paymentReference = generateReference(vrn, period)
    )
  }

  def etmpVatReturnQ1(vrn: String, period: String): EtmpVatReturn = {

    EtmpVatReturn(
      returnReference = generateReference(vrn, period),
      returnVersion = LocalDateTime.of(2024, 1, 2, 0, 0, 0),
      periodKey = period,
      returnPeriodFrom = LocalDate.of(2021, 7, 1),
      returnPeriodTo = LocalDate.of(2021, 9, 30),
      goodsSupplied = Seq(
        EtmpVatReturnGoodsSupplied(
          msOfConsumption = "FR",
          vatRateType = EtmpVatRateType.StandardVatRate,
          taxableAmountGBP = BigDecimal(12345.67),
          vatAmountGBP = BigDecimal(1500.00)
        )
      ),
      totalVATGoodsSuppliedGBP = BigDecimal(1500.00),
      goodsDispatched = Seq(
        EtmpVatReturnGoodsDispatched(
          msOfConsumption = "DK",
          msOfEstablishment = "FR",
          vatRateType = EtmpVatRateType.StandardVatRate,
          taxableAmountGBP = BigDecimal(12345.67),
          vatAmountGBP = BigDecimal(4521.20)
        )
      ),
      totalVATAmountPayable = BigDecimal(6021.20),
      totalVATAmountPayableAllSpplied = BigDecimal(6021.20),
      correctionPreviousVATReturn = Seq.empty,
      totalVATAmountFromCorrectionGBP = BigDecimal(0.00),
      balanceOfVATDueForMS = Seq(
        EtmpVatReturnBalanceOfVatDue(
          msOfConsumption = "DK",
          totalVATDueGBP = BigDecimal(4521.20)
        ),
        EtmpVatReturnBalanceOfVatDue(
          msOfConsumption = "FR",
          totalVATDueGBP = BigDecimal(1500.00)
        )
      ),
      totalVATAmountDueForAllMSGBP = BigDecimal(8703.13),
      paymentReference = generateReference(vrn, period)
    )
  }

  def etmpVatReturnQ2(vrn: String, period: String): EtmpVatReturn = {

    EtmpVatReturn(
      returnReference = generateReference(vrn, period),
      returnVersion = LocalDateTime.of(2024, 1, 2, 0, 0, 0),
      periodKey = period,
      returnPeriodFrom = LocalDate.of(2021, 10, 1),
      returnPeriodTo = LocalDate.of(2021, 12, 31),
      goodsSupplied = Seq(
        EtmpVatReturnGoodsSupplied(
          msOfConsumption = "FR",
          vatRateType = EtmpVatRateType.StandardVatRate,
          taxableAmountGBP = BigDecimal(12345.67),
          vatAmountGBP = BigDecimal(1000.00)
        ),
        EtmpVatReturnGoodsSupplied(
          msOfConsumption = "AT",
          vatRateType = EtmpVatRateType.ReducedVatRate,
          taxableAmountGBP = BigDecimal(23973.03),
          vatAmountGBP = BigDecimal(1000.00)
        )
      ),
      totalVATGoodsSuppliedGBP = BigDecimal(2000.00),
      goodsDispatched = Seq(
        EtmpVatReturnGoodsDispatched(
          msOfConsumption = "HR",
          msOfEstablishment = "DE",
          vatRateType = EtmpVatRateType.StandardVatRate,
          taxableAmountGBP = BigDecimal(12345.67),
          vatAmountGBP = BigDecimal(1000.00)
        )
      ),
      totalVATAmountPayable = BigDecimal(3000.00),
      totalVATAmountPayableAllSpplied = BigDecimal(3000.00),
      correctionPreviousVATReturn = Seq(
        EtmpVatReturnCorrection(
          periodKey = "21Q3",
          periodFrom = LocalDate.of(2021, 7, 1).toString,
          periodTo = LocalDate.of(2021, 9, 30).toString,
          msOfConsumption = "DK",
          totalVATAmountCorrectionGBP = BigDecimal(-1500.00)
        ),
        EtmpVatReturnCorrection(
          periodKey = "21Q3",
          periodFrom = LocalDate.of(2021, 7, 1).toString,
          periodTo = LocalDate.of(2021, 9, 30).toString,
          msOfConsumption = "FR",
          totalVATAmountCorrectionGBP = BigDecimal(3500.00)
        )
      ),
      totalVATAmountFromCorrectionGBP = BigDecimal(2500.00),
      balanceOfVATDueForMS = Seq(
        EtmpVatReturnBalanceOfVatDue(
          msOfConsumption = "DK",
          totalVATDueGBP = BigDecimal(-1500.00)
        ),
        EtmpVatReturnBalanceOfVatDue(
          msOfConsumption = "FR",
          totalVATDueGBP = BigDecimal(4500.00)
        ),
        EtmpVatReturnBalanceOfVatDue(
          msOfConsumption = "HR",
          totalVATDueGBP = BigDecimal(1000.00)
        ),
        EtmpVatReturnBalanceOfVatDue(
          msOfConsumption = "AT",
          totalVATDueGBP = BigDecimal(1000.00)
        )
      ),
      totalVATAmountDueForAllMSGBP = BigDecimal(6500.00),
      paymentReference = generateReference(vrn, period)
    )
  }

  def etmpVatReturnQ3(vrn: String, period: String): EtmpVatReturn = {

    EtmpVatReturn(
      returnReference = generateReference(vrn, period),
      returnVersion = LocalDateTime.of(2024, 1, 2, 0, 0, 0),
      periodKey = period,
      returnPeriodFrom = LocalDate.of(2022, 1, 1),
      returnPeriodTo = LocalDate.of(2022, 3, 31),
      goodsSupplied = Seq(
        EtmpVatReturnGoodsSupplied(
          msOfConsumption = "PL",
          vatRateType = EtmpVatRateType.StandardVatRate,
          taxableAmountGBP = BigDecimal(12345.67),
          vatAmountGBP = BigDecimal(123.45)
        ),
        EtmpVatReturnGoodsSupplied(
          msOfConsumption = "IE",
          vatRateType = EtmpVatRateType.ReducedVatRate,
          taxableAmountGBP = BigDecimal(23973.03),
          vatAmountGBP = BigDecimal(987.65)
        )
      ),
      totalVATGoodsSuppliedGBP = BigDecimal(1111.10),
      goodsDispatched = Seq(
        EtmpVatReturnGoodsDispatched(
          msOfConsumption = "BE",
          msOfEstablishment = "DE",
          vatRateType = EtmpVatRateType.StandardVatRate,
          taxableAmountGBP = BigDecimal(12345.67),
          vatAmountGBP = BigDecimal(2500.00)
        ),
      ),
      totalVATAmountPayable = BigDecimal(3000.00),
      totalVATAmountPayableAllSpplied = BigDecimal(3000.00),
      correctionPreviousVATReturn = Seq(
        EtmpVatReturnCorrection(
          periodKey = "21Q3",
          periodFrom = LocalDate.of(2021, 7, 1).toString,
          periodTo = LocalDate.of(2021, 9, 30).toString,
          msOfConsumption = "DK",
          totalVATAmountCorrectionGBP = BigDecimal(250.00)
        ),
        EtmpVatReturnCorrection(
          periodKey = "21Q4",
          periodFrom = LocalDate.of(2021, 10, 1).toString,
          periodTo = LocalDate.of(2021, 12, 31).toString,
          msOfConsumption = "HR",
          totalVATAmountCorrectionGBP = BigDecimal(-250.00)
        )
      ),
      totalVATAmountFromCorrectionGBP = BigDecimal(0),
      balanceOfVATDueForMS = Seq(
        EtmpVatReturnBalanceOfVatDue(
          msOfConsumption = "DK",
          totalVATDueGBP = BigDecimal(250.00)
        ),
        EtmpVatReturnBalanceOfVatDue(
          msOfConsumption = "BE",
          totalVATDueGBP = BigDecimal(2500.00)
        ),
        EtmpVatReturnBalanceOfVatDue(
          msOfConsumption = "HR",
          totalVATDueGBP = BigDecimal(-250.00)
        ),
        EtmpVatReturnBalanceOfVatDue(
          msOfConsumption = "PL",
          totalVATDueGBP = BigDecimal(123.45)
        ),
        EtmpVatReturnBalanceOfVatDue(
          msOfConsumption = "IE",
          totalVATDueGBP = BigDecimal(987.65)
        )
      ),
      totalVATAmountDueForAllMSGBP = BigDecimal(3611.10),
      paymentReference = generateReference(vrn, period)
    )
  }

  def etmpVatReturnWithoutCorrections(vrn: String, period: String): EtmpVatReturn = {

    EtmpVatReturn(
      returnReference = generateReference(vrn, period),
      returnVersion = LocalDateTime.of(2024, 1, 2, 0, 0, 0),
      periodKey = period,
      returnPeriodFrom = LocalDate.of(2021, 10, 1),
      returnPeriodTo = LocalDate.of(2021, 12, 31),
      goodsSupplied = Seq(
        EtmpVatReturnGoodsSupplied(
          msOfConsumption = "FR",
          vatRateType = EtmpVatRateType.StandardVatRate,
          taxableAmountGBP = BigDecimal(12345.67),
          vatAmountGBP = BigDecimal(1234.00)
        ),
        EtmpVatReturnGoodsSupplied(
          msOfConsumption = "ES",
          vatRateType = EtmpVatRateType.StandardVatRate,
          taxableAmountGBP = BigDecimal(12345.67),
          vatAmountGBP = BigDecimal(1000.00)
        )
      ),
      totalVATGoodsSuppliedGBP = BigDecimal(2204.00),
      goodsDispatched = Seq(
        EtmpVatReturnGoodsDispatched(
          msOfConsumption = "HR",
          msOfEstablishment = "MT",
          vatRateType = EtmpVatRateType.StandardVatRate,
          taxableAmountGBP = BigDecimal(12345.67),
          vatAmountGBP = BigDecimal(7469.13)
        )
      ),
      totalVATAmountPayable = BigDecimal(9703.13),
      totalVATAmountPayableAllSpplied = BigDecimal(9703.13),
      correctionPreviousVATReturn = Seq.empty,
      totalVATAmountFromCorrectionGBP = BigDecimal(0.00),
      balanceOfVATDueForMS = Seq(
        EtmpVatReturnBalanceOfVatDue(
          msOfConsumption = "HR",
          totalVATDueGBP = BigDecimal(7469.13)
        ),
        EtmpVatReturnBalanceOfVatDue(
          msOfConsumption = "FR",
          totalVATDueGBP = BigDecimal(1234.00)
        ),
        EtmpVatReturnBalanceOfVatDue(
          msOfConsumption = "ES",
          totalVATDueGBP = BigDecimal(1000.00)
        )
      ),
      totalVATAmountDueForAllMSGBP = BigDecimal(9703.13),
      paymentReference = generateReference(vrn, period)
    )
  }

  def etmpVatReturnPartialDates(vrn: String, period: String, returnStartDate: LocalDate, returnEndDate: LocalDate): EtmpVatReturn = {

    EtmpVatReturn(
      returnReference = generateReference(vrn, period),
      returnVersion = LocalDateTime.of(2024, 1, 2, 0, 0, 0),
      periodKey = period,
      returnPeriodFrom = returnStartDate,
      returnPeriodTo = returnEndDate,
      goodsSupplied = Seq(
        EtmpVatReturnGoodsSupplied(
          msOfConsumption = "FR",
          vatRateType = EtmpVatRateType.StandardVatRate,
          taxableAmountGBP = BigDecimal(12345.67),
          vatAmountGBP = BigDecimal(1234.00)
        )
      ),
      totalVATGoodsSuppliedGBP = BigDecimal(1234.00),
      goodsDispatched = Seq(
        EtmpVatReturnGoodsDispatched(
          msOfConsumption = "HR",
          msOfEstablishment = "MT",
          vatRateType = EtmpVatRateType.StandardVatRate,
          taxableAmountGBP = BigDecimal(12345.67),
          vatAmountGBP = BigDecimal(7469.13)
        )
      ),
      totalVATAmountPayable = BigDecimal(8703.13),
      totalVATAmountPayableAllSpplied = BigDecimal(8703.13),
      correctionPreviousVATReturn = Seq.empty,
      totalVATAmountFromCorrectionGBP = BigDecimal(0.00),
      balanceOfVATDueForMS = Seq(
        EtmpVatReturnBalanceOfVatDue(
          msOfConsumption = "HR",
          totalVATDueGBP = BigDecimal(7469.13)
        ),
        EtmpVatReturnBalanceOfVatDue(
          msOfConsumption = "FR",
          totalVATDueGBP = BigDecimal(1234.00)
        )
      ),
      totalVATAmountDueForAllMSGBP = BigDecimal(8703.13),
      paymentReference = generateReference(vrn, period)
    )
  }

  def nilEtmpVatReturn(vrn: String, period: String): EtmpVatReturn = {

    EtmpVatReturn(
      returnReference = generateReference(vrn, period),
      returnVersion = LocalDateTime.of(2024, 1, 2, 0, 0, 0),
      periodKey = "21Q3",
      returnPeriodFrom = LocalDate.of(2021, 7, 1),
      returnPeriodTo = LocalDate.of(2021, 9, 30),
      goodsSupplied = Seq.empty,
      totalVATGoodsSuppliedGBP = BigDecimal(0),
      goodsDispatched = Seq.empty,
      totalVATAmountPayable = BigDecimal(0),
      totalVATAmountPayableAllSpplied = BigDecimal(0),
      correctionPreviousVATReturn = Seq.empty,
      totalVATAmountFromCorrectionGBP = BigDecimal(0),
      balanceOfVATDueForMS = Seq.empty,
      totalVATAmountDueForAllMSGBP = BigDecimal(0),
      paymentReference = generateReference(vrn, period)
    )
  }

  private def generateReference(vrn: String, etmpPeriod: String): String = {
    val etmpYear = etmpPeriod.substring(0, 2)
    val etmpQuarter = etmpPeriod.substring(2, 4)

    val quarter = etmpQuarter.replace("C", "Q")

    s"XI/XI$vrn/${quarter}.20${etmpYear}"
  }
}
