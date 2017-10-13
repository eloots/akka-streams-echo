import sbt._

object Version {
  val akkaVer         = "2.5.6"
  val scalaVer        = "2.12.3"
  val scalaTestVer    = "3.0.1"
  val logbackVer      = "1.1.3"

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