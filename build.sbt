
libraryDependencies += "com.github.tototoshi" %% "scala-csv" % "1.3.3"
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"
val h2 = "com.h2database" % "h2" % "1.2.127"
libraryDependencies ++= Seq("com.googlecode.mapperdao" %% "mapperdao" % "1.0.1", h2)

lazy val root = (project in file(".")).
  settings(
    name := "Lunatech",
    version := "1.0",
    scalaVersion := "2.11.8"
  )
