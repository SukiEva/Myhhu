package top.sukiu.myhhu.activity

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.webkit.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_catch_info.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.alert
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jsoup.Jsoup
import top.sukiu.myhhu.R
import java.util.regex.Pattern


class CatchInfoActivity : AppCompatActivity() {

    private val log = AnkoLogger<CatchInfoActivity>()
    private var currUrl: String = ""
    var sp: SharedPreferences? = null
    private var JavaScript: InJavaScriptLocalObj = InJavaScriptLocalObj()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TransportStatusBar()
        setContentView(R.layout.activity_catch_info)
        setSupportActionBar(catch_info_bar)
        supportActionBar?.title = "Fetch Info"

        sp = this.getSharedPreferences("dakainfo", Context.MODE_PRIVATE)


        webView.loadUrl("http://my.hhu.edu.cn/login.portal")
        webView.addJavascriptInterface(this, "android")

        webView.webChromeClient = webChromeClient
        webView.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (url == "http://my.hhu.edu.cn/index.portal")
                    webView.loadUrl("http://form.hhu.edu.cn/pdc/formDesignApi/S/gUTwwojq")
                else webView.loadUrl(url)
                currUrl = url
                log.info { "->" + url }
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                view!!.loadUrl(
                    "javascript:window.java_obj.getSource("
                            + "document.getElementsByTagName('html')[0].innerHTML);"
                )
                super.onPageFinished(view, url)
            }
        })
        val webSettings: WebSettings = webView.getSettings()
        webSettings.javaScriptEnabled = true //允许使用js
        webView.addJavascriptInterface(JavaScript, "java_obj")
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        webSettings.setSupportZoom(true)
        webSettings.setBuiltInZoomControls(true)
        webSettings.setDisplayZoomControls(false)

        FetchButton.setOnClickListener {
            FetchButtonHandle()
        }
    }


    private fun FetchButtonHandle() {
        val mwid: String
        val muid: String
        val reg_wid = "(?<=selfFormWid=).*(?=&)"
        val reg_uid = "(?<=lwUserId=).*"
        val matcher_wid = Pattern.compile(reg_wid).matcher(currUrl)
        val matcher_uid = Pattern.compile(reg_uid).matcher(currUrl)
        if (matcher_uid.find() && matcher_wid.find()) {
            muid = matcher_uid.group()
            mwid = matcher_wid.group()
        } else {
            alert("请先点击历史填报") { positiveButton("٩('ω')وget！") {} }.show()
            return
        }
        val parse = Jsoup.parse(JavaScript.webHtml)
        val infos = parse.getElementById("countTableBody").getElementsByTag("tr")[0].getElementsByTag("td")
        val editor = sp!!.edit()
        try {
            editor.putString("account", muid)
            editor.putString("wid", mwid)
            editor.putString("Name", infos[2].text())
            editor.putString("SelfAccount", infos[3].text())
            editor.putString("Institute", infos[4].text())
            editor.putString("Grade", infos[5].text())
            editor.putString("Major", infos[6].text())
            editor.putString("Classes", infos[7].text())
            editor.putString("Building", infos[8].text())
            editor.putString("Room", infos[9].text())
            editor.putString("PhoneNum", infos[10].text())
            editor.putString("Address", infos[23].text())
            editor.commit()
        } catch (e: Exception) {
            alert("出错啦，请到Issues反馈") { positiveButton("٩('ω')وget！") {} }.show()
        }
        startActivity<ClockInSetActivity>()
        finish()
    }


    class InJavaScriptLocalObj {
        var webHtml: String? = null

        @JavascriptInterface
        fun getSource(html: String?) {
            webHtml = html
        }
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

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (webView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK) { //点击返回按钮的时候判断有没有上一页
            webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        webView.destroy()
    }


    @Suppress("DEPRECATION")
    private fun TransportStatusBar() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(Color.TRANSPARENT)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}