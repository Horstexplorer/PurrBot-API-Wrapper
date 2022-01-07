# PurrBot API Wrapper

###### A simple wrapper for the anime image api provided by PurrBot

[![Generic badge](https://img.shields.io/badge/Java-17-white.svg)]()
[![Generic badge](https://img.shields.io/nexus/r/de.netbeacon.purrito/PurrBot-API-Wrapper?color=white&label=Latest%20Version&nexusVersion=3&server=https%3A%2F%2Fcd.voigt.app%2F)]()

## Installation

Grab the latest version from our [Nexus-Proxy](https://nexus.hypercdn.de/) using Gradle or Maven.  
⚠️Be aware that this instance acts as proxy for load distribution.  
⚠️Artifacts might not be listed till requested and the initial load might be very slow.

### Gradle

```
repositories {
    maven {
        name 'maven-hypercdn'
        url 'https://nexus.hypercdn.de/repository/maven-hypercdn'
    }
}
```

```
dependencies {
    implementation group: 'de.netbeacon.purrito', name: 'PurrBot-API-Wrapper', version: 'REPLACE WITH LATEST VERSION'
}
```

### Maven

```
<repositories>
    <repository>
        <id>maven-hypercdn</id>
        <name>maven-hypercdn</name>
        <url>https://nexus.hypercdn.de/repository/maven-hypercdn</url>
    </repository>
</repositories>
```

```
<dependencies>
    <dependency>
        <groupId>de.netbeacon.purrito</groupId>
        <artifactId>PurrBot-API-Wrapper</artifactId>
        <version>REPLACE WITH LATEST VERSION</version>
    </dependency>
</dependencies>

```

## Links

[PurrBot](https://purrbot.site)  
[PurrBot - API](https://docs.purrbot.site)

## Usage

All requests are generated via the wrapper instance which can be created simply like this and should be reused for multiple requests

```java
var purrito=new Purrito();
```

The wrapper supports the API functionalities for loading random anime images, as well as the image generation for status and quote images.

```java
var retrieveAnimeImageUrl=purrito.retrieveAnimeImageFor(imageContent,imageType);
var retrieveQuoteImage=purrito.retrieveQuoteImageOf(avatarUrl, username, message, nameColor, dateformat);
var retrieveStatusImage=retrieveStatusImageOf(avatarUrl, status, isMobile);
```

All requests can be triggered either asynchronously or synchronously

```java
// async
retrieveAnimeImageUrl.queue(retrievedData->{
        // handle data
});
// sync
var retrievedData=retrieveAnimeImageUrl.execute()
```