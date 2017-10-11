
lazy val akka_streams_echo = (project in file("."))
  .aggregate(
    common,
    exercise_000_initial_state,
    exercise_010_implement_a_delay_element,
    exercise_020_implement_fir_manually,
    exercise_030_implement_fir_streamlined,
    exercise_040_implement_iir,
    exercise_050_chain_fir_and_fir,
    exercise_060_chain_iir_and_fir_cancel_echo,
    exercise_070_check_diff,
    exercise_080_vco,
		exercise_090_matching_streams_speeds
 )
  .settings(CommonSettings.commonSettings: _*)


lazy val common = project.settings(CommonSettings.commonSettings: _*)

lazy val exercise_000_initial_state = project
  .settings(CommonSettings.commonSettings: _*)
  .dependsOn(common % "test->test;compile->compile")
lazy val exercise_010_implement_a_delay_element = project
  .settings(CommonSettings.commonSettings: _*)
  .dependsOn(common % "test->test;compile->compile")
lazy val exercise_020_implement_fir_manually = project
  .settings(CommonSettings.commonSettings: _*)
  .dependsOn(common % "test->test;compile->compile")
lazy val exercise_030_implement_fir_streamlined = project
  .settings(CommonSettings.commonSettings: _*)
  .dependsOn(common % "test->test;compile->compile")
lazy val exercise_040_implement_iir = project
  .settings(CommonSettings.commonSettings: _*)
  .dependsOn(common % "test->test;compile->compile")
lazy val exercise_050_chain_fir_and_fir = project
  .settings(CommonSettings.commonSettings: _*)
  .dependsOn(common % "test->test;compile->compile")
lazy val exercise_060_chain_iir_and_fir_cancel_echo = project
  .settings(CommonSettings.commonSettings: _*)
  .dependsOn(common % "test->test;compile->compile")
lazy val exercise_070_check_diff = project
  .settings(CommonSettings.commonSettings: _*)
  .dependsOn(common % "test->test;compile->compile")
lazy val exercise_080_vco = project
  .settings(CommonSettings.commonSettings: _*)
  .dependsOn(common % "test->test;compile->compile")
lazy val exercise_090_matching_streams_speeds = project
  .settings(CommonSettings.commonSettings: _*)
  .dependsOn(common % "test->test;compile->compile")
