import sbt._

object Version {
  val akkaVer         = "2.6.13"
  val scalaVersion    = "2.13.5"
  val scalaTestVer    = "3.2.5"
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
