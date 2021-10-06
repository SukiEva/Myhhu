package github.sukieva.hhu.ui.activity.config

import android.content.pm.PackageManager
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import github.sukieva.hhu.MyApp
import github.sukieva.hhu.MyApp.Companion.context
import github.sukieva.hhu.R
import github.sukieva.hhu.ui.components.InJavaScriptLocalObj
import github.sukieva.hhu.utils.*
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

class ConfigViewModel : ViewModel() {

    private val TAG = "ConfigActivity"

    var account = mutableStateOf("")
    var password = mutableStateOf("")
    var wid = mutableStateOf("")
    var name = mutableStateOf("")
    var aid = mutableStateOf("")
    var institute = mutableStateOf("")
    var grade = mutableStateOf("")
    var major = mutableStateOf("")
    var mclass = mutableStateOf("")
    var building = mutableStateOf("")
    var room = mutableStateOf("")
    var phone = mutableStateOf("")
    var place = mutableStateOf("")
    val configUrl = "http://dailyreport.hhu.edu.cn/pdc/formDesign/showFormFilled?selfFormWid=A335B048C8456F75E0538101600A6A04&lwUserId=${account.value}"
    var isFetchData = false

    fun saveConfig() {
        viewModelScope.launch {
            DataManager.saveData("account", account.value)
            DataManager.saveData("password", password.value)
            DataManager.saveData("wid", wid.value)
            DataManager.saveData("name", name.value)
            DataManager.saveData("aid", aid.value)
            DataManager.saveData("institute", institute.value)
            DataManager.saveData("grade", grade.value)
            DataManager.saveData("major", major.value)
            DataManager.saveData("mclass", mclass.value)
            DataManager.saveData("building", building.value)
            DataManager.saveData("room", room.value)
            DataManager.saveData("phone", phone.value)
            DataManager.saveData("place", place.value)
            if (wid.value == "") DataManager.saveData("wid", "A335B048C8456F75E0538101600A6A04")
            MyApp.context.getString(R.string.config_save_config).infoToast()
            //refreshUI()
        }
    }

    fun showConfig() {
        viewModelScope.launch {
            account.value = DataManager.readData("account", "")
            password.value = DataManager.readData("password", "")
            wid.value = DataManager.readData("wid", "")
            name.value = DataManager.readData("name", "")
            aid.value = DataManager.readData("aid", "")
            institute.value = DataManager.readData("institute", "")
            grade.value = DataManager.readData("grade", "")
            major.value = DataManager.readData("major", "")
            mclass.value = DataManager.readData("mclass", "")
            building.value = DataManager.readData("building", "")
            room.value = DataManager.readData("room", "")
            phone.value = DataManager.readData("phone", "")
            place.value = DataManager.readData("place", "")
        }
    }


    fun saveFetchData() {
        val html = InJavaScriptLocalObj.webHtml
        if (html == null) MyApp.context.getString(R.string.config_fetch_data).errorToast()
        else {
            val parse = Jsoup.parse(html)
            val infos = try {
                parse.getElementById("countTableBody")!!.getElementsByTag("tr")[0].getElementsByTag("td")
            } catch (e: Exception) {
                MyApp.context.getString(R.string.config_login_in).errorToast()
                LogUtil.d("ConfigActivity", "==> Fail to fetch data")
                return
            }
            //for (info in infos) println(info.text())
            viewModelScope.launch {
                DataManager.saveData("name", infos[2].text())
                DataManager.saveData("aid", infos[3].text())
                DataManager.saveData("institute", infos[4].text())
                DataManager.saveData("grade", infos[5].text())
                DataManager.saveData("major", infos[6].text())
                DataManager.saveData("mclass", infos[7].text())
                DataManager.saveData("building", infos[8].text())
                DataManager.saveData("room", infos[9].text())
                DataManager.saveData("phone", infos[10].text())
                DataManager.saveData("place", infos[12].text())
                refreshUI()
            }
        }
    }


    fun fetchData() {
        if (account.value == "") {
            MyApp.context.getString(R.string.config_student_id).warningToast()
            return
        }
        ActivityCollector.finishTopActivity()
        start<ConfigActivity> {
            putExtra("fetchData", true)
        }
    }

    private fun refreshUI() {
        ActivityCollector.finishTopActivity()
        start<ConfigActivity>()
    }



}