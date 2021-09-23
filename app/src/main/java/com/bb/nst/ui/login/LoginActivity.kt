package com.bb.nst.ui.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bb.nst.data.remote.service.InstagramWebViewClient
import com.bb.nst.databinding.ActivityLoginBinding
import com.bb.nst.ui.profile.ProfileActivity
import com.bb.nst.ui.splash.SplashActivity
import com.bb.nst.utils.common.Constants
import com.bb.nst.utils.callback.LoginCallback
import com.bb.nst.utils.callback.PageCallback
import com.bb.nst.utils.common.InstagramException
import com.bb.nst.utils.display.Toaster
import com.bb.nst.utils.network.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding
    private val scope: String = "user_profile,user_media"
    private val responseType: String = "code"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        init()
    }

    /**
     * WebView Configuration
     */
    private fun init() {
        binding.webView.webViewClient = InstagramWebViewClient(loginCallback, pageCallback)
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.domStorageEnabled = true
        binding.webView.loadUrl(buildUri)
    }

    /**
     * URL Generated
     */
    private val buildUri = Uri.parse(Constants.BASE_URL_FIRST_API
            + Constants.ENDPOINT_OAUTH + Constants.ENDPOINT_AUTH).buildUpon()
        .appendQueryParameter("client_id", Constants.CLIENT_ID)
        .appendQueryParameter("redirect_uri", Constants.REDIRECT_URI)
        .appendQueryParameter("scope", scope)
        .appendQueryParameter("response_type", responseType)
        .build().toString()

    private val loginCallback = object: LoginCallback {
        override fun onSuccess(token: String) {
            // create short lived access token
            // replace long lived access token
            // open new activity
            fetchData(token)
        }

        override fun onError(e: Exception) {
            // show error message
            Toaster.show(applicationContext, e.message, "ERROR")
            startActivity(Intent(this@LoginActivity, SplashActivity::class.java))
            finish()
        }
    }

    private val pageCallback = object: PageCallback {
        // loading activated.
        override fun onLoadingStarted() {
            binding.swipeRefreshLayout.isEnabled = true
            binding.swipeRefreshLayout.isRefreshing = true
        }

        // loading deactivated.
        override fun onLoadingFinished() {
            binding.swipeRefreshLayout.isEnabled = false
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun fetchData(token: String) {
        loginViewModel.fetchShortLivedAccessTokenResponse(token)
        loginViewModel.shortLivedAccessTokenResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    // create short lived access token
                    response.data?.let { data ->
                        data.access_token?.let { accessToken ->
                            loginViewModel.fetchLongLivedAccessTokenResponse(accessToken)
                        }
                    }
                }
                is NetworkResult.Error -> {
                    // show error message
                    loginCallback.onError(InstagramException(response.message.toString()))
                }
                is NetworkResult.Loading -> {
                    // show a loading message
                    Toaster.show(applicationContext, "Loading..", "LOADING")
                }
            }
        }
        loginViewModel.longLivedAccessTokenResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    // replace long lived access token
                    response.data?.let { data ->
                        loginViewModel.preferencesService.setAccessToken(data.access_token)
                        loginViewModel.preferencesService.setTokenType(data.token_type)
                        loginViewModel.preferencesService.setExpiresIn(data.expires_in)
                        startActivity(Intent(this@LoginActivity, ProfileActivity::class.java))
                        finish()
                    }
                }
                is NetworkResult.Error -> {
                    // show error message
                    loginCallback.onError(InstagramException(response.message.toString()))
                }
                is NetworkResult.Loading -> {
                    // show a loading message
                    Toaster.show(applicationContext, "Loading..", "LOADING")
                }
            }
        }
    }
}