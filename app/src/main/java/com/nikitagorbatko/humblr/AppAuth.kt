package com.nikitagorbatko.humblr

import android.content.Intent
import android.net.Uri
import net.openid.appauth.*
import kotlin.coroutines.suspendCoroutine

object AppAuth {
    private val authorizationUri = Uri.parse("https://reddit.com/api/v1/authorize")
    private val tokenUri = Uri.parse("https://www.reddit.com/api/v1/access_token")
    private const val SCOPES =
        "identity edit flair history modconfig modflair modlog modposts modwiki mysubreddits" +
                " privatemessages read report save submit subscribe vote wikiedit wikiread"
    private const val CLIENT_ID = "f937EmzoWrkPXuGy3Xb76g"
    private const val CLIENT_SECRET = ""
    private val redirectUri = Uri.parse("com.nikitagorbatko.humblr://reddit")
    private val logoutUri = Uri.parse("https://www.reddit.com/api/v1/revoke_token")
    private const val STATE = "com.nikitagorbatko.humblr:state"
    private const val GRANT_TYPE = "authorization_code"

    private val serviceConfig = AuthorizationServiceConfiguration(
        authorizationUri,
        tokenUri,
        null,
        logoutUri
    )

    val authRequest: AuthorizationRequest =
        AuthorizationRequest.Builder(
            serviceConfig,
            CLIENT_ID,
            ResponseTypeValues.CODE,
            redirectUri
        ).setScopes(SCOPES)
            .setAdditionalParameters(mapOf("duration" to "permanent"))
            .setState(STATE)
            .build()

    fun prepareTokenRequest(intent: Intent): TokenRequest {
        val code = intent.dataString?.substringAfter("code=")?.substringBefore('#')
        return TokenRequest.Builder(serviceConfig, CLIENT_ID)
            .setRedirectUri(redirectUri)
            .setGrantType(GRANT_TYPE)
            .setAuthorizationCode(code)
            .build()
    }

    suspend fun performTokenRequestSuspend(
        authService: AuthorizationService,
        tokenRequest: TokenRequest,
    ): String {
        return suspendCoroutine { continuation ->
            authService.performTokenRequest(
                tokenRequest,
                ClientSecretBasic(CLIENT_SECRET)
            ) { response, ex ->
                when {
                    response != null -> {
                        val token = response.accessToken.orEmpty()
                        continuation.resumeWith(Result.success(token))
                    }
                    ex != null -> {
                        continuation.resumeWith(Result.failure(ex))
                    }
                    else -> error("unreachable")
                }
            }
        }
    }
}