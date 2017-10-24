name := "DataConnector"

version := "1.0"

scalaVersion := "2.12.4"

libraryDependencies += "org.apache.kafka" % "kafka-clients" % "0.11.0.1"

libraryDependencies += "com.typesafe.play" % "play-json_2.12" % "2.6.6"

libraryDependencies += "io.reactivex.rxjava2" % "rxjava" % "2.1.5"

javacOptions in (Compile,doc) ++= Seq("-notimestamp", "-linksource")
