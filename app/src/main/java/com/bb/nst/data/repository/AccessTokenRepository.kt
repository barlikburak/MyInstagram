package com.bb.nst.data.repository

import com.bb.nst.data.remote.source.AccessTokenDataSource
import com.bb.nst.data.model.BaseApiResponse
import com.bb.nst.data.model.access_token.response.LongLivedAccessTokenResponse
import com.bb.nst.data.model.access_token.response.ShortLivedAccessTokenResponse
import com.bb.nst.utils.network.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * We created a coroutine model to rotate short and long access tokens.
 * This flow works when it is started to be collected, not when it is created.
 * There are two interfaces here, Flow and FlowCollector.
 * There are suspend functions in both interfaces.
 * The function in the Flow interface has FLowCollector as a parameter.
 * This structure reduces the concurrency error.
 * We specify the type of transaction made with FlowOn.
 * Dispatchers.IO => For network and disk.
 */
@ActivityRetainedScoped
class AccessTokenRepository @Inject constructor(
    private val remoteDataSource: AccessTokenDataSource,
) : BaseApiResponse() {
    suspend fun getShortLivedAccessTokenResponse(
        grantType: String, token: String
    ): Flow<NetworkResult<ShortLivedAccessTokenResponse>> {
        return flow {
            emit(safeApiCall {
                remoteDataSource.getShortLivedAccessTokenResponse(grantType, token)
            })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getLongLivedAccessTokenResponse(
        path: String, grantType: String, accessToken: String
    ): Flow<NetworkResult<LongLivedAccessTokenResponse>> {
        return flow {
            emit(safeApiCall {
                remoteDataSource.getLongLivedAccessTokenResponse(path, grantType, accessToken)
            })
        }.flowOn(Dispatchers.IO)
    }
}