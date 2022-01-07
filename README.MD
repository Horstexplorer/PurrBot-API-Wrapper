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
