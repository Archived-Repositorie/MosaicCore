# MosaicCore Documentation
## What is MosaicCore?
MosaicCore is loader/library made as core for MosaicMC to have base to implement features. It can be seen as plugin loader or very extended library for fabric.
## Usage
1. Add modrinth repository to build.gradle
```groovy
repositories {
    // ...
    maven {
        url = "https://api.modrinth.com/maven"
    }
}
```
2. Add dependience (replace `{version}` with latest version found [here](https://modrinth.com/mod/mosaiccore/versions))
```groovy
dependencies {
    // Adding and remapping a mod only in local runtime
    modImplementation "maven.modrinth:mosaiccore:{version}"
}
```
## Making plugin
1. First you need to add entrypoint called `plugin` to `fabric.mod.json` under `Entrypoints` key, like this. Replace `plugin.entrypoint.PluginObject` with value that represents your function/class/object. If wanted to use different methods of doing it, look at readme of [fabric-language-kotlin](https://github.com/FabricMC/fabric-language-kotlin#entrypoint-samples).
```json
  "entrypoints": {
    "plugin": [
      {
        "adapter": "kotlin",
        "value": "plugin.entrypoint.PluginObject"
      }
    ]
  },
```
2. Your plugin entrypoint (if it is class/object) requires to implement interface called `PluginInitializer` which will have function called `onLoad`, it will contain in the argument the `PluginContainer` which contains informations about plugin and also the function will be executed before the server loader. If wanted to use different methods of doing it, look at readme of [fabric-language-kotlin](https://github.com/FabricMC/fabric-language-kotlin#entrypoint-samples).
```kt
object PluginObject : PluginInitializer {
    override fun onLoad(plugin: PluginContainer) {
        plugin.logger.info("Hello there!")
    }
}
```

## Event system
### Registering subscriber
Registering subscriber can be done through listener using `EventHandler.registerListener(EventListener)`. Listener needs to implement `Listener` interface.</br>
Or registering the subscriber itself using `EventHandler.registerSubscriber(::test)`.</br>
Example usage:
```kt
fun test() {
    EventHandler.registerListener(EventListener)
    EventHandler.registerSubscriber(::test)
}

object EventListener : Listener { 
    @Subscriber 
    fun test(event: TestEvent)
}

@Subscriber
fun test(event: TestEvent)
```
### Subscriber
Subscriber is a function that subscribe on specific event, it has many way of registering. Either by using listener or using the function itself.
Subscriber can also handle priority and can ignore cancellation.</br>
Example usage: 
```kt
object EventListener : Listener { 
    @Subscriber(priority = Priority.HIGHEST, ignoreCancelled = true) 
    fun test(event: TestEvent)
}
```
```kt
@Subscriber(priority = Priority.HIGHEST, ignoreCancelled = true)
fun test(event: TestEvent)
```
### Custom event
Custom event is class which subscriber can subscribe on. Event also needs to be registered using `EventHandler.registerEvent(TestEvent::class)`. Events can be cancellable by implementing `Cancellable` interface and also can be laggy by annotating `@Laggy`
which will force user to OptIn and also will warn them from usage of the event. They can be called by using `EventHandler.callEvent(TestEvent)`
</br>
It is advice to register events on before plugin load, so they can be registered before any subscriber is registered.
Example of simple event:
```kt
fun test() {
    EventHandler.registerEvent(TestEvent::class)
    EventHandler.callEvent(TestEvent())
}

class TestEvent : Event
```
Example of laggy and cancellable event and usage:
```kt
fun test() { 
    @OptIn(Laggy::class) EventHandler.callEvent(TestEvent())
}
fun beforeTest() { 
    EventHandler.registerListener(EventListener)
}

data EventListener {
  @OptIn(Laggy::class) @Subscriber
  fun test(event: TestEvent)
}

@Laggy
class TestEvent : Event, CancellableEvent {
    override var cancelled: Boolean = false
}
```
## Config system
Mod contains a very flexible config system which can be used by any plugin. It gives ability for developers to also add different types of data for config.
### Using config (JSON)
Example of making config that uses default json data type.

```kt
internal val config = ConfigLoader.JSON_CONFIG

fun pluginInit(plugin: PluginContainer) {
    val configObjectPair = config.loadOrCreateConfig(plugin, TestConfig())
    val configObject = configObjectPair.first!! // it is null when the TestConfig is not presented
    plugin.logger.info(configObject.test) //"test"
}

data class TestConfig(
    val test: String = "test"
) : ConfigObject
```
### Creating custom `DataConverter`
Example of making config that uses custom data type.

```kt
class JsonConverter : DataConverter<JsonObject> { //`JsonObject` is value that is used by whatever data you have chosen
    override val default: JsonObject = JsonObject() //default value
    override val extension: String = "json" //extension of the file

    //converting data to the type of data you have chosen
    override fun convertObject(data: Any): JsonObject {
        return gson.toJsonTree(data).asJsonObject
    } 

    // parsing data from string to the type of data you have chosen
    override fun parseData(string: String): JsonObject {
        return gson.fromJson(string, JsonObject::class.java)
    }

    //converting data to string
    override fun convertToString(data: JsonObject): String = gson.toJson(data)

    //parsing data, it is implemented by default, but it is the best to override it and make it more efficient
    override fun parseData(path: Path): JsonObject {
        val file = path.toFile()
        val reader = JsonReader(FileReader(file))
        return JsonParser.parseReader(reader).asJsonObject
    } 

    companion object {
        private val gson = GsonBuilder().setPrettyPrinting().create()
    } 
}
```