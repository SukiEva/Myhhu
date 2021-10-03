package github.sukieva.hhu.data

import android.graphics.BitmapFactory
import github.sukieva.hhu.network.EasyOkhttp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.InputStream

object Repository {

    private const val BASE_URL = "http://jwxs.hhu.edu.cn/"
    private const val captchaPicUrl = BASE_URL + "img/captcha.jpg"
    private const val loginUrl = BASE_URL + "j_spring_security_check"
    private const val gradesUrl = BASE_URL + "student/integratedQuery/scoreQuery/schemeScores/callback"
    private const val rankUrl = BASE_URL + "student/integratedQuery/gpaRankingQuery/index/jdpmcx"


    fun getCaptchaPic() = flow {
        val response = EasyOkhttp.request(captchaPicUrl)
        val `is`: InputStream = response.byteInputStream()
        val bitmap = BitmapFactory.decodeStream(`is`)
        emit(bitmap)
    }.flowOn(Dispatchers.IO)



}