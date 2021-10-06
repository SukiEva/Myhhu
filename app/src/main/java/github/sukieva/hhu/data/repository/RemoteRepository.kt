package github.sukieva.hhu.data.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import coil.request.ImageRequest
import github.sukieva.hhu.data.remote.EasyOkhttp
import github.sukieva.hhu.utils.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.io.InputStream
import kotlin.concurrent.thread

object RemoteRepository {

    private const val BASE_URL = "http://jwxs.hhu.edu.cn/"
    private const val captchaPicUrl = BASE_URL + "img/captcha.jpg"
    private const val loginUrl = BASE_URL + "j_spring_security_check"
    private const val gradesUrl = BASE_URL + "student/integratedQuery/scoreQuery/schemeScores/callback"
    private const val rankUrl = BASE_URL + "student/integratedQuery/gpaRankingQuery/index/jdpmcx"
    private const val TAG = "HTTP"


    fun getCaptchaPic(): Bitmap? {
        val request = Request.Builder().url(captchaPicUrl).build()
        try {
            val response = EasyOkhttp.okHttpClient.newCall(request).execute()
            if (!response.isSuccessful) response.close()
            if (response.body() != null) {
                val `is`: InputStream? = response.body()!!.byteStream()
                LogUtil.d(TAG, "==> Successfully get captchaPic")
                return BitmapFactory.decodeStream(`is`)
            }
        } catch (e: Exception) {
            LogUtil.d(TAG, "==> Fail to get captchaPic")
        }
        return null
    }


    fun getHitokoto() = flow {
        try {
            val hitoko = EasyOkhttp.request("https://v1.hitokoto.cn/?encode=text&c=i")
            LogUtil.d("Hitokoto", "==> Hitokoto is $hitoko")
            emit(hitoko)
        } catch (e: Exception) {
            e.printStackTrace()
            LogUtil.d("Hitokoto", "Fail to get Hitokoto")
        }
    }.flowOn(Dispatchers.IO)

}