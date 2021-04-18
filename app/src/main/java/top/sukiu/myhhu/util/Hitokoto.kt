package top.sukiu.myhhu.util

import android.os.Handler
import android.os.Message
import org.jsoup.Jsoup


fun getHitokoto(handler: Handler) {
    Thread {
        try {
            val connect = Jsoup.connect("https://v1.hitokoto.cn/?encode=text&c=i")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4209.2 Safari/537.36")
                .timeout(2000)
                .ignoreContentType(true)
            val meg = Message.obtain()
            meg.what = 111
            meg.obj = connect.get().text()
            handler.sendMessage(meg)
        } catch (e: Exception) {
            LogUtil.d("Hitoko", e.stackTrace.toString())
            e.printStackTrace()
        }
    }.start()
}
