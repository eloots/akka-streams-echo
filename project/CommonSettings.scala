import sbt.Keys._
import sbt._
import sbtstudent.AdditionalSettings

object CommonSettings {
  lazy val commonSettings = Seq(
    organization := "org.echo",
    version := "1.0.0",
    scalacOptions ++= CompileOptions.compileOptions,
    unmanagedSourceDirectories in Compile := List((scalaSource in Compile).value, (javaSource in Compile).value),
    unmanagedSourceDirectories in Test := List((scalaSource in Test).value, (javaSource in Test).value),
    fork in Test := true,
    parallelExecution in Test := false,
    logBuffered in Test := false,
    parallelExecution in GlobalScope := false,
    libraryDependencies ++= Dependencies.dependencies
  ) ++
    AdditionalSettings.initialCmdsConsole ++
    AdditionalSettings.initialCmdsTestConsole ++
    AdditionalSettings.cmdAliases

  lazy val configure: Project => Project = (project: Project) => {
    project.settings(CommonSettings.commonSettings: _*)
  }
}
