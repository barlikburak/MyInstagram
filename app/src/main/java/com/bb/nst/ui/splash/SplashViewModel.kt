package com.bb.nst.ui.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bb.nst.data.local.service.PreferencesService
import com.bb.nst.data.model.access_token.response.LongLivedAccessTokenResponse
import com.bb.nst.data.repository.AccessTokenRepository
import com.bb.nst.utils.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: AccessTokenRepository,
    private val _preferencesService: PreferencesService,
    application: Application
) : AndroidViewModel(application) {
    private val grantType = "ig_refresh_token"
    private val path = "refresh_access_token"

    val preferencesService get() = _preferencesService

    /**
     * When you don't want your data to be modified use LiveData
     * if you want to modify your data later use MutableLiveData
     */
    private val _response: MutableLiveData<NetworkResult<LongLivedAccessTokenResponse>> = MutableLiveData()
    val response: LiveData<NetworkResult<LongLivedAccessTokenResponse>> = _response

    /**
     * We started by making 'collect' to get the long term access code.
     */
    fun fetchLongLivedAccessTokenResponse() = viewModelScope.launch {
        repository.getLongLivedAccessTokenResponse(
            path, grantType, preferencesService.getAccessToken()
        ).collect { values ->
            _response.value = values
        }
    }
}