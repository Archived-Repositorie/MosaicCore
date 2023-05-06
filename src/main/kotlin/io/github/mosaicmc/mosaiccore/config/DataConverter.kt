package io.github.mosaicmc.mosaiccore.config

import java.nio.file.Path
import kotlin.io.path.readText

interface DataConverter<T> {
    fun getDefault(): T
    fun <V> convertData(dataToConvert: T,objectToConvert: V): V

    fun parseData(path: Path): T {
        return parseData(path.readText())
    }

    fun parseData(string: String): T

    fun <V> parseData(path: Path, objectToParse: V): T
}