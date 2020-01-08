= Vert.x Maven Starter

This project is a template to start your own Vert.x project using Apache Maven.

== Prerequisites

* Apache Maven
* JDK 8+

== Getting started

Create your project with:

[source]
----
git clone https://github.com/Nischalesha/vertx-maven-gleads.git 
----

== Running the project

Once you have retrieved the project, you can check that everything works with:

[source]
----
mvn test exec:java
----

The command compiles the project and runs the tests, then  it launches the application, so you can check by yourself. Open your browser to http://localhost:8080. You should see a _Hello World_ message.

== Anatomy of the project

The project contains:

* a `pom.xml` file
* a _main_ verticle file (src/main/java/io/vertx/maven/gleads/MainVerticle.java)
* an unit test (src/main/test/io/vertx/maven/gleads/MainVerticleTest.java)

== Building the project

To build the project, just use:

----
mvn clean package
----

It generates a _fat-jar_ in the `target` directory.
