package top.sukiu.myhhu.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_catch_info.*
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import top.sukiu.myhhu.R
import top.sukiu.myhhu.util.*
import java.util.regex.Pattern


class CatchInfoActivity : AppCompatActivity() {


    private val TAG = ""
    private var currUrl: String = ""
    private var JavaScript: InJavaScriptLocalObj = InJavaScriptLocalObj()

    @SuppressLint("JavascriptInterface", "SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catch_info)
        setSupportActionBar(catch_info_bar)
        supportActionBar?.title = "Fetch Info"
        transportStatusBar(this, window, catch_info_bar)

        webView.loadUrl("http://myold.hhu.edu.cn/login.portal")
        webView.addJavascriptInterface(this, "android")

        webView.webChromeClient = webChromeClient
        webView.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (url == "http://myold.hhu.edu.cn/index.portal")
                    webView.loadUrl("http://form.hhu.edu.cn/pdc/formDesignApi/S/gUTwwojq")
                else webView.loadUrl(url)
                currUrl = url
                LogUtil.i(TAG, " -> $url")
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
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true //允许使用js
        webView.addJavascriptInterface(JavaScript, "java_obj")
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        webSettings.setSupportZoom(true)
        webSettings.builtInZoomControls = true
        webSettings.displayZoomControls = false

        FetchButton.setOnClickListener {
            fetchButtonHandle()
        }
    }


    private fun fetchButtonHandle() {
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
            alert(this, message = "请先点击历史填报")
            return
        }
        val parse = Jsoup.parse(JavaScript.webHtml)
        val infos =
            parse.getElementById("countTableBody").getElementsByTag("tr")[0].getElementsByTag("td")
        lifecycleScope.launch {
            addData("account", muid)
            addData("wid", mwid)
            addData("Name", infos[2].text())
            addData("SelfAccount", infos[3].text())
            addData("Institute", infos[4].text())
            addData("Grade", infos[5].text())
            addData("Major", infos[6].text())
            addData("Classes", infos[7].text())
            addData("Building", infos[8].text())
            addData("Room", infos[9].text())
            addData("PhoneNum", infos[10].text())
            addData("Address", infos[23].text())
        }
        toast("数据获取成功")
        //start(ClockInSetActivity::class.java)
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
        override fun onJsAlert(
            webView: WebView,
            url: String,
            message: String,
            result: JsResult
        ): Boolean {
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

}