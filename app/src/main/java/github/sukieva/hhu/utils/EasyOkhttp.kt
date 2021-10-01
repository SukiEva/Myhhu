package github.sukieva.hhu.utils

import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object EasyOkhttp {

    private var cookie: String? = null

    private val client = OkHttpClient().newBuilder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .cookieJar(object : CookieJar {
            // store cookies
            private val cookieStore: HashMap<String, List<Cookie>> = HashMap()
            override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                cookieStore[url.host] = cookies
                cookie = cookies[0].name + "=" + cookies[0].value
            }

            override fun loadForRequest(url: HttpUrl): List<Cookie> {
                val cookies = cookieStore[url.host]
                return cookies ?: ArrayList()
            }
        }).build()


    private fun sendHttpRequest(address: String, hasHeader: Boolean = true, callback: Callback) {
        val request = if (hasHeader)
            Request.Builder().removeHeader("User-Agent")
                .addHeader(
                    "User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4209.2 Safari/537.36"
                ).url(address).build()
        else
            Request.Builder().url(address).build()
        client.newCall(request).enqueue(callback)
    }

//    suspend fun request(address: String, hasHeader: Boolean = true) {
//        return suspendCoroutine { continuation ->
//            sendHttpRequest(address = address, hasHeader = hasHeader, object : Callback {
//                override fun onResponse(call: Call, response: Response) {
//                    val res = response.body?.string()
//                    continuation.resume(res)
//                }
//
//                override fun onFailure(call: Call, e: IOException) {
//                    continuation.resumeWithException(e)
//                }
//            })
//        }
//    }
//
//    suspend fun getRequest(){
//        try {
//            val response:String = request("https://baidu.com")
//        }catch (e:Exception){
//
//        }
//    }

}