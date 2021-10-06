package github.sukieva.hhu.data.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import github.sukieva.hhu.MyApp.Companion.context
import github.sukieva.hhu.R
import github.sukieva.hhu.data.bean.Course
import github.sukieva.hhu.data.bean.Rank
import github.sukieva.hhu.data.remote.EasyOkhttp
import github.sukieva.hhu.utils.DataManager
import github.sukieva.hhu.utils.LogUtil
import github.sukieva.hhu.utils.errorToast
import github.sukieva.hhu.utils.infoToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.FormBody
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Jsoup
import java.io.InputStream
import java.math.BigInteger
import java.security.MessageDigest

object RemoteRepository {

    private const val BASE_URL = "http://jwxs.hhu.edu.cn/"
    private const val captchaPicUrl = BASE_URL + "img/captcha.jpg"
    private const val loginUrl = BASE_URL + "j_spring_security_check"
    private const val gradesUrl = BASE_URL + "student/integratedQuery/scoreQuery/schemeScores/callback"
    private const val rankUrl = BASE_URL + "student/integratedQuery/gpaRankingQuery/index/jdpmcx"
    private const val TAG = "HTTP"


    suspend fun getRank(): Rank {
        var cnt = 1
        try {
            val rankhtml = EasyOkhttp.request(rankUrl)
            val parse = Jsoup.parse(rankhtml)
            while (cnt <= 10) {
                val infos = parse.getElementsByClass("report1_$cnt")
                //[序号，学号，姓名，专业，专业人数，平均绩点，平均成绩，平均排名，推优绩点，推优成绩，推优排名]
                val rankinfos = Rank(
                    infos[2].text(), // 姓名
                    infos[3].text(), // 专业
                    infos[4].text().toInt(), // 专业人数
                    infos[5].text().toDouble(), // 平均绩点
                    infos[6].text().toDouble(), // 平均成绩
                    infos[7].text().toInt(), // 平均排名
                    infos[8].text().toDouble(), // 推优绩点
                    infos[9].text().toDouble(), // 推优成绩
                    infos[10].text().toInt() // 推优排名
                )
                LogUtil.i(TAG, "Get rank successfully")
                return rankinfos
            }
        } catch (e: Exception) {
            cnt += 1
            LogUtil.d(TAG, "Fail to get rank")
            e.printStackTrace()
        }
        return Rank("Error", "Error", 999, 5.0, 100.0, 1, 5.0, 100.0, 1)
    }

    suspend fun getGrades(): MutableList<Course>? {
        val gradesinfos: MutableList<Course> = mutableListOf()
        try {
            val gradeshtml = EasyOkhttp.request(gradesUrl)
            val lessons = JSONArray(gradeshtml).optJSONObject(0).getJSONArray("cjList")
            for (i in 0 until lessons.length()) {
                val lesson = JSONObject(lessons[i].toString())
                val course = Course()
                course.courseName = lesson.getString("courseName")
                course.courseAttributeName = lesson.getString("courseAttributeName")
                course.courseScore = lesson.getDouble("courseScore")
                course.gradePointScore = lesson.getDouble("gradePointScore")
                course.credit = lesson.getDouble("credit")
                course.academicYearCode =
                    lesson.getString("academicYearCode") + "-" + lesson.getInt("termCode")
                gradesinfos.add(course)
            }
            LogUtil.i(TAG, "Get grades Successfully")
            return gradesinfos
        } catch (e: Exception) {
            LogUtil.d(TAG, "Fail to get grades")
            e.printStackTrace()
            return null
        }
    }

    suspend fun login(yzm: String): Boolean {
        val loginData = getLoginData(yzm) ?: return false
        return try {
            val html = EasyOkhttp.request(loginUrl, loginData)
            if ("<title>URP综合教务系统首页</title>" in html) {
                LogUtil.d(TAG, "==> Login successfully")
                "登录成功".infoToast()
                true
            } else {
                LogUtil.d(TAG, "==> Fail to login")
                "验证码或信息错误".errorToast()
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            LogUtil.d(TAG, "==> Fail to login")
            "服务器错误".errorToast()
            false
        }
    }

    private suspend fun getLoginData(yzm: String): RequestBody? {
        val account = DataManager.readData("account", "")
        val password = DataManager.readData("password", "")
        if (account == "" || password == "") {
            context.getString(R.string.results_config_null).errorToast()
            return null
        }
        return FormBody.Builder()
            .add("j_username", account)
            .add("j_password", md5(password))
            .add("j_captcha", yzm)
            .build()
    }


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
            //e.printStackTrace()
            LogUtil.d("Hitokoto", "Fail to get Hitokoto")
        }
    }.flowOn(Dispatchers.IO)


    private fun md5(password: String): String {
        val md = MessageDigest.getInstance("MD5")
        md.update(password.toByteArray())
        return BigInteger(1, md.digest()).toString(16)
    }

}