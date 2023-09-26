package com.example.html5android

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.html5android.databinding.ActivityMainBinding
import java.lang.reflect.InvocationTargetException

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addHtml5Features()
    }

    private fun addHtml5Features() {
        binding.webView.webViewClient = MyClient()
        binding.webView.settings.loadsImagesAutomatically = true
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.setSupportMultipleWindows(true)
        binding.webView.settings.allowFileAccess = true;
        binding.webView.settings.allowContentAccess = true;
        binding.webView.settings.allowFileAccessFromFileURLs = true;
        binding.webView.settings.allowUniversalAccessFromFileURLs = true;
        binding.webView.settings.setSupportZoom(true);
        binding.webView.settings.builtInZoomControls = true;
        binding.webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            try {
                Log.d(TAG, "Enabling HTML5-Features")
                val feature1 =
                    WebSettings::class.java.getMethod(
                        "setDomStorageEnabled",
                        *arrayOf<Class<*>>(java.lang.Boolean.TYPE)
                    )
                feature1.invoke(binding.webView.settings, java.lang.Boolean.TRUE)
                val feature2 =
                    WebSettings::class.java.getMethod(
                        "setDatabaseEnabled",
                        *arrayOf<Class<*>>(java.lang.Boolean.TYPE)
                    )
                feature2.invoke(binding.webView.settings, java.lang.Boolean.TRUE)
                val feature3 =
                    WebSettings::class.java.getMethod(
                        "setDatabasePath", *arrayOf<Class<*>>(
                            String::class.java
                        )
                    )
                feature3.invoke(
                    binding.webView.settings,
                    "/data/data/" + packageName + "/databases/"
                )
                val feature4 =
                    WebSettings::class.java.getMethod(
                        "setAppCacheMaxSize",
                        *arrayOf<Class<*>>(java.lang.Long.TYPE)
                    )
                feature4.invoke(binding?.webView?.settings, 1024 * 1024 * 8)
                val feature5 =
                    WebSettings::class.java.getMethod(
                        "setAppCachePath", *arrayOf<Class<*>>(
                            String::class.java
                        )
                    )
                feature5.invoke(
                    binding?.webView?.settings,
                    "/data/data/" + packageName + "/cache/"
                )
                val feature6 =
                    WebSettings::class.java.getMethod(
                        "setAppCacheEnabled",
                        *arrayOf<Class<*>>(java.lang.Boolean.TYPE)
                    )
                feature6.invoke(binding?.webView?.settings, java.lang.Boolean.TRUE)
                Log.d(TAG, "Enabled HTML5-Features")
            } catch (e: NoSuchMethodException) {
                Log.e(TAG, "NoSuchMethodException", e)
            } catch (e: InvocationTargetException) {
                Log.e(TAG, "InvocationTargetException", e)
            } catch (e: IllegalAccessException) {
                Log.e(TAG, "IllegalAccessException", e)
            }
        }
        var url = "https://dashboardpack.com/live-demo-preview/?livedemo=290?utm_source=colorlib&utm_medium=reactlist&utm_campaign=architecthtml"
        binding.webView.loadUrl(url)

    }

    inner class MyClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            loading()
            return true
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            stopLoading()

        }

        private fun loading() {
            binding.loadingProgress.visibility = View.VISIBLE
        }

        private fun stopLoading() {
            binding.loadingProgress.visibility = View.GONE
        }
    }
}