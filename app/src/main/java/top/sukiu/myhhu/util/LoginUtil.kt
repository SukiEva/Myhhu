package top.sukiu.myhhu.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Message
import android.widget.EditText
import android.widget.ImageView
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Jsoup
import top.sukiu.myhhu.bean.Course
import top.sukiu.myhhu.bean.Rank
import java.io.InputStream
import java.util.concurrent.TimeUnit

object LoginUtil {

    private var cookie: String? = null
    private var homeUrl = "http://202.119.114.197/"
    val client = OkHttpClient().newBuilder()
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

    fun getCaptchaPic(checkCodePicture: ImageView, handler: Handler) {
        val ImgUrl = homeUrl + "img/captcha.jpg"
        val request = Request.Builder()
            .removeHeader("User-Agent")
            .addHeader(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4209.2 Safari/537.36"
            )
            .url(ImgUrl)
            .build()
        var captchaPic: Bitmap?
        Thread(
            object : Runnable {
                override fun run() {
                    try {
                        val response = client.newCall(request).execute()
                        val `is`: InputStream = response.body!!.byteStream()
                        captchaPic = BitmapFactory.decodeStream(`is`)
                        checkCodePicture.post { checkCodePicture.setImageBitmap(captchaPic) }
                        return
                    } catch (e: Exception) {
                        LogUtil.d("LoginActivity", "Connect failed: " + e.stackTrace)
                        val meg = Message()
                        meg.what = 404
                        handler.sendMessage(meg)
                        return
                    }
                }
            }).start()
    }

    fun login(
        Num: String,
        Password: String,
        Yzm: String,
        checkCodePicture: ImageView,
        handler: Handler,
        loginYzm: EditText
    ): String? {
        val LoginUrl = homeUrl + "j_spring_security_check"
        val requestbody: RequestBody = FormBody.Builder()
            .add("j_username", Num)
            .add("j_password", getMD5(Password))
            .add("j_captcha", Yzm)
            .build()
        val request = Request.Builder()
            .removeHeader("User-Agent")
            .addHeader(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4209.2 Safari/537.36"
            )
            .url(LoginUrl)
            .post(requestbody)
            .build()
        return try {
            val response = client.newCall(request).execute()
            LogUtil.i("LoginActivity", "Login Successfully")
            response.body?.string()
        } catch (e: Exception) {
            LogUtil.d("LoginActivity", "Login Failed: " + e.stackTrace)
            e.printStackTrace()
            toast("Login failed. Please try again!")
            loginYzm.setText("")
            getCaptchaPic(checkCodePicture, handler)
            null
        }
    }


    fun getRank(): Rank {
        val RankUrl = homeUrl + "student/integratedQuery/gpaRankingQuery/index/jdpmcx"
        val rankhtml: String?
        val request = Request.Builder()
            .removeHeader("User-Agent")
            .addHeader(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4209.2 Safari/537.36"
            )
            .url(RankUrl)
            .build()
        var cnt = 1
        val response = client.newCall(request).execute()
        rankhtml = response.body?.string()
        val parse = Jsoup.parse(rankhtml)
        while (cnt <= 10) {
            try {
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
                LogUtil.i("LoginActivity", "Get Rank Successfully")
                return rankinfos
            } catch (e: Exception) {
                LogUtil.d("LoginActivity", "Get Rank error: " + e.stackTrace)
                e.printStackTrace()
                cnt++
            }
        }
        return Rank("Error", "Error", 999, 5.0, 100.0, 1, 5.0, 100.0, 1)
    }

    fun getGrades(): MutableList<Course>? {
        val GradesUrl = homeUrl + "student/integratedQuery/scoreQuery/schemeScores/callback"
        val gradeshtml: String?
        val gradesinfos: MutableList<Course> = mutableListOf()
        val request = Request.Builder()
            .removeHeader("User-Agent")
            .addHeader(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4209.2 Safari/537.36"
            )
            .url(GradesUrl)
            .build()
        try {
            val response = client.newCall(request).execute()
            gradeshtml = response.body?.string()
            val lessons = JSONArray(gradeshtml.toString()).optJSONObject(0).getJSONArray("cjList")
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
            LogUtil.i("LoginActivity", "Get Grades Successfully")
            return gradesinfos
        } catch (e: Exception) {
            LogUtil.d("LoginActivity", "Get Grades error: " + e.stackTrace)
            e.printStackTrace()
            return null
        }
    }

}