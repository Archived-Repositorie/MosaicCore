package io.github.mosaicmc.mosaiccore.utils

@Suppress("unused")
fun String.formater(format: HashMap<String,Any>): String {
    var result = this
    for (pair in format) {
        result = result.replace(pair.key, pair.value.toString())
    }
    return result
}
