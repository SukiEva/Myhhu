package top.sukiu.myhhu.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.*
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.*
import org.jetbrains.anko.*
import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.Jsoup
import top.sukiu.myhhu.R
import top.sukiu.myhhu.bean.Course
import top.sukiu.myhhu.bean.Grades
import top.sukiu.myhhu.bean.MD5
import top.sukiu.myhhu.bean.Rank
import java.io.InputStream
import java.util.concurrent.TimeUnit


class LoginActivity : AppCompatActivity() {

    private val homeUrls = listOf(
        "http://202.119.114.197/", "http://202.119.114.198/", "http://202.119.114.199/"
    )
    private var client: OkHttpClient? = null
    private var homeUrl = "http://202.119.114.197/"
    var sp: SharedPreferences? = null
    private var cookie: String? = null
    val log = AnkoLogger<LoginActivity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TransportStatusBar()
        setContentView(R.layout.activity_login)
        setSupportActionBar(login_bar)
        supportActionBar?.title = "Login"

        if (Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

        sp = this.getSharedPreferences("userinfo", Context.MODE_PRIVATE)
        hasInfo()

        loadingCaptchaPic()
        askGrades.setOnClickListener { loginButtonClickHandler() }
        refreshButton.setOnClickListener { refreshButtonClickHandler() }
    }

    private fun hasInfo() { // check if wanting to store information
        if (sp!!.getBoolean("checkboxBoolean", false)) {
            loginNum.setText(sp!!.getString("uname", null))
            loginPassword.setText(sp!!.getString("upswd", null))
            rememberInfo.isChecked = true
        }
    }

    @SuppressLint("HandlerLeak")
    @Suppress("DEPRECATION")
    private fun loadingCaptchaPic() { // show yzm by bitmap
        client = OkHttpClient().newBuilder()
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
        //load yzm captcha
        homeUrl = homeUrls[0]
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
        val handler: Handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (msg.what == 404) {
                    toast("教务系统崩溃啦>_<")
                }
            }
        }
        Thread(
            object : Runnable {
                override fun run() {
                    try {
                        val response = client!!.newCall(request).execute()
                        val `is`: InputStream = response.body!!.byteStream()
                        captchaPic = BitmapFactory.decodeStream(`is`)
                        checkCodePicture?.post { checkCodePicture!!.setImageBitmap(captchaPic) }
                        return
                    } catch (e: Exception) {
                        log.debug { "Connect failed: " + e.stackTrace }
                        e.printStackTrace()
                        val meg = Message()
                        meg.what = 404
                        handler.sendMessage(meg)
                        return
                    }
                }
            }).start()
        return
    }

    private fun loginButtonClickHandler() {
        val Num = loginNum.text.toString()
        val Password = loginPassword.text.toString()
        val Yzm = loginYzm.text.toString()
        when { // check if any is empty
            Num.equals("") -> {
                alert("请填写学号！￣へ￣") { positiveButton("٩('ω')وget！") {} }.show()
                return
            }
            Password.equals("") -> {
                alert("请填写密码！(*￣︿￣)") { positiveButton("٩('ω')وget！") {} }.show()
                return
            }
            Yzm.equals("") -> {
                alert("请填写验证码！凸(艹皿艹 )") { positiveButton("٩('ω')وget！") {} }.show()
                return
            }
        }
        if (rememberInfo!!.isChecked) { // store user info by SharedPreferences
            val editor = sp!!.edit()
            editor.putString("uname", Num)
            editor.putString("upswd", Password)
            editor.putBoolean("checkboxBoolean", true)
            editor.commit()
        } else {
            val editor = sp!!.edit()
            editor.putString("uname", null)
            editor.putString("upswd", null)
            editor.putBoolean("checkboxBoolean", false)
            editor.commit()
        }
        val LoginUrl = homeUrl + "j_spring_security_check"
        val requestbody: RequestBody = FormBody.Builder()
            .add("j_username", Num)
            .add("j_password", MD5().getMD5(Password))
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
        val homehtml: String?
        try {
            val response = client!!.newCall(request).execute()
            homehtml = response.body?.string()
            if (homehtml!!.contains("URP综合教务系统首页")) {
                val rank = getRank()
                val courses = getGrades()
                val grades = Grades(courses)
                //indeterminateProgressDialog("登录中")
                startActivity<ShowResultsActivity>(
                    "grades" to grades,
                    "rank" to rank
                )
                finish()
            } else {
                toast("Login failed. Please try again!")
                loginYzm.setText("")
                loadingCaptchaPic()
            }
        } catch (e: Exception) {
            log.debug { "Login Failed: " + e.stackTrace }
            e.printStackTrace()
            toast("Login failed. Please try again!")
            loginYzm.setText("")
            loadingCaptchaPic()
            return
        }
    }

    private fun refreshButtonClickHandler() {
        val animation = RotateAnimation(
            0.0f, 360.0f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        animation.duration = 500
        refreshButton.startAnimation(animation)
        loadingCaptchaPic()
    }

    private fun getRank(): Rank {
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
        val response = client!!.newCall(request).execute()
        rankhtml = response.body?.string()
        val parse = Jsoup.parse(rankhtml)
        while (cnt <= 10) {
            try {
                val infos = parse.getElementsByClass("report1_$cnt")
                //[序号，学号，姓名，专业，专业人数，平均绩点，平均成绩，平均排名，推优绩点，推优成绩，推优排名]
                //println("report1_$cnt")
                //for (info in infos) println(info.text())
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
                return rankinfos
            } catch (e: Exception) {
                log.debug { "Get Rank error: " + e.stackTrace }
                e.printStackTrace()
                cnt++
            }
        }
        return Rank("Error", "Error", 999, 5.0, 100.0, 1, 5.0, 100.0, 1)
    }

    private fun getGrades(): MutableList<Course>? {
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
            val response = client!!.newCall(request).execute()
            gradeshtml = response.body?.string()
            val lessons = JSONArray(gradeshtml.toString()).optJSONObject(0).getJSONArray("cjList")
            for (i in 0..lessons.length() - 1) {
                val lesson = JSONObject(lessons[i].toString())
                val course = Course()
                course.courseName = lesson.getString("courseName")
                course.courseAttributeName = lesson.getString("courseAttributeName")
                course.courseScore = lesson.getDouble("courseScore")
                course.gradePointScore = lesson.getDouble("gradePointScore")
                course.credit = lesson.getDouble("credit")
                course.academicYearCode = lesson.getString("academicYearCode") + "-" + lesson.getInt("termCode")
                gradesinfos.add(course)
            }
            return gradesinfos
        } catch (e: Exception) {
            log.debug { "Get Grades error: " + e.stackTrace }
            e.printStackTrace()
            return null
        }
    }

    @Suppress("DEPRECATION")
    private fun TransportStatusBar() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(Color.TRANSPARENT)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

}