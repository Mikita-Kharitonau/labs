name := "lab1"

version := "0.1"

scalaVersion := "2.12.9"

val breezeVersion = "1.0"


libraryDependencies ++= Seq(
  "org.scalanlp" %% "breeze" % breezeVersion,
  "org.scalanlp" %% "breeze-viz" % breezeVersion,
  "org.scalanlp" %% "breeze-natives" % breezeVersion
)
