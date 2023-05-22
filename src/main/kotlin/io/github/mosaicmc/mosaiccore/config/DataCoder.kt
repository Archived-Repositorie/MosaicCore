@file:Suppress("KDocMissingDocumentation", "UNUSED")
package io.github.mosaicmc.mosaiccore.config

import java.io.File

interface DataCoder<T> {
    val extension: String
    val default: T

    fun <O : ConfigData> convertObject(data: O): T

    fun <O : ConfigData> convertToObject(data: T, clazz: Class<O>): O

    fun decodeFile(file: File): T

    fun encodeToFile(data: T, file: File)

    fun validateFile(file: File): Boolean
}
