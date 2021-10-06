package github.sukieva.hhu.service

import github.sukieva.hhu.MyApp
import github.sukieva.hhu.R
import github.sukieva.hhu.data.remote.EasyOkhttp
import github.sukieva.hhu.utils.*
import okhttp3.FormBody
import okhttp3.RequestBody
import java.util.*

object CheckInService {

    private var account = ""
    private var wid = ""
    private var name = ""
    private var aid = ""
    private var institute = ""
    private var grade = ""
    private var major = ""
    private var mclass = ""
    private var building = ""
    private var room = ""
    private var phone = ""
    private var place = ""

    suspend fun checkIn() {
        val date = getDate()
        if (!setData()) {
            MyApp.context.getString(R.string.results_config_null).warningToast()
            return
        }
        val url = "http://dailyreport.hhu.edu.cn/pdc/formDesignApi/dataFormSave?wid=$wid&userId=$account"
        val response = EasyOkhttp.request(address = url, body = getPostBody())
        if ("{\"result\":true}" in response) {
            "$date 打卡成功".infoToast()
            notify("健康打卡", "$date 打卡成功")
            DataManager.saveData("whenCheckIn", date)
        } else {
            "$date 打卡失败".errorToast()
            notify("健康打卡", "$date 打卡失败")
            LogUtil.d("CheckInService", response)
        }
    }


    private suspend fun setData(): Boolean {
        account = DataManager.readData("account", "")
        wid = DataManager.readData("wid", "")
        name = DataManager.readData("name", "")
        aid = DataManager.readData("aid", "")
        institute = DataManager.readData("institute", "")
        grade = DataManager.readData("grade", "")
        major = DataManager.readData("major", "")
        mclass = DataManager.readData("mclass", "")
        building = DataManager.readData("building", "")
        room = DataManager.readData("room", "")
        phone = DataManager.readData("phone", "")
        place = DataManager.readData("place", "")
        if (account == "" || wid == "" || name == "" ||
            aid == "" || institute == "" || grade == "" || major == "" ||
            mclass == "" || building == "" || room == "" || phone == ""
        ) return false
        return true
    }

    // https://blog.csdn.net/qq_43640009/article/details/107868713
    private fun getPostBody(): RequestBody = FormBody.Builder()
        .add("DATETIME_CYCLE", getDate())
        .add("XGH_336526", account)
        .add("XM_1474", name)
        .add("SFZJH_859173", aid)
        .add("SELECT_941320", institute)
        .add("SELECT_459666", grade)
        .add("SELECT_814855", major)
        .add("SELECT_525884", mclass)
        .add("SELECT_125597", building)
        .add("TEXT_950231", room)
        .add("TEXT_937296", phone)
        .add("RADIO_6555", "正常（37.3℃）")
        .add("RADIO_535015", place)
        .add("RADIO_891359", "健康")
        .add("RADIO_372002", "健康")
        .add("RADIO_618691", "否")
        .build()


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