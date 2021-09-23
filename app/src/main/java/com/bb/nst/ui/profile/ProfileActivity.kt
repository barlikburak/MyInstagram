package com.bb.nst.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bb.nst.data.model.user.response.MediaDetailsResponse
import com.bb.nst.databinding.ActivityProfileBinding
import com.bb.nst.ui.profile.adapter.MediaAdapter
import com.bb.nst.utils.network.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.widget.Toolbar
import com.bb.nst.R
import com.bb.nst.data.model.user.response.MediaListResponse
import com.bb.nst.data.model.user.response.UserInfoResponse
import com.bb.nst.ui.splash.SplashActivity
import com.bb.nst.utils.display.Toaster

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener {
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var binding: ActivityProfileBinding
    private var albumList = ArrayList<ArrayList<MediaDetailsResponse>>()
    private var accessToken = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        init()
        observeLiveData()
    }

    private fun init() {
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        accessToken = profileViewModel.preferencesService.getAccessToken()
        profileViewModel.fetchUserInfoResponse(accessToken)

        // The menu and its functions have been created.
        binding.topAppBar.inflateMenu(R.menu.menu_option)
        binding.topAppBar.setOnMenuItemClickListener(this)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.signOut -> signOut()
            else -> super.onOptionsItemSelected(item!!)
        }
    }

    private fun signOut(): Boolean {
        // All data in SharedPreferences has been deleted.
        profileViewModel.preferencesService.removingAllPreferences()
        startActivity(Intent(this, SplashActivity::class.java))
        finish()
        return true
    }

    private fun observeLiveData() {
        profileViewModel.userInfoResponse.observe(this) {
            fetchData(it as NetworkResult<Any>,1)
        }
        profileViewModel.albumListResponse.observe(this) {
            fetchData(it as NetworkResult<Any>,2)
        }
        profileViewModel.albumResponse.observe(this) {
            fetchData(it as NetworkResult<Any>,3)
        }
        profileViewModel.mediaDetailsResponse.observe(this) {
            fetchData(it as NetworkResult<Any>,4)
        }
    }

    private fun fetchData(response: NetworkResult<Any>, dataId: Int) {
        when(response) {
            is NetworkResult.Success -> {
                response.data?.let { data ->
                    when(dataId) {
                        1 -> {// userInfoResponse
                            data as UserInfoResponse
                            profileViewModel.fetchMediaListResponse(data.id, "media", accessToken)
                            binding.topAppBar.title = data.username
                        }
                        2 -> {// albumListResponse
                            data as MediaListResponse
                            for (post in data.data) {
                                profileViewModel.fetchMediaDetailsResponse(post.id, accessToken)
                            }
                        }
                        3 -> {// albumResponse
                            data as MediaListResponse
                            for (post in data.data) {
                                profileViewModel.fetchMediaDetailsResponse(post.id, accessToken)
                            }
                        }
                        4 -> {// mediaDetailsResponse
                            parseMediaDetails(data as MediaDetailsResponse)
                        }
                        else -> Toaster.show(applicationContext, response.message, "ERROR")
                    }
                }
            }
            is NetworkResult.Error -> {
                // show error message
                Toaster.show(applicationContext, response.message, "ERROR")
            }
            is NetworkResult.Loading -> {
                // show loading
                Toaster.show(applicationContext, "Loading..", "ERROR")
            }
        }
    }

    private fun parseMediaDetails(data: MediaDetailsResponse) {
        if(data.media_type=="CAROUSEL_ALBUM") {
            profileViewModel.fetchMediaListResponse(data.id, "children", accessToken)
        }else {
            var isNotEquals = true
            for(node in albumList) {
                if(node[0].timestamp==data.timestamp) {
                    isNotEquals = false
                    node.add((MediaDetailsResponse(data.id, data.media_type, data.media_url, data.username, data.timestamp)))
                }
            }
            if(isNotEquals) {
                albumList.add(arrayListOf(MediaDetailsResponse(data.id, data.media_type, data.media_url, data.username, data.timestamp)))
            }
            // Show RecyclerView
            binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)
            binding.recyclerView.adapter = MediaAdapter(applicationContext, albumList)
        }
    }
}