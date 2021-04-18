package top.sukiu.myhhu.activity

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_service.*
import top.sukiu.myhhu.R
import top.sukiu.myhhu.util.LogUtil
import top.sukiu.myhhu.util.browse
import top.sukiu.myhhu.util.transportStatusBar


class ServiceActivity : AppCompatActivity() {

    var webUrl: String? = null

    @SuppressLint("JavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_service)
        setSupportActionBar(service_bar)
        transportStatusBar(this, window, service_bar)

        webUrl = intent.getStringExtra("weburl")
        LogUtil.i("ServiceActivity", " -> " + webUrl)
        try {
            web.loadUrl(webUrl!!)
        } catch (e: Exception) {
            LogUtil.d("ServiceActivity", "LoadUrl Error")
        }
        web.addJavascriptInterface(this, "android")

        web.webChromeClient = webChromeClient
        web.setWebViewClient(object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) { //页面加载完成
                progressBar.visibility = View.GONE
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) { //页面开始加载
                progressBar.visibility = View.VISIBLE
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                web.loadUrl(url)
                return true
            }
        })
        val webSettings: WebSettings = web.settings
        webSettings.javaScriptEnabled = true //允许使用js
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        webSettings.setSupportZoom(true)
        webSettings.setBuiltInZoomControls(true)
        webSettings.setDisplayZoomControls(false)
    }

    //WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
    private val webChromeClient: WebChromeClient = object : WebChromeClient() {
        override fun onJsAlert(webView: WebView, url: String, message: String, result: JsResult): Boolean {
            val localBuilder = AlertDialog.Builder(webView.context)
            localBuilder.setMessage(message).setPositiveButton("确定", null)
            localBuilder.setCancelable(false)
            localBuilder.create().show()
            result.confirm()
            return true
        }

        //获取网页标题
        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            supportActionBar?.title = title
        }

        //加载进度回调
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            progressBar.progress = newProgress
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (web.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK) { //点击返回按钮的时候判断有没有上一页
            web.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        web.destroy()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.broser -> browse(webUrl!!)
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.service_bar_menu, menu)
        return true
    }
}