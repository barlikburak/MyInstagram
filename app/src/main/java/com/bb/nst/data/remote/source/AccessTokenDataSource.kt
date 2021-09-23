package com.bb.nst.data.remote.source

import com.bb.nst.data.remote.service.InstagramAuthService
import com.bb.nst.data.remote.service.InstagramService
import javax.inject.Inject

/**
 * The functions here return short and long term access tokens.
 */
class AccessTokenDataSource @Inject constructor(
    private val instagramAuthService: InstagramAuthService,
    private val instagramServiceGraphApi: InstagramService
) {
    suspend fun getShortLivedAccessTokenResponse(grantType: String, token: String) =
        instagramAuthService.getShortLivedAccessToken(grantType = grantType, token = token)

    suspend fun getLongLivedAccessTokenResponse(
        path: String, grantType: String, accessToken: String
    ) =
        instagramServiceGraphApi.getLongLivedAccessToken(
            path = path, grantType = grantType, accessToken = accessToken
        )
}