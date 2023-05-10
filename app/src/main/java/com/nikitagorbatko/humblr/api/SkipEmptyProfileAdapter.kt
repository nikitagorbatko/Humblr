package com.nikitagorbatko.humblr.api

import android.util.Log
import com.nikitagorbatko.humblr.api.pojos.RepliesDto
import com.squareup.moshi.*
import okio.Buffer

class SkipEmptyRepliesAdapter: JsonAdapter<RepliesDto?>() {

    companion object {
        val source = Buffer()
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<RepliesDto> = moshi.adapter(RepliesDto::class.java).lenient()
    }

    @FromJson
    override fun fromJson(reader: JsonReader): RepliesDto? {
        return when(reader.peek()) {
            JsonReader.Token.BEGIN_OBJECT -> {
                val string = reader.readJsonValue().toString()
                Log.d("TAG reader", string)
                adapter.fromJson(string)
            }
            else -> null
        }

//        if (response.replies != "" && response.replies != null) {
//            val string = response.replies.toString()
//            val reader = getJsonReader(string)
//            val commentsResponse = adapter.fromJson(reader)
//            response.parsedReplies = commentsResponse
//        }
//        return response
    }

    override fun toJson(writer: JsonWriter, value: RepliesDto?) {

    }

    private fun getJsonReader(stringJson: String): JsonReader {
        source.clear()
        source.writeString(stringJson, Charsets.UTF_8)

        val reader = JsonReader.of(source).also { it.isLenient = true }
        return reader
    }
}