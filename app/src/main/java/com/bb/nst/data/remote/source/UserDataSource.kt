package com.bb.nst.data.remote.source

import com.bb.nst.data.remote.service.InstagramService
import javax.inject.Inject

/**
 * The functions here return the user's information and shared media.
 */
class UserDataSource @Inject constructor(
    private val instagramServiceGraphApi: InstagramService
) {
    suspend fun getUserInfoResponse(fields: String, accessToken: String) =
        instagramServiceGraphApi.getUserInfoResponse(fields = fields, accessToken = accessToken)

    suspend fun getMediaListResponse(id: String, path: String, accessToken: String) =
        instagramServiceGraphApi.getMedMediaListResponse(
            id = id, path = path, accessToken = accessToken
        )

    suspend fun getMediaDetailsResponse(mediaId: String, fields: String, accessToken: String) =
        instagramServiceGraphApi.getMediaDetailsResponse(
            mediaId = mediaId, fields = fields, accessToken = accessToken
        )
}