package com.bb.nst.data.repository

import com.bb.nst.data.model.BaseApiResponse
import com.bb.nst.data.model.user.response.MediaDetailsResponse
import com.bb.nst.data.model.user.response.MediaListResponse
import com.bb.nst.data.model.user.response.UserInfoResponse
import com.bb.nst.data.remote.source.UserDataSource
import com.bb.nst.utils.network.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * We created a coroutine model to return user information and media.
 * This flow works when it is started to be collected, not when it is created.
 * There are two interfaces here, Flow and FlowCollector.
 * There are suspend functions in both interfaces.
 * The function in the Flow interface has FLowCollector as a parameter.
 * This structure reduces the concurrency error.
 * We specify the type of transaction made with FlowOn.
 * Dispatchers.IO => For network and disk.
 */
@ActivityRetainedScoped
class UserRepository @Inject constructor(
    private val remoteDataSource: UserDataSource
) : BaseApiResponse() {
    suspend fun getUserInfoResponse(
        fields: String, accessToken: String
    ): Flow<NetworkResult<UserInfoResponse>> {
        return flow {
            emit(safeApiCall {
                remoteDataSource.getUserInfoResponse(fields, accessToken)
            })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMediaListResponse(
        id: String, path: String, accessToken: String
    ): Flow<NetworkResult<MediaListResponse>> {
        return flow {
            emit(safeApiCall {
                remoteDataSource.getMediaListResponse(id, path, accessToken)
            })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMediaDetailsResponse(
        mediaId: String, fields: String, accessToken: String
    ): Flow<NetworkResult<MediaDetailsResponse>> {
        return flow {
            emit(safeApiCall {
                remoteDataSource.getMediaDetailsResponse(mediaId, fields,  accessToken)
            })
        }.flowOn(Dispatchers.IO)
    }
}