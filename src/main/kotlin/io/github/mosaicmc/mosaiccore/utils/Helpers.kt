package io.github.mosaicmc.mosaiccore.utils

/**
 * Formats a string.
 *
 * @param format The format.
 * @return The formatted string.
 */
@Suppress("unused")
fun String.formater(format: HashMap<String,Any>): String {
    var result = this
    for ((key, value) in format) {
        result = result.replace(key, value.toString())
    }
    return result
}
