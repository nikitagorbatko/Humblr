package com.nikitagorbatko.humblr.data.name

interface NameRepository {
    suspend fun fetchName(): String?
}