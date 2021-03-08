/***************************************************************
  *      THIS IS A GENERATED FILE - EDIT AT YOUR OWN RISK      *
  **************************************************************
  *
  * Use the mainadm command to generate a new version of
  * this build file.
  *
  * See https://github.com/lightbend/course-management-tools
  * for more details
  *
  */

import sbt._

Global / onChangedBuildSource := ReloadOnSourceChanges

lazy val `akka_streams_echo` = (project in file("."))
  .aggregate(
    common,
    `exercise_000_initial_state`,
    `exercise_001_implement_a_delay_element`,
    `exercise_002_implement_fir_manually`,
    `exercise_003_implement_fir_streamlined`,
    `exercise_004_implement_iir`,
    `exercise_005_chain_fir_and_fir`,
    `exercise_006_chain_iir_and_fir_cancel_echo`,
    `exercise_007_check_diff`,
    `exercise_008_vco`,
    `exercise_009_matching_streams_speeds`
  )
  .settings(ThisBuild / scalaVersion := Version.scalaVersion)
  .settings(CommonSettings.commonSettings: _*)

lazy val common = project
  .settings(CommonSettings.commonSettings: _*)

lazy val `exercise_000_initial_state` = project
  .configure(CommonSettings.configure)
  .dependsOn(common % "test->test;compile->compile")

lazy val `exercise_001_implement_a_delay_element` = project
  .configure(CommonSettings.configure)
  .dependsOn(common % "test->test;compile->compile")

lazy val `exercise_002_implement_fir_manually` = project
  .configure(CommonSettings.configure)
  .dependsOn(common % "test->test;compile->compile")

lazy val `exercise_003_implement_fir_streamlined` = project
  .configure(CommonSettings.configure)
  .dependsOn(common % "test->test;compile->compile")

lazy val `exercise_004_implement_iir` = project
  .configure(CommonSettings.configure)
  .dependsOn(common % "test->test;compile->compile")

lazy val `exercise_005_chain_fir_and_fir` = project
  .configure(CommonSettings.configure)
  .dependsOn(common % "test->test;compile->compile")

lazy val `exercise_006_chain_iir_and_fir_cancel_echo` = project
  .configure(CommonSettings.configure)
  .dependsOn(common % "test->test;compile->compile")

lazy val `exercise_007_check_diff` = project
  .configure(CommonSettings.configure)
  .dependsOn(common % "test->test;compile->compile")

lazy val `exercise_008_vco` = project
  .configure(CommonSettings.configure)
  .dependsOn(common % "test->test;compile->compile")

lazy val `exercise_009_matching_streams_speeds` = project
  .configure(CommonSettings.configure)
  .dependsOn(common % "test->test;compile->compile")
       