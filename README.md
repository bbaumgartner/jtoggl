<h1>What is it about?</h1>

jtoggl wraps the Toggl REST/JSON API for Java. 
https://www.toggl.com/public/api

<h1>Get it!</h1>

<h2>I use Maven</h2>
You can find jtoggl-api in our Maven repository

http://pandora.comerge.net:8081/nexus/content/repositories/public_snapshots/ch/simas/jtoggl/jtoggl-api/

```XML
<repositories>
  <repository>
    <id>public-releases</id>
    <name>Comerge Public Releases</name>
    <url>http://pandora.comerge.net:8081/nexus/content/repositories/public_snapshots/</url>
  </repository>
</repositories>

<dependency>
  <groupId>ch.simas.jtoggl</groupId>
  <artifactId>jtoggl-api</artifactId>
  <version>1.0.2-SNAPSHOT</version>
</dependency>
```

<h2>I don't use Maven</h2>

You can download the jar from here: http://pandora.comerge.net:8081/nexus/content/repositories/public_snapshots/ch/simas/jtoggl/jtoggl-api/

<h1>Ok, but how do I use this</h1>
Have a look at our tests and Toggl API documentation. https://github.com/bbaumgartner/jtoggl/blob/master/jtoggl-api/src/test/java/ch/simas/jtoggl/JTogglTest.java
