name := "ScalaKafkaExamples"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.apache.kafka" %% "kafka" % "0.8.2.1",
  "org.apache.kafka" % "kafka-clients" % "0.8.2.1"
)

assemblyMergeStrategy in assembly := {
  case PathList(ps @ _*) if ps.last endsWith ".html"        => MergeStrategy.first
  case "application.conf"                                   => MergeStrategy.concat
  case "log4j.properties"                                   => MergeStrategy.first
  case "reference.conf"                                     => MergeStrategy.concat
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}