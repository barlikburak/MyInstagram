package com.bb.nst.utils.callback

/**
 * The interface to be used in the login process.
 */
interface LoginCallback {
    fun onSuccess(token: String)
    fun onError(e: Exception)
}