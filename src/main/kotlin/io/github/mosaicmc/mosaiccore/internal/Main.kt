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

@file:Suppress("unused")

package io.github.mosaicmc.mosaiccore.internal

import com.mojang.serialization.Codec
import com.mojang.serialization.JsonOps
import com.mojang.serialization.codecs.RecordCodecBuilder
import org.slf4j.LoggerFactory

internal val logger = LoggerFactory.getLogger("mosaicmc")

fun preInit() {
    val codec =
        RecordCodecBuilder.create {
            it.group(
                    Codec.STRING.optionalFieldOf("test", null).forGetter<TestCodec> { it1 ->
                        it1.tester
                    }
                )
                .apply(it, ::TestCodec)
        }
    codec.parse(JsonOps.INSTANCE, null)
}

data class TestCodec(val tester: String?)
