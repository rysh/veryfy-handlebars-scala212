import Dependencies._


lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.11.12",
//      scalaVersion := "2.12.4",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "veryfy-handlebars-scala212",
    libraryDependencies ++= Seq(
      "com.github.jknack" % "handlebars" % "4.0.6",
      "com.github.jknack" % "handlebars-jackson2" % "4.0.6",
      "joda-time" % "joda-time" % "2.7",
      "org.joda" % "joda-convert" % "1.7",
      scalaTest % Test)
  )

