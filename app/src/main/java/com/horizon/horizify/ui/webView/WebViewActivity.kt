package com.horizon.horizify.ui.webView

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.horizon.horizify.base.SharedPreference
import com.horizon.horizify.databinding.ActivityCheckInBinding
import com.horizon.horizify.extensions.get
import com.horizon.horizify.utils.Constants.CHECK_IN_URL
import com.horizon.horizify.utils.Constants.GIVING_URL
import com.horizon.horizify.utils.Constants.WEB_URL

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckInBinding

    private var prefs: SharedPreference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpWebView()

        binding.back.setOnClickListener { finish() }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Check if the key event was the Back button and if there's history
        if (keyCode == KeyEvent.KEYCODE_BACK && binding.webView.canGoBack()) {
            binding.webView.goBack()
            return true
        }
        // If it wasn't the Back key or there's no web page history, exit the activity)
        return super.onKeyDown(keyCode, event)
    }

    private fun getWebUrl(): String {
        prefs = SharedPreference(this)
        return prefs!!.get(WEB_URL, "")
    }

    private fun setUpWebView() {
        val url = getWebUrl()
        with(binding.webView) {
            setLayerType(View.LAYER_TYPE_HARDWARE, null)
            with(settings) {
                databaseEnabled = true
                allowFileAccess = true
                allowContentAccess = true
                javaScriptEnabled = true
                loadWithOverviewMode = true
                useWideViewPort = true
                setSupportZoom(false)
                domStorageEnabled = true
                builtInZoomControls = false
            }
            loadUrl(url)
        }
        when(url){
            CHECK_IN_URL -> binding.txtTitle.text = "WELCOME"
            GIVING_URL -> binding.txtTitle.text = "GIVING"
        }
    }
}