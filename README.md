<h1>What is it about?</h1>

jtoggl wraps the Toggl REST/JSON API for Java. 
https://www.toggl.com/public/api

<h1>Get it!</h1>

Please have a look at the fork https://github.com/konikvranik/jtoggl which might contain a more suitable solution for you.

<h2>I use Maven</h2>
You can find jtoggl-api in our Maven repository

```XML
<repositories>
		<repository>
			<id>public-releases</id>
			<name>Comerge Public Releases</name>
			<url>https://nexus.comerge.net/repository/comerge-public-maven/</url>
		</repository>
</repositories>

		<dependency>
			<groupId>ch.simas.jtoggl</groupId>
			<artifactId>jtoggl-api</artifactId>
			<version>1.1.0-SNAPSHOT</version>
		</dependency>
```

<h1>Ok, but how do I use this</h1>
Have a look at our tests and Toggl API documentation. https://github.com/bbaumgartner/jtoggl/blob/master/jtoggl-api/src/test/java/ch/simas/jtoggl/JTogglTest.java
