package com.tenkovskaya.testprogect.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.tenkovskaya.testprogect.R
import com.tenkovskaya.testprogect.domain.FirebaseManager
import com.tenkovskaya.testprogect.domain.LocationManager
import com.tenkovskaya.testprogect.domain.SharedPreferencesManager
import com.tenkovskaya.testprogect.domain.WebViewManager

class LoaderActivity : AppCompatActivity() {

    private lateinit var preferencesManager: SharedPreferencesManager
    private lateinit var webViewManager: WebViewManager
    private lateinit var firebaseManager: FirebaseManager
    private lateinit var locationManager: LocationManager
    private lateinit var webView: WebView

    private var link = ""
    private var countryCodeFromApi = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loader)

        Log.d("TAGGG", "onCreate")

        preferencesManager = SharedPreferencesManager(this)
        webViewManager = WebViewManager(this)
        firebaseManager = FirebaseManager()
        locationManager = LocationManager()
        webView = webViewManager.getWebView()

        if (preferencesManager.getLastVisitedPage().isNotEmpty()) {
            webViewManager.initWebView()
            setContentView(webView)
            Log.d("TAGGG", "preferencesManager.getLastVisitedPage().isNotEmpty()")

        } else {
            fetchAndHandleDataFromFirebase()
            Log.d("TAGGG", "fetchAndHandleDataFromFirebase")

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        webViewManager.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        webViewManager.onRestoreInstanceState(savedInstanceState)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val webView = webViewManager.getWebView()
        if (webView != null) {
            if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                webView.goBack()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }


    private fun fetchAndHandleDataFromFirebase() {
        firebaseManager.fetchDataFromFirebase { data ->
            if (data.workStatus) {

                locationManager.getCountry { countryCode ->
                    countryCodeFromApi = countryCode
                    val countryCodeFromFirebase = data.geo
                    Log.d("TAGGG", "Country code from API: $countryCodeFromApi")
                    Log.d("TAGGG", "Country code from Firebase: $countryCodeFromFirebase")

                    if (countryCodeFromApi == countryCodeFromFirebase) {
                        link = data.organics
                        Log.d("TAGGG", "countryCodeFromApi == countryCodeFromFirebase $link")
                        openWebView()
                    } else {
                            goToNextActivity()
                    }
                }
            } else {
                    goToNextActivity()
            }
        }
    }

    private fun openWebView() {

        if (link.isNullOrEmpty()) {
            Log.d("TAGGG", "openWebView goToNextActivity()")
            goToNextActivity()
        } else {
            runOnUiThread {
                Log.d("TAGGG", "openWebView openWebView")
                preferencesManager.saveLastVisitedPage(link)
                Log.d("TAGGG", "openWebView $link")
                webViewManager.loadUrl(link)
                setContentView(webView)
            }
        }
    }



    private fun goToNextActivity() {
        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
        finish()
    }
}