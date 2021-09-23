package com.bb.nst.data.remote.service

import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.bb.nst.utils.common.Constants
import com.bb.nst.utils.callback.LoginCallback
import com.bb.nst.utils.callback.PageCallback
import com.bb.nst.utils.common.InstagramAuthAccessDeniedException
import com.bb.nst.utils.common.InstagramAuthNetworkOperationException

class InstagramWebViewClient(
    private val loginCallback: LoginCallback,
    private val pageCallback: PageCallback
) : WebViewClient() {
    private var errorHappened = false

    /**
     * Works while url is loading.
     */
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        url?.let {
            /**
             * It works if the BASE_URL of the opened website is my website.
             */
            if (Constants.REDIRECT_URI.contains(Uri.parse(url).authority.toString())) {
                if (it.contains("code=")) {
                    // successful
                    loginCallback.onSuccess(
                        it.substring(it.lastIndexOf("code=")+5, it.lastIndexOf("#_")))
                } else if (it.contains("?error")) {
                    // unsuccessful
                    loginCallback.onError(InstagramAuthAccessDeniedException())
                }
                return true
            }
        }
        return false
    }

    /**
     * It works if an error occurs while opening a web page.
     */
    override fun onReceivedError(
        view: WebView?, errorCode: Int, description: String?, failingUrl: String?
    ) {
        errorHappened = true
        loginCallback.onError(InstagramAuthNetworkOperationException())
    }

    /**
     * Runs when a web page is opened.
     */
    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        view?.visibility = View.INVISIBLE
        pageCallback.onLoadingStarted()
    }

    /**
     * Runs when a web page is closed.
     */
    override fun onPageFinished(view: WebView?, url: String?) {
        pageCallback.onLoadingFinished()
        if (!errorHappened) view?.visibility = View.VISIBLE
    }
}