package github.sukieva.hhu.ui.activity.config

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import github.sukieva.hhu.ui.components.InJavaScriptLocalObj
import github.sukieva.hhu.utils.DataManager
import github.sukieva.hhu.utils.infoToast
import kotlinx.coroutines.launch

class ConfigViewModel : ViewModel() {


    var account = mutableStateOf("")
    var password = mutableStateOf("")
    var wid = mutableStateOf("")
    var name = mutableStateOf("")
    var aid = mutableStateOf("")
    var institute = mutableStateOf("")
    var grade = mutableStateOf("")
    var mclass = mutableStateOf("")
    var building = mutableStateOf("")
    var room = mutableStateOf("")
    var phone = mutableStateOf("")
    val configUrl = "http://dailyreport.hhu.edu.cn/pdc/formDesign/showFormFilled?selfFormWid=A335B048C8456F75E0538101600A6A04&lwUserId=${account.value}"

    fun saveConfig() {
        viewModelScope.launch {
            DataManager.saveData("account", account.value)
            DataManager.saveData("password", password.value)
            DataManager.saveData("wid", wid.value)
            DataManager.saveData("name", name.value)
            DataManager.saveData("aid", aid.value)
            DataManager.saveData("institute", institute.value)
            DataManager.saveData("grade", grade.value)
            DataManager.saveData("mclass", mclass.value)
            DataManager.saveData("building", building.value)
            DataManager.saveData("room", room.value)
            DataManager.saveData("phone", phone.value)
        }
        "已保存配置".infoToast()
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
            mclass.value = DataManager.readData("mclass", "")
            building.value = DataManager.readData("building", "")
            room.value = DataManager.readData("room", "")
            phone.value = DataManager.readData("phone", "")
            if (wid.value == "") wid.value = "A335B048C8456F75E0538101600A6A04"
            return@launch
        }
    }

    fun getHtml() {
        val html = InJavaScriptLocalObj.webHtml
        println(html)
    }


}