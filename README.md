# MosaicCore Documentation
## What is MosaicCore?
MosaicCore is a core designed as the core for MosaicMC toolchain, providing a foundation for implementing features. 
## Usage
1. Add the Modrinth repository to your `build.gradle` file:
- Groovy
```groovy
repositories {
    // ...
    maven {
        url = "https://api.modrinth.com/maven"
    }
}
```
- Kotlin
```kotlin
repositories {
    maven { url = uri("https://api.modrinth.com/maven") }
}
```
2. Add the dependency to your `build.gradle` file, replacing `{version}` with the latest version found [here](https://modrinth.com/mod/mosaiccore/versions):
```groovy
dependencies {
    // Adding and remapping a mod only in local runtime
    implementation("maven.modrinth:mosaiccore:{version}")
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
2. To create a plugin, you need to add an entrypoint called `plugin` to your `fabric.mod.json` file under the `Entrypoints` key. Replace `plugin.entrypoint.PluginObject` with the value that represents your function/class/object. For alternative methods, refer to the readme of [fabric-language-kotlin](https://github.com/FabricMC/fabric-language-kotlin#entrypoint-samples).
```kt
fun onLoad(plugin: PluginContainer) {
    plugin.logger.info("Hello there!")
}
```

## Event system
### Using DSL Listener
The DSL Listener utilizes Kotlin's DSL feature, which allows for cleaner event handling compared to other event systems. Here's an example usage:
Example usage: 
```kt
fun test(plugin: PluginContainer) = plugin listen {
    subscriber<TestEvent>(SubscriberData(Priority.HIGHEST)) {
        plugin.logger.info("Test event called! 2")
    }
}
```
### Custom event
A custom event is a class to which subscribers can subscribe. Events can be cancellable by implementing the `Cancellable` interface and can be flagged as laggy by annotating `@Laggy`, which requires opting in and warns against its usage. Custom events can be triggered using `TestEvent().call()`.
<br>
It is recommended to register events before plugin load to ensure they are registered before any subscribers. Here's an example of a simple event:
```kt
fun test() {
    TestEvent().call()
}

class TestEvent : Event
```
Example of laggy and cancellable event and usage:
```kt
fun test() { 
    @OptIn(Laggy::class) EventHandler.callEvent(TestEvent())
}

fun pluginInit(plugin: PluginContainer) = plugin listen {
    @OptIn(Laggy::class) 
    subscriber<TestEvent> {
        plugin.logger.info("Test event called! 2")
    }
}

@Laggy
class TestEvent : Event, CancellableEvent {
    override var cancelled: Boolean = false
}
```

