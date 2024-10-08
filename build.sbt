import play.sbt.routes.RoutesKeys
import uk.gov.hmrc.DefaultBuildSettings.integrationTestSettings

val appName = "one-stop-shop-returns-stub"

lazy val microservice = Project(appName, file("."))
  .enablePlugins(play.sbt.PlayScala, SbtDistributablesPlugin)
  .settings(
    majorVersion                     := 0,
    scalaVersion                     := "2.13.12",
    libraryDependencies              ++= AppDependencies.compile ++ AppDependencies.test,

    RoutesKeys.routesImport ++= Seq(
      "java.time.LocalDate",
      "uk.gov.hmrc.onestopshopreturnsstub.models._"
    )
  )
  .configs(IntegrationTest)
  .settings(integrationTestSettings(): _*)
  .settings(resolvers += Resolver.jcenterRepo)
  .settings(PlayKeys.playDefaultPort := 10206)
  .settings(scalacOptions += "-Wconf:src=routes/.*:s")
