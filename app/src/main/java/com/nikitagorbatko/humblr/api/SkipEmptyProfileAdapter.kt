package com.nikitagorbatko.humblr.api

import android.util.Log
import com.nikitagorbatko.humblr.api.pojos.CommentDto
import com.nikitagorbatko.humblr.api.pojos.DataDto
import com.nikitagorbatko.humblr.api.pojos.RepliesDto
import com.squareup.moshi.*
import okio.Buffer
import org.json.JSONObject

class SkipEmptyRepliesAdapter : JsonAdapter<RepliesDto?>() {

    companion object {
        val source = Buffer()
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<RepliesDto> = moshi.adapter(RepliesDto::class.java).lenient()
    }

    @FromJson
    override fun fromJson(reader: JsonReader): RepliesDto? {
        return try {
            adapter.fromJson(reader)
        } catch (_: Exception) {
            null
        }
//        if (reader.peekJson() == JsonReader.Token.STRING) {
//            reader.skipValue()
//            return null
//        }
        return adapter.fromJson(reader)
    }

    override fun toJson(writer: JsonWriter, value: RepliesDto?) {
        TODO("Not yet implemented")
    }
}


//    @FromJson
//    fun fromJson(stringJson: String): RepliesDto? {
//        //return when (reader.peek()) {
//        //JsonReader.Token.BEGIN_OBJECT -> {
//
//
//        //val a = reader.peek()
//        val string =
//            stringJson.replace("replies=,", "replies=null,").replace("replies=,", "replies=null,")
//                .replace("geo_filter=,", "geo_filter=null,")
//        //val resultString = string.replace("replies=,", "replies=null,").replace("geo_filter=,", "geo_filter=null,")
//        Log.d("TAG reader", string)
//
//        source.writeString(string, Charsets.UTF_8)
//
//        val newReader = JsonReader.of(source).also { it.isLenient = true }
//        return null
//        return adapter.fromJson(newReader)
//    }
//
//    override fun toJson(writer: JsonWriter, value: RepliesDto?) {
//        TODO("Not yet implemented")
//    }
//    //else -> null
//    //}
//
////        if (response.replies != "" && response.replies != null) {
////            val string = response.replies.toString()
////            val reader = getJsonReader(string)
////            val commentsResponse = adapter.fromJson(reader)
////            response.parsedReplies = commentsResponse
////        }
////        return response
//}
//
//fun toJson(writer: JsonWriter, value: RepliesDto?) {
//
//}
//
////private fun converter(reader: JsonReader) {
////    reader.beginArray()
////    while (reader.hasNext()) {
////        var kind = ""
////        var data: DataDto? = null
////
////        var after: String? = null
////        var dist: String? = null
////        var children: List<CommentDto>? = null
////        var before: String? = null
////
////        reader.beginObject()
////        while (reader.hasNext()) {
////            when (reader.nextName()) {
////                "kind" -> kind = reader.nextString()
////                "data" -> {
////                    reader.beginObject()
////                    while (reader.hasNext()) {
////                        when (reader.nextName()) {
////                            "after" -> after = reader.nextString()
////                            "dist" -> dist = reader.nextString()
////                            "children" -> {
////                                reader.beginArray()
////                                reader.hasNext()
////                                reader.nextSource()
////                            }
////                            "before" -> before = reader.nextString()
////                            else -> reader.skipValue()
////                        }
////                    }
////                    reader.endObject()
////                }
////                else -> reader.skipValue()
////            }
////        }
////        reader.endObject()
////
////        if (id == -1L || name == "") {
////            throw JsonDataException("Missing required field")
////        }
////        val person = Person(id, name, age)
////        result.add(person)
////    }
////    reader.endArray()
////}
//
////private fun getJsonReader(stringJson: String): JsonReader {
////    source.clear()
////    source.writeString(stringJson, Charsets.UTF_8)
////
////    val reader = JsonReader.of(source).also { it.isLenient = true }
////    return reader
////}
