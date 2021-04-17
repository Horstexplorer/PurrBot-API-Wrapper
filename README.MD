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
[View all](https://ci.netbeacon.de/viewType.html?buildTypeId=Netbeacon_Miscellaneous_PurritoPurrBotApiWrapper&tab=buildTypeStatusDiv)  
[Direct download](https://ci.netbeacon.de/repository/download/Netbeacon_Miscellaneous_PurritoPurrBotApiWrapper/latest.lastSuccessful/PurrBot-API-Wrapper_latest.jar)  

## Links
[PurrBot](https://purrbot.site)  
[PurrBot - API](https://docs.purrbot.site)

## Usage
This wrapper provides various ways to interact with the image api.
A full description of what is possible can be viewed through the javadocs.
### Raw
#### Prepare the wrapper
Create a new instance of the class to prepare the wrapper. This instance should be used for all requests (but you can create multiple instances if that is needed somehow)
```java
 Purrito purrito = new Purrito(); 
```
#### Build a request
A request can be build just like that
```java
Request.Builder newRequestBuilder = purrito.newRequest();
Request request = newRequestBuilder
                    .useEndpoint(Endpoint.SFW_Bite)
                    .getReturnType(Endpoint.ReturnType.JSON_w_GIF)
                    .prepare();
```
The example below shows a basic example on how to interact with the api: You decide what to do with the responses
#### Sync
You can get responses in a blocking way
```java
IResponse iResponse = request.execute();
if(iResponse instanceof IResponse.Success){
    // handle response (might require additional checks)
}else{
    // handle error
}
```
#### Async
... or you can use it in a non-blocking way
```java
request.execute(success -> {
        // handle response (might require additional checks)
    }, error -> {
        // handle error
});
```

### Simple
The wrapper also provides tool to do what you want to do in a more easy way. Keep in mind that not all features are available that way.  

#### Prepare the wrapper
We need to prepare the wrapper aswell but instead of using the mentioned way above, we use:
```java
Purritowo purritowo = new Purritowo();
```

#### Sync
You can get responses in a blocking way - for example if you want to get the url of a random dance image
```
String url = purritowo.doSync().getImageURL(ImageType.SFW.DANCE, ContentType.GIF);
```

#### Async
This also works in a non-blocking way
```
purritowo.doAsync().getImageURL(ImageType.SFW.DANCE, ContentType.GIF, (url) -> { // do something with the url });
```