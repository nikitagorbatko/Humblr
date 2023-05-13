package com.nikitagorbatko.humblr.api

import com.nikitagorbatko.humblr.api.pojos.RepliesDto
import com.squareup.moshi.*
import okio.Buffer

class SkipEmptyRepliesAdapter : JsonAdapter<RepliesDto?>() {

    companion object {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<RepliesDto> = moshi.adapter(RepliesDto::class.java)
    }

    @FromJson
    override fun fromJson(reader: JsonReader): RepliesDto? {
        val result: RepliesDto?
        try {
            val localReader = reader.peekJson()
            result = adapter.fromJson(localReader)
        } catch (_: Exception) {
            reader.skipValue()
            return null
        }
        return if (result != null) {
            adapter.fromJson(reader)
        } else {
            null
        }
    }

    override fun toJson(writer: JsonWriter, value: RepliesDto?) {
        TODO("Not yet implemented")
    }
}