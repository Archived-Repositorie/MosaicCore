package io.github.mosaicmc.mosaiccore.config

import java.nio.file.Path

/**
 * An interface for converting data between different representations.
 *
 * @param T the type of data to be converted.
 */
interface DataConverter<T> {

    /**
     * The default value for the converted data.
     */
    val default: T

    /**
     * The file extension associated with the converted data.
     */
    val extension: String

    /**
     * Converts the specified object to the type T.
     *
     * @param data the object to be converted.
     * @return the converted object of type T.
     */
    fun <Object : ConfigObject> convertObject(data: Object): T

    /**
     * Parses the data from the specified file path.
     *
     * @param path the path to the file containing the data.
     * @return the parsed data of type T.
     */
    fun parseData(path: Path): T = parseData(path.toString())

    /**
     * Parses the data from the specified string.
     *
     * @param string the string containing the data.
     * @return the parsed data of type T.
     */
    fun parseData(string: String): T

    /**
     * Converts the specified data of type T to a string representation.
     *
     * @param data the data to be converted to a string.
     * @return the string representation of the data.
     */
    fun convertToString(data: T): String
}
