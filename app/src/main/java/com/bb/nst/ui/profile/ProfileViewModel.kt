package com.bb.nst.ui.profile

import android.app.Application
import androidx.lifecycle.*
import com.bb.nst.data.local.service.PreferencesService
import com.bb.nst.data.model.user.response.MediaDetailsResponse
import com.bb.nst.data.model.user.response.MediaListResponse
import com.bb.nst.data.model.user.response.UserInfoResponse
import com.bb.nst.data.repository.UserRepository
import com.bb.nst.utils.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: UserRepository,
    private val _preferencesService: PreferencesService,
    application: Application
) : AndroidViewModel(application) {
    private val fieldsUser = "id,username"
    private val fieldsMedia = "id,media_type,media_url,username,timestamp"

    val preferencesService get() = _preferencesService

    /**
     * When you don't want your data to be modified use LiveData
     * if you want to modify your data later use MutableLiveData
     */

    private val _userInfoResponse: MutableLiveData<NetworkResult<UserInfoResponse>> = MutableLiveData()
    private val _albumListResponse: MutableLiveData<NetworkResult<MediaListResponse>> = MutableLiveData()
    private val _albumResponse: MutableLiveData<NetworkResult<MediaListResponse>> = MutableLiveData()
    private val _mediaDetailsResponse: MutableLiveData<NetworkResult<MediaDetailsResponse>> = MutableLiveData()

    val userInfoResponse: LiveData<NetworkResult<UserInfoResponse>> = _userInfoResponse
    val albumListResponse: LiveData<NetworkResult<MediaListResponse>> = _albumListResponse
    val albumResponse: LiveData<NetworkResult<MediaListResponse>> = _albumResponse
    val mediaDetailsResponse: LiveData<NetworkResult<MediaDetailsResponse>> = _mediaDetailsResponse

    /**
     * By doing 'collect' the coroutine is started and the data is started to be received.
     */

    fun fetchUserInfoResponse(accessToken: String) = viewModelScope.launch {
        repository.getUserInfoResponse(fieldsUser, accessToken).collect { values ->
            _userInfoResponse.value = values
        }
    }

    fun fetchMediaListResponse(id: String, path: String, accessToken: String) = viewModelScope.launch {
        repository.getMediaListResponse(id, path, accessToken).collect { values ->
            when(path) {
                "media" -> _albumListResponse.value = values
                "children" -> _albumResponse.value = values
            }
        }
    }

    fun fetchMediaDetailsResponse(mediaId: String, accessToken: String) = viewModelScope.launch {
        repository.getMediaDetailsResponse(mediaId, fieldsMedia, accessToken).collect { values ->
            _mediaDetailsResponse.value = values
        }
    }
}