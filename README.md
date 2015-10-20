# Point Location 6709 

[![Build Status](https://travis-ci.org/sualeh/pointlocation6709.svg?branch=master)](https://travis-ci.org/sualeh/pointlocation6709)
[![Maven Central](https://img.shields.io/maven-central/v/us.fatehi/pointlocation6709.svg)](http://search.maven.org/#search%7Cga%7C1%7Cg%3Aus.fatehi%20pointlocation6709)

*Point Location 6709* is a Java representation of [ISO 6709] geographic point location by coordinates. 

All classes are immutable and thread-safe. The code includes a parser that can parse all valid ISO 6709 representations. A formatter formats point locations to ISO 6709 "[human interface]" representations as well as "[string expressions]". Validity is enforced by JUnit tests. Java 7 or newer is required. 

## Download

You can download the [jar on the Maven Central Repository].

## Maven Build

To use *Point Location 6709* in your Maven build, include the following dependency. No repositories references are needed, since the jars are in the Maven Central Repository.
```xml
<dependency>
    <groupId>us.fatehi</groupId>
    <artifactId>pointlocation6709</artifactId>
    <version>4.1</version>
</dependency>
```


[ISO 6709]: https://en.wikipedia.org/wiki/ISO_6709
[human interface]: https://en.wikipedia.org/wiki/ISO_6709#Representation_at_the_human_interface_.28Annex_D.29
[string expressions]: https://en.wikipedia.org/wiki/ISO_6709#String_expression_.28Annex_H.29
[jar on the Maven Central Repository]: http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22pointlocation6709%22
