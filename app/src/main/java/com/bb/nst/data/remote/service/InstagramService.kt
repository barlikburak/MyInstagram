package com.bb.nst.data.remote.service

import com.bb.nst.data.model.access_token.response.LongLivedAccessTokenResponse
import com.bb.nst.data.model.user.response.MediaDetailsResponse
import com.bb.nst.data.model.user.response.MediaListResponse
import com.bb.nst.data.model.user.response.UserInfoResponse
import com.bb.nst.utils.common.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface InstagramService {
    /**
     * It is exchanged for a short-term access token or the old long-term access token to
     * obtain the long-term access token.
     * Endpoint and grant_type change to generate new tokens or to renew tokens.
     */
    @GET("/{path}")
    suspend fun getLongLivedAccessToken(
        @Path("path") path: String,
        @Query("client_secret") clientSecret: String = Constants.CLIENT_SECRET,
        @Query("grant_type") grantType: String,
        @Query("access_token") accessToken: String
    ): Response<LongLivedAccessTokenResponse>

    /**
     * to get the user's profile information.
     */
    @GET(Constants.ENDPOINT_USER)
    suspend fun getUserInfoResponse(
        @Query("fields") fields: String,
        @Query("access_token") accessToken: String
    ) : Response<UserInfoResponse>

    /**
     * It takes 2 different parameter values, id, user id and album(children) id.
     * The path takes 2 different parameter values, media and album(children).
     * path = 'media' or 'children'
     */
    @GET("/{id}/{path}")
    suspend fun getMedMediaListResponse(
        @Path("id") id: String,
        @Path("path") path: String,
        @Query("access_token") accessToken: String
    ) : Response<MediaListResponse>

    /**
     * Gets information by media ID.
     */
    @GET("/{media_id}")
    suspend fun getMediaDetailsResponse(
        @Path("media_id") mediaId: String,
        @Query("fields") fields: String,
        @Query("access_token") accessToken: String
    ) : Response<MediaDetailsResponse>
}