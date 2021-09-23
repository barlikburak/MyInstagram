package com.bb.nst.data.local.service

import android.content.SharedPreferences
import javax.inject.Inject

class PreferencesService @Inject constructor(private val prefs: SharedPreferences) {
    /**
     * STATIC
     */
    companion object {
        const val ACCESS_TOKEN = "access_token"
        const val TOKEN_TYPE = "token_type"
        const val EXPIRES_IN = "expires_in"
    }

    /**
     * Retrieving data from SharedPreferences and data is saved.
     */

    fun getAccessToken(): String = prefs.getString(ACCESS_TOKEN, "") ?: ""
    fun setAccessToken(accessToken: String) = prefs.edit().putString(ACCESS_TOKEN, accessToken).apply()

    fun getTokenType(): String = prefs.getString(TOKEN_TYPE, "") ?: ""
    fun setTokenType(tokenType: String) = prefs.edit().putString(TOKEN_TYPE, tokenType).apply()

    fun getExpiresIn(): Long = prefs.getLong(EXPIRES_IN, 0L)
    fun setExpiresIn(expiresIn: Long) = prefs.edit().putLong(EXPIRES_IN, expiresIn).apply()

    fun removingAllPreferences() = prefs.edit().clear().apply()
}