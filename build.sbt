resolvers += "Sonatype releases" at "https://oss.sonatype.org/content/repositories/releases"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

scalaVersion := "2.10.2"

libraryDependencies ++= Seq( 
	"org.mongodb" %% "casbah" % "2.6.2",
	"junit" % "junit" % "4.8.1" % "test",
	"org.scalatest" %% "scalatest" % "1.9.1" % "test",
	"com.novocode" % "junit-interface" % "0.8" % "test->default",
	"com.typesafe.akka" % "akka-actor_2.10" % "2.2.1",
	"com.typesafe.akka" %% "akka-remote" % "2.2.1"
)

libraryDependencies <+= scalaVersion { "org.scala-lang" % "scala-swing" % _ }
