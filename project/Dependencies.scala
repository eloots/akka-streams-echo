import sbt._

object Version {
  val akkaVer         = "2.5.16"
  val scalaVer        = "2.12.6"
  val scalaTestVer    = "3.0.4"
  val logbackVer      = "1.2.3"

}

object Dependencies {
  val dependencies = Seq(
    "com.typesafe.akka"       %% "akka-actor"                 % Version.akkaVer withSources(),
    "com.typesafe.akka"       %% "akka-testkit"               % Version.akkaVer withSources(),
    "com.typesafe.akka"       %% "akka-stream"                % Version.akkaVer withSources(),
    "com.typesafe.akka"       %% "akka-stream-testkit"        % Version.akkaVer withSources(),
    "com.typesafe.akka"       %% "akka-slf4j"                 % Version.akkaVer,
    "ch.qos.logback"           % "logback-classic"            % Version.logbackVer,
    "org.scalatest"           %% "scalatest"                  % Version.scalaTestVer % "test",
    "org.scalactic"           %% "scalactic"                  % Version.scalaTestVer % "test"
  )
}