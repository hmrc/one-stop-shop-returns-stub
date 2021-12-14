
# one-stop-shop-returns-stub

This is the repository for One Stop Shop Returns Stub, it is used by the One Stop Shop Returns Frontend.
This application replaces the call to DES API to retrieve the payments made by the user in a given time range. It allows to test different scenarios such as the return being fully or partially paid by the user or no transactions found.

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

Requirements
------------

This service is written in [Scala](http://www.scala-lang.org/) and [Play](http://playframework.com/), so needs at least a [JRE] to run.

### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
