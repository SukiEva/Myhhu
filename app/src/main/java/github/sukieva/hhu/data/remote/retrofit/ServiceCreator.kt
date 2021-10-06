package github.sukieva.hhu.data.remote.retrofit

import github.sukieva.hhu.data.remote.EasyOkhttp.sessionCookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ServiceCreator {
    private const val BASE_URL = "http://jwxs.hhu.edu.cn/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(
            OkHttpClient().newBuilder()
                .cookieJar(sessionCookieJar)
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)


}

