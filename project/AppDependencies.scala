import play.core.PlayVersion
import sbt.*

object AppDependencies {

  val compile = Seq(
    "uk.gov.hmrc"                   %% "bootstrap-backend-play-30"  % "10.7.0",
    "com.networknt"                 %  "json-schema-validator"      % "1.5.9",
    "com.fasterxml.jackson.module"  %% "jackson-module-scala"       % "2.21.1"
  )

  val test = Seq(
    "uk.gov.hmrc"             %% "bootstrap-test-play-30"     % "10.7.0"            % Test,
    "org.scalatest"           %% "scalatest"                  % "3.2.19"            % Test,
    "org.playframework"       %% "play-test"                  % PlayVersion.current % Test,
    "com.vladsch.flexmark"    %  "flexmark-all"               % "0.64.8"            % "test, it"
  )
}
