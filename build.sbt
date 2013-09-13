resolvers += "Sonatype releases" at "https://oss.sonatype.org/content/repositories/releases"

libraryDependencies ++= Seq( 
	"org.mongodb" %% "casbah" % "2.6.2",
	"junit" % "junit" % "4.8.1" % "test",
	"org.scalatest" %% "scalatest" % "1.9.1" % "test",
	"com.novocode" % "junit-interface" % "0.8" % "test->default"
)

libraryDependencies <+= scalaVersion { "org.scala-lang" % "scala-swing" % _ }
