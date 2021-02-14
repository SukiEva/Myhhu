package top.sukiu.myhhu.activity

import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_service.*
import top.sukiu.myhhu.R


class ServiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TransportStatusBar()
        setContentView(R.layout.activity_service)
        setSupportActionBar(service_bar)

        web.loadUrl("http://www.hhu.edu.cn/xyfw/list.htm")
        web.addJavascriptInterface(this, "android")

        web.webChromeClient = webChromeClient
//        web.setWebViewClient(object : WebViewClient() {
//            override fun onPageFinished(view: WebView?, url: String?) { //页面加载完成
//                progressBar.visibility = View.GONE
//            }
//            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) { //页面开始加载
//                progressBar.visibility = View.VISIBLE
//            }
//            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
//                web.loadUrl(url)
//                return true
//            }
//        })
        val webSettings: WebSettings = web.getSettings()
        webSettings.javaScriptEnabled = true //允许使用js
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE)
        webSettings.setSupportZoom(true)
        webSettings.setBuiltInZoomControls(true)
    }


    //WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
    private val webChromeClient: WebChromeClient = object : WebChromeClient() {
        //不支持js的alert弹窗，需要自己监听然后通过dialog弹窗
        override fun onJsAlert(webView: WebView, url: String, message: String, result: JsResult): Boolean {
            val localBuilder = AlertDialog.Builder(webView.context)
            localBuilder.setMessage(message).setPositiveButton("确定", null)
            localBuilder.setCancelable(false)
            localBuilder.create().show()
            //注意:
            //必须要这一句代码:result.confirm()表示:
            //处理结果为确定状态同时唤醒WebCore线程
            //否则不能继续点击按钮
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
        //Log.i("ansen", "是否有上一个页面:" + webView.canGoBack())
        if (web.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK) { //点击返回按钮的时候判断有没有上一页
            web.goBack() // goBack()表示返回webView的上一页面
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        //释放资源
        web.destroy()
    }

    @Suppress("DEPRECATION")
    private fun TransportStatusBar() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(Color.TRANSPARENT)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}