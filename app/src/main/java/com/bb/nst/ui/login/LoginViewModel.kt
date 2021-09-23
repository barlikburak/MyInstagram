package com.bb.nst.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bb.nst.data.local.service.PreferencesService
import com.bb.nst.data.model.access_token.response.LongLivedAccessTokenResponse
import com.bb.nst.data.model.access_token.response.ShortLivedAccessTokenResponse
import com.bb.nst.data.repository.AccessTokenRepository
import com.bb.nst.utils.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AccessTokenRepository,
    private val _preferencesService: PreferencesService,
    application: Application
) : AndroidViewModel(application) {
    val preferencesService get() = _preferencesService

    /**
     * When you don't want your data to be modified use LiveData
     * if you want to modify your data later use MutableLiveData
     */

    private val _shortLivedAccessTokenResponse: MutableLiveData<NetworkResult<ShortLivedAccessTokenResponse>> = MutableLiveData()
    private val _longLivedAccessTokenResponse: MutableLiveData<NetworkResult<LongLivedAccessTokenResponse>> = MutableLiveData()

    val shortLivedAccessTokenResponse: LiveData<NetworkResult<ShortLivedAccessTokenResponse>> = _shortLivedAccessTokenResponse
    val longLivedAccessTokenResponse: LiveData<NetworkResult<LongLivedAccessTokenResponse>> = _longLivedAccessTokenResponse

    /**
     * By doing 'collect' the coroutine is started and the data is started to be received.
     */

    fun fetchShortLivedAccessTokenResponse(token: String) = viewModelScope.launch {
        repository.getShortLivedAccessTokenResponse("authorization_code", token).collect { values ->
            _shortLivedAccessTokenResponse.value = values
        }
    }

    fun fetchLongLivedAccessTokenResponse(accessToken: String) = viewModelScope.launch {
        repository.getLongLivedAccessTokenResponse("access_token", "ig_exchange_token", accessToken).collect { values ->
            _longLivedAccessTokenResponse.value = values
        }
    }
}