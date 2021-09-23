package com.bb.nst.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.bb.nst.R
import com.bb.nst.databinding.ActivitySplashBinding
import com.bb.nst.ui.login.LoginActivity
import com.bb.nst.ui.profile.ProfileActivity
import com.bb.nst.utils.display.Toaster
import com.bb.nst.utils.network.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var splashViewModel: SplashViewModel
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        splashViewModel = ViewModelProvider(this).get(SplashViewModel::class.java)

        // bright mode on
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // start icon animation
        binding.icon.startAnimation(AnimationUtils.loadAnimation(this,
            R.anim.rotate_splash_icon))

        binding.signIn.setOnClickListener(this)

        if(splashViewModel.preferencesService.getAccessToken().isNotEmpty()) {
            val intent = Intent(this, ProfileActivity::class.java)
            if(splashViewModel.preferencesService.getExpiresIn() < 1000) {
                // replace access token
                fetchData()
                startActivity(intent)
                finish()
            } else {
                // open new activity
                binding.signIn.visibility = View.INVISIBLE
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(intent)
                    finish()
                }, 3000)
            }
        }
    }

    private fun fetchData() {
        splashViewModel.fetchLongLivedAccessTokenResponse()
        splashViewModel.response.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    // We register the new token in SharedPreferences.
                    response.data?.let { data ->
                        splashViewModel.preferencesService.setAccessToken(data.access_token)
                        splashViewModel.preferencesService.setTokenType(data.token_type)
                        splashViewModel.preferencesService.setExpiresIn(data.expires_in)
                    }
                }
                is NetworkResult.Error -> {
                    // show error message
                    Toaster.show(applicationContext, response.message, "ERROR")
                }
                is NetworkResult.Loading -> {
                    // Turning off the button display until the new token is loaded.
                    binding.signIn.visibility = View.INVISIBLE
                }
            }
        }
    }

    override fun onClick(p0: View?) {
        p0?.let{ view ->
            when(view.id) {
                // open login activity
                binding.signIn.id -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
        }
    }
}