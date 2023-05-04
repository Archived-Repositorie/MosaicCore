# MosaicCore Documentation
## What is MosaicCore?
MosaicCore is loader/library made as core for MosaicMC to have base to implement features. It can be seen as plugin loader or very extended library for fabric but either way it is made to support server side plugins on fabric loader. It doesn't modify the forge loader at all as the fabric loader is correctly suited for it will break mods (can change in feature). The core itself only access MinecraftServer class to make plugins loading on start of server. It is made purely to work on kotlin, any type with java compatibility won't be seen in official releases.
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
### Registering listener
Registering listener can be done using `EventHandler.registerListener(EventListener)`. Listener needs to implement `Listener` interface.</br>
Example usage:
```kt
fun init() {
  EventHandler.registerListener(EventListener)
}

object EventListener : Listener {
  @Subscriber
  fun test(event: TestEvent)
}
```
### Subscriber
Subscriber is a function inside listener. It subscribes on event which is in the argument of the function. Function cannot have annotation `@JvmStatic` otherwise it errors.
Subscriber can also handle priority and can ignore cancellation.</br>
Example usage: 
```kt
object EventListener : Listener {
  @Subscriber(priority = Priority.HIGHEST, ignoreCancelled = true)
  fun test(event: TestEvent)
}
```
### Custom event
Custom event is class which subscriber can subscribe on through listener. Events can be cancellable by implementing `Cancellable` interface and also can be laggy by annotating `@Laggy`
which will force user to OptIn and also will warn them from usage of the event. They can be called by using `EventHandler.callEvent(TestEvent)`
</br>
Example of simple event:
```kt
fun init() {
  EventHandler.callEvent(TestEvent)
}

class TestEvent : Event() {
    override fun getHandler(): Handler<*> {
        return handler
    }
    
    companion object {  // !! THIS PART IS REQUIRED TO WORK OTHERWISE IT WILL FAIL !!
        private val handler = Handler<TestEvent>()
        fun getHandler(): Handler<TestEvent> {
            return handler
        }
    }
}
```
Example of laggy and cancellable event and usage:
```kt
fun init() {
  @OptIn(Laggy::class) EventHandler.callEvent(TestEvent())
}
fun afterInit() {
  EventHandler.registerListener(EventListener)
}

data EventListener {
  @OptIn(Laggy::class) @Subscriber
  fun test(event: TestEvent)
}

@Laggy
class TestEvent : Event(), CancellableEvent {
    override var cancelled: Boolean = false

    override fun getHandler(): Handler<*> {
        return handler
    }
    
    companion object {  // !! THIS PART IS REQUIRED TO WORK OTHERWISE IT WILL FAIL !!
        private val handler = Handler<TestEvent>()
        fun getHandler(): Handler<TestEvent> {
            return handler
        }
    }
}
```
