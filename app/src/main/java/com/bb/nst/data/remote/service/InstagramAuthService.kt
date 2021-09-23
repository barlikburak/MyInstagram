package com.bb.nst.data.remote.service

import com.bb.nst.data.model.access_token.response.ShortLivedAccessTokenResponse
import com.bb.nst.utils.common.Constants
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface InstagramAuthService {
    /**
     * We obtain a short-term access token by submitting the following data in form type.
     */
    @FormUrlEncoded
    @POST("/oauth/access_token")
    suspend fun getShortLivedAccessToken(
        @Field("client_id") clientId: String = Constants.CLIENT_ID,
        @Field("client_secret") clientSecret: String = Constants.CLIENT_SECRET,
        @Field("redirect_uri") redirectUri: String = Constants.REDIRECT_URI,
        @Field("grant_type") grantType: String,
        @Field("code") token: String
    ): Response<ShortLivedAccessTokenResponse>
}