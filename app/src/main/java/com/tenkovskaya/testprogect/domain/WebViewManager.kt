package com.tenkovskaya.testprogect.domain

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi

class WebViewManager(private val context: Context) {
    private var webView: WebView? = null

    fun getWebView(): WebView {
        if (webView == null) {
            webView = WebView(context)
        }
        return webView!!
    }

    fun initWebView() {
        val webView = getWebView()
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true

        webView.webViewClient = object : WebViewClient() {
            override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
                super.onReceivedHttpError(view, request, errorResponse)
                Log.e("TAGGG", "Received HTTP error: ${errorResponse?.statusCode}")
            }

            @RequiresApi(Build.VERSION_CODES.M)
            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                Log.e("TAGGG", "Received WebView error: ${error?.description}")
            }
        }
    }

    fun loadUrl(url: String) {
        webView?.loadUrl(url)
        Log.d("TAGGG", "Loaded URL: $url")
    }

    fun onSaveInstanceState(outState: Bundle) {
        webView?.saveState(outState)
        Log.d("TAGGG", "Saved WebView state")
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle) {
        if (webView == null) {
            webView = getWebView()
        }
        webView?.restoreState(savedInstanceState)
        Log.d("TAGGG", "Restored WebView state")
    }
}
