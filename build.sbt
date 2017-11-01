name := "DataConnector"

version := "1.0"

scalaVersion := "2.12.4"

libraryDependencies += "org.apache.kafka" % "kafka-clients" % "0.11.0.1"

libraryDependencies += "com.typesafe.play" % "play-json_2.12" % "2.6.6"

libraryDependencies += "io.reactivex" % "rxjava" % "1.2.4"

libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.25"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"

javacOptions in (Compile,doc) ++= Seq("-notimestamp", "-linksource")
