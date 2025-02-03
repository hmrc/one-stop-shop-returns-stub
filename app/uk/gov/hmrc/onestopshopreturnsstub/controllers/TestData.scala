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

  val singleOutstandingPayment = Seq(
    FinancialTransaction(
      chargeType = Some("G Ret FR EU-OMS"),
      mainType = None,
      taxPeriodFrom = Some(period.firstDay),
      taxPeriodTo = Some(period.lastDay),
      originalAmount = Some(BigDecimal(8703.13)),
      outstandingAmount = Some(BigDecimal(8703.13)),
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
        periodKey = "22Q1"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = "22Q2"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Open,
        periodKey = "22Q3"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Open,
        periodKey = "21Q3"
      )
    )
  )))

  val oneFulfilledObligationDetails: EtmpObligations = EtmpObligations(obligations = Seq(EtmpObligation(
    obligationDetails = Seq(
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = "21Q3"
      )
    )
  )))

  val twoFulfilledObligationDetails: EtmpObligations = EtmpObligations(obligations = Seq(EtmpObligation(
    obligationDetails = Seq(
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = "21Q3"
      ),
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Fulfilled,
        periodKey = "21Q4"
      )
    )
  )))

  val firstPeriodNoCorrections: EtmpObligations = EtmpObligations(obligations = Seq(EtmpObligation(
    obligationDetails = Seq(
      EtmpObligationDetails(
        status = EtmpObligationsFulfilmentStatus.Open,
        periodKey = "21Q3"
      )
    )
  )))


  def basicEtmpVatReturn(vrn: String, period: String): EtmpVatReturn = {

    EtmpVatReturn(
      returnReference = generateReference(vrn, period),
      returnVersion = LocalDateTime.of(2024, 1, 2, 0, 0, 0),
      periodKey = period,
      returnPeriodFrom = LocalDate.of(2023, 12, 1),
      returnPeriodTo = LocalDate.of(2023, 12, 31),
      goodsSupplied = Seq(
        EtmpVatReturnGoodsSupplied(
          msOfConsumption = "DE",
          msOfEstablishment = "FR",
          vatRateType = EtmpVatRateType.StandardVatRate,
          taxableAmountGBP = BigDecimal(12345.67),
          vatAmountGBP = BigDecimal(1000.00)
        ),
        EtmpVatReturnGoodsSupplied(
          msOfConsumption = "FR",
          msOfEstablishment = "ES",
          vatRateType = EtmpVatRateType.ReducedVatRate,
          taxableAmountGBP = BigDecimal(23973.03),
          vatAmountGBP = BigDecimal(1000.00)
        ),
      ),
      totalVATGoodsSuppliedGBP = BigDecimal(2000.00),
      totalVATAmountPayable = BigDecimal(2000.00),
      totalVATAmountPayableAllSpplied = BigDecimal(2000.00),
      correctionPreviousVATReturn = Seq.empty,
      totalVATAmountFromCorrectionGBP = BigDecimal(0.00),
      balanceOfVATDueForMS = Seq(
        EtmpVatReturnBalanceOfVatDue(
          msOfConsumption = "DE",
          totalVATDueGBP = BigDecimal(1000.00),
          totalVATEUR = BigDecimal(1000.00)
        ),
        EtmpVatReturnBalanceOfVatDue(
          msOfConsumption = "FR",
          totalVATDueGBP = BigDecimal(1000.00),
          totalVATEUR = BigDecimal(1000.00)
        )
      ),
      totalVATAmountDueForAllMSGBP = BigDecimal(2000.00),
      paymentReference = generateReference(vrn, period)
    )
  }

//  def etmpVatReturnWithCorrections(vrn: String, period: String): EtmpVatReturn = {
//
//    EtmpVatReturn(
//      returnReference = generateReference(vrn, period),
//      returnVersion = LocalDateTime.of(2024, 1, 2, 0, 0, 0),
//      periodKey = period,
//      returnPeriodFrom = LocalDate.of(2023, 12, 1),
//      returnPeriodTo = LocalDate.of(2023, 12, 31),
//      goodsSupplied = Seq(
//        EtmpVatReturnGoodsSupplied(
//          msOfConsumption = "DE",
//          msOfEstablishment = "MT",
//          vatRateType = EtmpVatRateType.StandardVatRate,
//          taxableAmountGBP = BigDecimal(12345.67),
//          vatAmountGBP = BigDecimal(1000.00)
//        ),
//        EtmpVatReturnGoodsSupplied(
//          msOfConsumption = "FR",
//          msOfEstablishment = "XI",
//          vatRateType = EtmpVatRateType.StandardVatRate,
//          taxableAmountGBP = BigDecimal(12345.67),
//          vatAmountGBP = BigDecimal(1000.00)
//        ),
//        EtmpVatReturnGoodsSupplied(
//          msOfConsumption = "ES",
//          msOfEstablishment = "XI",
//          vatRateType = EtmpVatRateType.ReducedVatRate,
//          taxableAmountGBP = BigDecimal(23973.03),
//          vatAmountGBP = BigDecimal(1000.00)
//        ),
//        EtmpVatReturnGoodsSupplied(
//          msOfConsumption = "DE",
//          msOfEstablishment = "AT",
//          vatRateType = EtmpVatRateType.ReducedVatRate,
//          taxableAmountGBP = BigDecimal(5231.03),
//          vatAmountGBP = BigDecimal(1565.46)
//        ),
//        EtmpVatReturnGoodsSupplied(
//          msOfConsumption = "LT",
//          msOfEstablishment = "LV",
//          vatRateType = EtmpVatRateType.ReducedVatRate,
//          taxableAmountGBP = BigDecimal(5231.03),
//          vatAmountGBP = BigDecimal(1565.46)
//        )
//      ),
//      totalVATGoodsSuppliedGBP = BigDecimal(2000.00),
//      totalVATAmountPayable = BigDecimal(2000.00),
//      totalVATAmountPayableAllSpplied = BigDecimal(2000.00),
//      correctionPreviousVATReturn = Seq(
//        EtmpVatReturnCorrection(
//          periodKey = "21Q3",
//          periodFrom = LocalDate.of(2021, 7, 1).toString,
//          periodTo = LocalDate.of(2021, 9, 30).toString,
//          msOfConsumption = "DE",
//          totalVATAmountCorrectionGBP = BigDecimal(-1500.00),
//          totalVATAmountCorrectionEUR = BigDecimal(-1500.00)
//        ),
//        EtmpVatReturnCorrection(
//          periodKey = "21Q3",
//          periodFrom = LocalDate.of(2021, 7, 1).toString,
//          periodTo = LocalDate.of(2021, 9, 30).toString,
//          msOfConsumption = "FR",
//          totalVATAmountCorrectionGBP = BigDecimal(-3500.00),
//          totalVATAmountCorrectionEUR = BigDecimal(-3500.00)
//        ),
//        EtmpVatReturnCorrection(
//          periodKey = "21Q4",
//          periodFrom = LocalDate.of(2021, 10, 1).toString,
//          periodTo = LocalDate.of(2021, 12, 31).toString,
//          msOfConsumption = "ES",
//          totalVATAmountCorrectionGBP = BigDecimal(-2500.00),
//          totalVATAmountCorrectionEUR = BigDecimal(-2500.00)
//        ),
//        EtmpVatReturnCorrection(
//          periodKey = "21Q4",
//          periodFrom = LocalDate.of(2021, 10, 1).toString,
//          periodTo = LocalDate.of(2021, 12, 31).toString,
//          msOfConsumption = "LV",
//          totalVATAmountCorrectionGBP = BigDecimal(-1800.00),
//          totalVATAmountCorrectionEUR = BigDecimal(-1800.00)
//        ),
//        EtmpVatReturnCorrection(
//          periodKey = "22Q1",
//          periodFrom = LocalDate.of(2022, 1, 1).toString,
//          periodTo = LocalDate.of(2021, 3, 31).toString,
//          msOfConsumption = "LT",
//          totalVATAmountCorrectionGBP = BigDecimal(-1800.00),
//          totalVATAmountCorrectionEUR = BigDecimal(-1800.00)
//        )
//      ),
//      totalVATAmountFromCorrectionGBP = BigDecimal(0.00),
//      balanceOfVATDueForMS = Seq(
//        EtmpVatReturnBalanceOfVatDue(
//          msOfConsumption = "DE",
//          totalVATDueGBP = BigDecimal(1000.00),
//          totalVATEUR = BigDecimal(1000.00)
//        ),
//        EtmpVatReturnBalanceOfVatDue(
//          msOfConsumption = "FR",
//          totalVATDueGBP = BigDecimal(1000.00),
//          totalVATEUR = BigDecimal(1000.00)
//        )
//      ),
//      totalVATAmountDueForAllMSGBP = BigDecimal(2000.00),
//      paymentReference = generateReference(vrn, period)
//    )
//  }

  def etmpVatReturnWithoutCorrections(vrn: String, period: String): EtmpVatReturn = {

    EtmpVatReturn(
      returnReference = generateReference(vrn, period),
      returnVersion = LocalDateTime.of(2024, 1, 2, 0, 0, 0),
      periodKey = period,
      returnPeriodFrom = LocalDate.of(2021, 7, 1),
      returnPeriodTo = LocalDate.of(2021, 9, 30),
      goodsSupplied = Seq(
        EtmpVatReturnGoodsSupplied(
          msOfConsumption = "HR",
          msOfEstablishment = "MT",
          vatRateType = EtmpVatRateType.StandardVatRate,
          taxableAmountGBP = BigDecimal(12345.67),
          vatAmountGBP = BigDecimal(7469.13)
        ),
        EtmpVatReturnGoodsSupplied(
          msOfConsumption = "FR",
          msOfEstablishment = "XI",
          vatRateType = EtmpVatRateType.StandardVatRate,
          taxableAmountGBP = BigDecimal(12345.67),
          vatAmountGBP = BigDecimal(1234.00)
        )
      ),
      totalVATGoodsSuppliedGBP = BigDecimal(8703.13),
      totalVATAmountPayable = BigDecimal(8703.13),
      totalVATAmountPayableAllSpplied = BigDecimal(8703.13),
      correctionPreviousVATReturn = Seq.empty,
      totalVATAmountFromCorrectionGBP = BigDecimal(0.00),
      balanceOfVATDueForMS = Seq(
        EtmpVatReturnBalanceOfVatDue(
          msOfConsumption = "DE",
          totalVATDueGBP = BigDecimal(7469.13),
          totalVATEUR = BigDecimal(7469.13)
        ),
        EtmpVatReturnBalanceOfVatDue(
          msOfConsumption = "FR",
          totalVATDueGBP = BigDecimal(1234.00),
          totalVATEUR = BigDecimal(1234.00)
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

    s"XI/XI$vrn/${etmpQuarter}.20${etmpYear}"
  }
}
