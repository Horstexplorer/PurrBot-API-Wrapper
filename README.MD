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
A full description of what is possible can be accessed through the javadocs.  
### Raw
#### Prepare the wrapper
Create a new instance of the class to prepare the wrapper. This instance should be used for all requests (but you can create multiple instances if that is needed somehow)
```java
 Purrito purrito = new Purrito(); 
```
#### Build a request
A request can be created like the example below
```java
Request.Builder newRequestBuilder = purrito.newRequest();
Request request = newRequestBuilder
                    .useEndpoint(Endpoint.SFW_Bite)
                    .getReturnType(Endpoint.ReturnType.JSON_w_GIF)
                    .prepare();
```
A basic example on how to interact with the api is shown below: You decide what to do with the responses
#### Sync
You can get the response in a blocking way
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
The wrapper also provides tools to do what you want to do in a more easy way. Keep in mind that not all features are available that way, and you loose a lot of customisability.  

#### Prepare the wrapper
We need to prepare the wrapper aswell but instead of using the way mentioned above we use:
```java
Purritowo purritowo = new Purritowo();
```

#### Sync
You still have the possibility to get the results in a blocking way
```
String url = purritowo.doSync().getImageURL(ImageType.SFW.DANCE, ContentType.GIF);
if(url == null){
  // some error occured - view logs
}
```

#### Async
... or in a non-blocking way
```
purritowo.doAsync().getImageURL(ImageType.SFW.DANCE, ContentType.GIF, (url) -> { // do something with the url }, (error) -> { // handle errors });
```