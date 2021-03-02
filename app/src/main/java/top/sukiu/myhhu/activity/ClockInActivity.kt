package top.sukiu.myhhu.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.*
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_clock_in.*
import okhttp3.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import top.sukiu.myhhu.R
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ClockInActivity : AppCompatActivity() {

    lateinit var account: String
    lateinit var wid: String
    lateinit var Name: String //姓名
    lateinit var SelfAccount: String //身份证号
    lateinit var Institute: String //学院
    lateinit var Grade: String //年级
    lateinit var Major: String //专业
    lateinit var Classes: String //班级
    lateinit var Building: String //宿舍楼
    lateinit var Room: String //宿舍号
    lateinit var PhoneNum: String //手机号码
    lateinit var Address: String //住址
    var flag: Boolean = false
    lateinit var daka_save_url: String
    private var client: OkHttpClient? = null
    var sp: SharedPreferences? = null
    private var cookie: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TransportStatusBar()
        setContentView(R.layout.activity_clock_in)
        setSupportActionBar(clock_in_bar)
        supportActionBar?.title = "Clock In"


        if (Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
        sp = this.getSharedPreferences("dakainfo", Context.MODE_PRIVATE)
        checkDakaInfo()
        dakaButton.setOnClickListener { dakaButtonHandle() }
        setButton.setOnClickListener { startActivity<ClockInSetActivity>() }
        at_home.setOnCheckedChangeListener { buttonView, isChecked -> atHomeButtonChange() }
        at_school.setOnCheckedChangeListener { buttonView, isChecked -> atSchoolButtonChange() }
    }

    private fun atHomeButtonChange() {
        if (at_school.isChecked && at_home.isChecked) at_school.isChecked = false
        if (!at_home.isChecked && !at_school.isChecked) at_school.isChecked = true
    }

    private fun atSchoolButtonChange() {
        if (at_home.isChecked && at_school.isChecked) at_home.isChecked = false
        if (!at_home.isChecked && !at_school.isChecked) at_home.isChecked = true
    }

    @SuppressLint("HandlerLeak")
    @Suppress("DEPRECATION")
    private val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                100 -> toast("开始打卡，请耐心等待")
                200 -> toast("打卡成功")
                300 -> toast("服务器连接超时")
                500 -> toast("打卡失败")
            }
        }
    }


    private fun dakaButtonHandle() {
        if (!flag) {
            toast("请补全打卡信息")
            return
        }
        if (at_home.isChecked && Address == "") {
            toast("请填写地址信息")
            return
        }
        daka_save_url = "http://form.hhu.edu.cn/pdc/formDesignApi/dataFormSave?wid=" + wid + "&userId=" + account
        client = OkHttpClient().newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
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
        Thread(
            object : Runnable {
                override fun run() {
                    try {
                        val meg = Message.obtain()
                        meg.what = 100
                        handler.sendMessage(meg)
                        daka()
                    } catch (e: Exception) {
                        e.printStackTrace()
                        val meg = Message.obtain()
                        meg.what = 300
                        handler.sendMessage(meg)
                    }
                }
            }
        ).start()
        return
    }

    private fun daka() {
        val currdate = getDate()
        //println(currdate)
        val requestBody: RequestBody
        if (at_home.isChecked) {
            requestBody = FormBody.Builder()
                .add("DATETIME_CYCLE", currdate)
                .add("XGH_336526", account)
                .add("XM_1474", Name) // 姓名
                .add("SFZJH_859173", SelfAccount) // 身份证号
                .add("SELECT_941320", Institute) // 学院
                .add("SELECT_459666", Grade) // 年级
                .add("SELECT_814855", Major) // 专业
                .add("SELECT_525884", Classes) // 班级
                .add("SELECT_125597", Building) // 宿舍楼
                .add("TEXT_950231", Room) // 宿舍号
                .add("TEXT_937296", PhoneNum) // 手机号码
                .add("RADIO_853789", "否")
                .add("RADIO_43840", "否")
                .add("RADIO_579935", "健康")
                .add("RADIO_138407", "否")
                .add("RADIO_546905", "否")
                .add("RADIO_314799", "否")
                .add("RADIO_209256", "否")
                .add("RADIO_836972", "否")
                .add("RADIO_302717", "否")
                .add("RADIO_701131", "否")
                .add("RADIO_438985", "否")
                .add("RADIO_467360", "是")
                .add("PICKER_956186", Address) // 住址 如：江苏省,南京市,江宁区
                .add("TEXT_434598", "")
                .add("TEXT_515297", "")
                .add("TEXT_752063", "")
                .build()
        } else {
            requestBody = FormBody.Builder()
                .add("DATETIME_CYCLE", currdate)
                .add("XGH_336526", account)
                .add("XM_1474", Name) // 姓名
                .add("SFZJH_859173", SelfAccount) // 身份证号
                .add("SELECT_941320", Institute) // 学院
                .add("SELECT_459666", Grade) // 年级
                .add("SELECT_814855", Major) // 专业
                .add("SELECT_525884", Classes) // 班级
                .add("SELECT_125597", Building) // 宿舍楼
                .add("TEXT_950231", Room) // 宿舍号
                .add("TEXT_937296", PhoneNum) // 手机号码
                .add("RADIO_853789", "否")
                .add("RADIO_43840", "否")
                .add("RADIO_579935", "健康")
                .add("RADIO_138407", "是") // 在学校
                .add("RADIO_546905", "")
                .add("RADIO_314799", "")
                .add("RADIO_209256", "")
                .add("RADIO_836972", "")
                .add("RADIO_302717", "")
                .add("RADIO_701131", "")
                .add("RADIO_438985", "")
                .add("RADIO_467360", "")
                .add("PICKER_956186", "") // 住址 如：江苏省,南京市,江宁区
                .add("TEXT_434598", "")
                .add("TEXT_515297", "")
                .add("TEXT_752063", "")
                .build()
        }
        val request = Request.Builder()
            .removeHeader("User-Agent")
            .addHeader(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4209.2 Safari/537.36"
            )
            .url(daka_save_url)
            .post(requestBody)
            .build()
        val response = client!!.newCall(request).execute()
        val dakahtml = response.body?.string()
        //println(dakahtml)
        if (dakahtml!!.contains("{\"result\":true}")) {
            val meg = Message.obtain()
            meg.what = 200
            handler.sendMessage(meg)
        } else {
            val meg = Message.obtain()
            meg.what = 500
            handler.sendMessage(meg)
        }
        return
    }

    private fun getDate(): String {
        val calendar: Calendar = Calendar.getInstance()
        val y = calendar.get(Calendar.YEAR).toString()
        var m = (calendar.get(Calendar.MONTH) + 1).toString()
        var d = calendar.get(Calendar.DAY_OF_MONTH).toString()
        if (m.toInt() < 10) m = "0" + m
        if (d.toInt() < 10) d = "0" + d
        return y + "/" + m + "/" + d
    }


    private fun checkDakaInfo() {
        if (sp == null) {
            toast("请补全打卡信息")
            return
        }
        sp!!.let {
            account = it.getString("account", "").toString()
            wid = it.getString("wid", "").toString()
            Name = it.getString("Name", "").toString()
            SelfAccount = it.getString("SelfAccount", "").toString()
            Institute = it.getString("Institute", "").toString()
            Grade = it.getString("Grade", "").toString()
            Major = it.getString("Major", "").toString()
            Classes = it.getString("Classes", "").toString()
            Building = it.getString("Building", "").toString()
            Room = it.getString("Room", "").toString()
            PhoneNum = it.getString("PhoneNum", "").toString()
            Address = it.getString("Address", "").toString()
        }
        if (account == "" || wid == "" || Name == "" ||
            SelfAccount == "" || Institute == "" || Grade == "" || Major == "" ||
            Classes == "" || Building == "" || Room == "" || PhoneNum == ""
        ) {
            toast("请补全打卡信息")
            return
        }
        flag = true
        return
    }

    @Suppress("DEPRECATION")
    private fun TransportStatusBar() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(Color.TRANSPARENT)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

}