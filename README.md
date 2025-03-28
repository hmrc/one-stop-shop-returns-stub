
# one-stop-shop-returns-stub

This is the repository for One Stop Shop Returns Stub, it is used by the One Stop Shop Returns Frontend.
This application replaces the call to DES API to retrieve the payments made by the user in a given time range. It allows 
us to test different scenarios such as the return being fully or partially paid by the user or no transactions found.
More information around this, including test data and the QA environment are towards the bottom of this file.

Frontend: https://github.com/hmrc/one-stop-shop-returns-frontend

Backend: https://github.com/hmrc/one-stop-shop-returns

## Run the application

To update from Nexus and start all services from the RELEASE version instead of snapshot
```
sm --start ONE_STOP_SHOP_ALL -r
```

### To run the application locally execute the following:
```
sm --stop ONE_STOP_SHOP_RETURNS_STUB
```
and 
```
sbt 'run 10206'
```

## Unit Tests

To run the unit tests you will need to open an sbt session on the browser.

To run all tests, run the following command in your sbt session:
```
test
```

To run a single test, run the following command in your sbt session:
```
testOnly <package>.<SpecName>
```

An asterisk can be used as a wildcard character without having to enter the package, as per the example below:
```
testOnly *FinancialDataControllerSpec
```

## Stub usage and data

The stub is currently configured to be used locally and in the Development and Staging environments to substitute the
call out to DES for financial information.

The returns frontend requires the DES Financial Information API in order to provide the most recent payment information
for a customer's returns. This data is displayed on the "Your Account" payment tile, the submitted returns history and
on a past return.

The stub data that will be returned in the environments not connected to DES, as mentioned above, will be as follows:
- VRNs beginning with 1 will return a charge that is fully paid (£1500 paid)
- VRNs beginning with 2 will return a charge that is partially paid (£500 paid)
- VRNs beginning with 3 will return a charge that is not paid at all (£0 paid)
- VRNs beginning with 4 will return charges for multiple periods with a mixture of partially paid and not paid
- VRNs beginning with 5 will simulate a Not Found response

More information about this data can be found in controllers/TestData.scala.

## QA environment

The QA environment is connected to the DES test environment so that we can use this for testing payment integration with ETMP.

From the previous two rounds of integration testing, we have the following test data available:

Scenario 1 - Fully paid
```
VRN - 123456789                       
£1500 VAT owed to France
July-September 2021 return                 
£1500 paid
```

Scenario 2 - Not paid
```
VRN - 234567891                       
£1500 VAT owed to France             
July-September 2021 return
£0 paid
```

Scenario 3 - Partially paid
```
VRN - 345678912                       
£1500 VAT owed to France             
July-September 2021 return
£500 paid
```

Scenario 4 - Three fully paid, one not paid, over multiple periods
```
VRN - 678912345                      
£1500 VAT owed to France
July-September 2021 return
£1500 paid
£2500 VAT owed to Germany
July-September 2021 return
£2500 paid
£1500 VAT owed to Austria             
October-December 2021 return
£1500 paid
£2500.99 VAT owed to Spain          
July-September 2022 return
£0 paid
```

Scenario 5 - One partially paid, two not paid, over multiple periods
```
VRN - 456789123                       
£1500 VAT owed to France             
July-September 2021 return
£500 paid
£1500 VAT owed to Austria             
October-December 2021 return
£0 paid
£2500.99 VAT owed to Spain         
July-September 2022 return
£0 paid
```

Scenario 6 - A return with corrections that has been fully paid
```
VRN - 392531937
£400 VAT owed to Austria
£105 VAT owed to Belgium
October-December 2021 return
Corrections for July-September 2021 return:
£250 VAT owed to Belgium
£350 VAT owed to Hungary
£1,105 paid altogether
```

Scenario 7 - A nil return with corrections that has been partially paid
```
VRN - 125163735
£0 VAT owed for October-December 2021 return
Corrections for July-September 2021 return:
£-18 VAT owed to Cyprus
£100 VAT owed to Luxembourg
£50 paid
```

In order to set up further payments integration test data, the appropriate returns would need to be added via the
returns frontend and details of these passed to ETMP, who will then be able to create the relevant charges and payments
on their environment, to be accessed via the API and viewed in the returns frontend.

Requirements
------------

This service is written in [Scala](http://www.scala-lang.org/) and [Play](http://playframework.com/), so needs at least a [JRE] to run.

### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
