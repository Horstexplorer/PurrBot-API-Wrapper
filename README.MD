# PurrBot API Wrapper
#### A simple wrapper for the anime image api provided by PurrBot

## Table of contents
- Download  
- Links  
- Usage
  - Raw
    - Prepare the wrapper
    - Build a request
    - Sync execution
    - Async execution
  - Simple
    - Prepare the wrapper
    - Sync execution
    - Async execution

## Download the latest version here
Get it from Nexus  
```
Gradle:
maven {
  url 'https://nexus.hypercdn.de/repository/hypercdn-maven'
}

implementation group: 'de.netbeacon.purrito', name: 'PurrBot-API-Wrapper', version: '#.#.#'


Maven:
<repository>
  <url>https://nexus.hypercdn.de/repository/hypercdn-maven</url>
</repository>

<dependency>
  <groupId>de.netbeacon.purrito</groupId>
  <artifactId>PurrBot-API-Wrapper</artifactId>
  <version>#.#.#</version>
</dependency>
```
Or from our CI  
[View all](https://ci.netbeacon.de/viewType.html?buildTypeId=Netbeacon_Miscellaneous_PurritoPurrBotApiWrapper&tab=buildTypeStatusDiv) /
[Direct download](https://ci.netbeacon.de/repository/download/Netbeacon_Miscellaneous_PurritoPurrBotApiWrapper/latest.lastSuccessful/PurrBot-API-Wrapper_latest.jar)  
Or here from GitHub

## Links
[PurrBot](https://purrbot.site)  
[PurrBot - API](https://docs.purrbot.site)

## Usage
This wrapper provides two ways of interacting with the api - an simplified one and a more raw one for when you want a maximum of customisability.  
A full description of what is possible can be accessed through the javadocs.  

### Simplified (Default)
Lets start with creating a new client to interact with the api. The client is intended to be reused for all of your requests but if you see the need to have multiple instance of it, that works aswell.
```java
Purrito purrito = new Purrito();
```
Now there are various things you can do to interact with the api - all of which will return an execution stage object
In this example we get the execution stage returning us an image object from the api data.
The imageType will be what type of image you like to receive starting with if it should be SFW or NSFW, the contentType will be what type of image you want (gif/image).
Keep in mind that not all image types support all content types.
```
ExecutionStage<Image> executionStage = getAnimeImageOf(imageType, contentType);
// example
ExecutionStage<Image> executionStage = getAnimeImageOf(ImageType.SFW.HUG, ContentType.GIF);
```
Now there are two ways to receive the image - a blocking and a non-blocking (preferred) way.
For our example above this could look like this:
```java
// blocking
Image image = executionStage.sync(); // will return null if something went wrong and logs an exception
// non blocking
executionStage.async((image) -> { // do something with the image here }); // will call the consumer on success and log an exception if things went wrong
executionStage.async((image) -> { // do something with the image here }, (error) -> { // do something with the error here}); // will call the first consumer on success and the second one with the exception if things went wrong
```

### Raw
As the simplified version is intended to be easily used everywhere it might not contain all features you like to work with.
Therfore you can use the more raw interaction with the api.
A short example below:
```java
PurritoRaw purritoRaw = new PurritoRaw();
Request request = purritoRaw.newRequest()
                            .useEndpoint(endpoint)
                            .getReturnType(returnType)
                            .addTransmissionData(jsonPayload)
                            .prepare();
// blocking
IResponse response = request.execute(); // requires additional checks for the iResponse if things went right
// non blocking
request.execute(iResponse -> , error ->); // requires additional checks or the iResponse if things went right

```