package io.github.mosaicmc.mosaiccore.config

import java.io.File

/**
 * Config key
 *
 * Key which stores data about a config file.
 * @param T Coder object
 * @property file The file
 * @property data The data
 * @property coderObject The coder object
 */
data class ConfigKey<T>  (
    val file: File,
    val coderObject: T
)