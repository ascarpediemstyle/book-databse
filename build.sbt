name := "book-database"

version := "1.0"

libraryDependencies ++= Seq(
"org.postgresql" % "postgresql" % "9.3-1101-jdbc41",
  javaJdbc,
  javaEbean,
  cache
)     

play.Project.playJavaSettings
