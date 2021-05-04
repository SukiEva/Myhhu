package top.sukiu.myhhu.util

import android.os.Handler
import android.os.Message
import okhttp3.*
import java.util.*
import java.util.concurrent.TimeUnit

object UserData {

    var account: String = ""    //学号
    var wid: String = ""    // 每个学院不一样
    var Name: String = ""   //姓名
    var SelfAccount: String = ""    //身份证号
    var Institute: String = ""  //学院
    var Grade: String = ""  //年级
    var Major: String = ""  //专业
    var Classes: String = ""    //班级
    var Building: String = ""   //宿舍楼
    var Room: String = ""   //宿舍号
    var PhoneNum: String = ""   //手机号码
    var Address: String = ""    //住址

    private var cookie: String? = null
    private val client = OkHttpClient().newBuilder()
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


    suspend fun setData() {
        account = readData("account")
        wid = readData("wid")
        Name = readData("Name")
        SelfAccount = readData("SelfAccount")
        Institute = readData("Institute")
        Grade = readData("Grade")
        Major = readData("Major")
        Classes = readData("Classes")
        Building = readData("Building")
        Room = readData("Room")
        PhoneNum = readData("PhoneNum")
        Address = readData("Address")
    }

    fun clockIn(handler: Handler? = null, isAtHome: Boolean = false) {
        Thread {
            try {
                if (handler == null) postData(isAtHome = isAtHome)
                else {
                    val meg = Message.obtain()
                    meg.what = 100
                    handler.sendMessage(meg)
                    postData(handler, isAtHome)
                    LogUtil.i("ClockIn", "Success")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                LogUtil.i("ClockIn", "Failed")
                handler?.let {
                    val meg = Message.obtain()
                    meg.what = 300
                    handler.sendMessage(meg)
                }
            }
        }.start()
    }


    private fun postData(handler: Handler? = null, isAtHome: Boolean = false) {
        val daka_save_url =
            "http://form.hhu.edu.cn/pdc/formDesignApi/dataFormSave?wid=$wid&userId=$account"
        val currdate = getDate()
        val requestBody: RequestBody
        if (isAtHome) {
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
        val response = client.newCall(request).execute()
        val dakahtml = response.body?.string()
        handler?.let {
            if (dakahtml!!.contains("{\"result\":true}")) {
                val meg = Message.obtain()
                meg.what = 200
                it.sendMessage(meg)
            } else {
                val meg = Message.obtain()
                meg.what = 500
                it.sendMessage(meg)
            }
        }
    }


    private fun getDate(): String {
        val calendar: Calendar = Calendar.getInstance()
        val y = calendar.get(Calendar.YEAR).toString()
        var m = (calendar.get(Calendar.MONTH) + 1).toString()
        var d = calendar.get(Calendar.DAY_OF_MONTH).toString()
        if (m.toInt() < 10) m = "0$m"
        if (d.toInt() < 10) d = "0$d"
        return "$y/$m/$d"
    }


}