package github.sukieva.hhu.network

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ServiceCreator {
    private const val BASE_URL = "http://jwxs.hhu.edu.cn/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(
            OkHttpClient().newBuilder()
                .cookieJar(SessionCookieJar())
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)

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

