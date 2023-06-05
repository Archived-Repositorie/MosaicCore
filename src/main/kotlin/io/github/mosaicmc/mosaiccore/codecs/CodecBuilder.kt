/*
 * Copyright (c) 2023. JustFoxx
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package io.github.mosaicmc.mosaiccore.codecs

import kotlin.reflect.full.createInstance

class CodecBuilder {
    inline fun <reified C : Codec<*>> field(name: String, noinline block: FieldBuilder<C>.(C) -> Unit) {
        val codecInstance = C::class.createInstance()
        FieldBuilder(name, codecInstance).apply {
            block(this, codecInstance)
        }
    }
}

open class Codec<T>

enum class CodecType {
    STRING,
    NUMBER,
    LIST
}

interface Codecs {
    class StringCodec : Codec<String>()

    class NumberCodec : Codec<Number>()

    class ListCodec<T> : Codec<List<T>>()
}

class FieldBuilder <C> (private val name: String, private val codecInstance: C) : Comparable<FieldBuilder<*>> {
    var alias: Set<String> = setOf()

    override fun compareTo(other: FieldBuilder<*>): Int {
        return name.compareTo(other.name) + alias.contains(other.name).compareTo(false)
    }
}

fun codecBuilder(block: CodecBuilder.() -> Unit) = CodecBuilder().apply(block)

fun test() = codecBuilder {
    field<Codecs.StringCodec>("name") {
        alias = setOf("name", "name")
    }
}