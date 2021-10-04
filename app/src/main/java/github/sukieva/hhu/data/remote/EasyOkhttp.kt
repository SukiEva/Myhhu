package github.sukieva.hhu.data.remote

import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object EasyOkhttp {

    val sessionCookieJar = SessionCookieJar()

    val okHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .cookieJar(sessionCookieJar)
        .build()

    private fun sendHttpRequest(address: String, body: RequestBody? = null, callback: Callback) {
        val request =
            if (body != null) Request.Builder().url(address).post(body).build()
            else Request.Builder().url(address).build()
        okHttpClient.newCall(request).enqueue(callback)
    }



    suspend fun request(address: String, body: RequestBody? = null): String {
        return suspendCoroutine {
            sendHttpRequest(address, body, object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    it.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    val resBody = response.body()?.string()
                    if (resBody != null) it.resume(resBody)
                    else it.resumeWithException(RuntimeException("response body is null"))
                }

            })
        }
    }


    // https://stackoverflow.com/questions/38418809/add-cookies-to-retrofit-2-request
    class SessionCookieJar : CookieJar {
        private val cookieStore: HashMap<String, List<Cookie>> = HashMap()
        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            cookieStore[url.host()] = cookies
        }

        override fun loadForRequest(url: HttpUrl): List<Cookie> {
            val cookies = cookieStore[url.host()]
            return cookies ?: ArrayList()
        }
    }
}