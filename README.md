[![Build Status](https://travis-ci.org/konikvranik/jtoggl.svg?branch=master)](https://travis-ci.org/konikvranik/jtoggl) [ ![Codeship Status for konikvranik/jtoggl](https://codeship.com/projects/0e0c0050-d308-0133-5da2-5e07c373472b/status?branch=master)](https://codeship.com/projects/141996) [![codecov.io](https://codecov.io/github/konikvranik/jtoggl/coverage.svg?branch=master)](https://codecov.io/github/konikvranik/jtoggl?branch=master) [![Issue Count](https://codeclimate.com/github/konikvranik/jtoggl/badges/issue_count.svg)](https://codeclimate.com/github/konikvranik/jtoggl) [![Code Issues](https://www.quantifiedcode.com/api/v1/project/4ec4d485b6884f76a74d5799c08d14dc/badge.svg)](https://www.quantifiedcode.com/app/project/4ec4d485b6884f76a74d5799c08d14dc) [![Download](https://api.bintray.com/packages/konikvranik/maven/jtoggl/images/download.svg)](https://bintray.com/konikvranik/maven/jtoggl/_latestVersion)
----
<h1>What is it about?</h1>

jtoggl wraps the Toggl REST/JSON API for Java. 
https://www.toggl.com/public/api

<h1>Get it!</h1>

Please have a look at the fork https://github.com/konikvranik/jtoggl which might contain a more suitable solution for you.

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
