
object CompileOptions {
  val compileOptions = Seq(
    "-unchecked",
    "-deprecation",
    "-feature",
    "-language:postfixOps",
    "-language:implicitConversions",
    "-language:reflectiveCalls",
    "-encoding", "UTF-8"
  )
}
