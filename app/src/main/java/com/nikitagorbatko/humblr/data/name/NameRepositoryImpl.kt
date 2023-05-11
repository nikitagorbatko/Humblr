package com.nikitagorbatko.humblr.data.name

import com.nikitagorbatko.humblr.App
import com.nikitagorbatko.humblr.api.services.MeService
import org.koin.mp.KoinPlatform

class NameRepositoryImpl(
    private val token: String,
    private val meService: MeService
) : NameRepository {
    private var name: String? = null

    override suspend fun fetchName(): String? {
        if (name != null) {
            return name
        }
        return try {
            name = meService.getAboutMe(accessToken = token).name
            name
        } catch (_: Exception) {
            null
        }
    }
}