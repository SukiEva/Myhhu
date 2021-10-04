package github.sukieva.hhu.data.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import github.sukieva.hhu.data.remote.EasyOkhttp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Request
import java.io.InputStream

object RemoteRepository {

    private const val BASE_URL = "http://jwxs.hhu.edu.cn/"
    private const val captchaPicUrl = BASE_URL + "img/captcha.jpg"
    private const val loginUrl = BASE_URL + "j_spring_security_check"
    private const val gradesUrl = BASE_URL + "student/integratedQuery/scoreQuery/schemeScores/callback"
    private const val rankUrl = BASE_URL + "student/integratedQuery/gpaRankingQuery/index/jdpmcx"


    fun getBitmap(): Bitmap {
        val request = Request.Builder().url(captchaPicUrl).build()
        val response = EasyOkhttp.okHttpClient.newCall(request).execute()
        val `is`: InputStream = response.body()!!.byteStream()
        return BitmapFactory.decodeStream(`is`)
    }


    fun getCaptchaPic() = flow<Bitmap> {
        try {

            //emit(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)


}