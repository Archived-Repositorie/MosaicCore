package io.github.mosaicmc.mosaiccore.config

import java.io.File

/**
 * Extended config key
 *
 * Key which stores data about a config file.
 * @param O Config data
 * @param T Coder object
 * @property file The file
 * @property data The data
 * @property coderObject The coder object
 */
data class ExtendedConfigKey<O,T>(
    val file: File,
    val data: O,
    val coderObject: T
) where O : ConfigData
