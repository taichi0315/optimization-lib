import Dependencies._

ThisBuild / scalaVersion     := "2.13.3"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "optimization-lib"
  )

// resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest-funsuite" % "3.2.0" % "test",
  "com.github.vagmcs" %% "optimus"   % "3.2.0",
  "com.github.vagmcs" %% "optimus-solver-oj" % "3.2.0",
  "com.github.vagmcs" %% "optimus-solver-lp" % "3.2.0"
)

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
