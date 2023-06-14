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
package io.github.mosaicmc.mosaiccore.depracted.codecs
@Deprecated("Gonna be moved into different library", level = DeprecationLevel.HIDDEN)
fun deprecate() {}

//import com.google.gson.Gson
//import com.google.gson.JsonElement
//import com.google.gson.JsonObject
//import com.google.gson.JsonParser
//
//
//fun <T : Codec<*>> codecCreator(codec: T, block: T.() -> Unit): T = codec.apply(block)
//
//@Suppress("UNCHECKED_CAST")
//abstract class Codec<T : Any>(private val default: T) {
//    private var map: (T?) -> Any = { default }
//    var nullable: Boolean = false
//
//    fun <V : Any> map(block: (T?) -> V) {
//        map = block
//    }
//
//    fun <V : Any> getMap(data: T?): V? {
//        return map(data) as? V
//    }
//
//    abstract fun decode(data: JsonElement?): T
//
//    abstract fun encode(data: T?): JsonObject?
//
//    fun checkNullable(data: JsonElement) {
//        if (!nullable && data.isJsonNull) {
//            throw NullPointerException("Value cannot be null")
//        }
//    }
//
//    abstract fun validation(data: JsonElement)
//}
//
//interface Codecs {
//    class StringCodec(default: String = "") : Codec<String>(default) {
//        override fun decode(data: JsonElement?): String? {
//            return data?.asString
//        }
//
//        override fun validation(data: JsonElement) {
//
//        }
//
//        override fun encode(data: String?): JsonObject {
//            return JsonObject().getAsJsonObject(data)
//        }
//
//    }
//
//    class ObjectCodec(var default: Any = Any()) : Codec<Any> {
//        private val fields = mutableSetOf<Field<*>>()
//        override fun decode(data: JsonElement?) {
//            TODO("Not yet implemented")
//        }
//
//        override fun validation(data: JsonElement) {
//            TODO("Not yet implemented")
//        }
//
//        override fun encode(data: Any?): JsonObject? {
//            TODO("Not yet implemented")
//        }
//
//        fun <C : Codec<*>> field(name: String, codec: C, block: Field<C>.(C) -> Unit) {
//            val field = Field(name, codec).apply { block(codec) }
//            fields.add(field)
//        }
//    }
//}
//
//class Field<C : Codec<*>>(private val name: String, val codec: C) : Comparable<Field<*>> {
//    var alias: Set<String> = setOf()
//
//    override fun compareTo(other: Field<*>): Int {
//        return name.compareTo(other.name) + alias.contains(other.name).compareTo(false)
//    }
//}
//
//fun test() = codecCreator(Codecs.ObjectCodec()) {
//    field("test", Codecs.StringCodec()) {
//        alias = setOf("test2")
//        it.nullable = true
//        it.map { i -> i!! }
//    }
//}