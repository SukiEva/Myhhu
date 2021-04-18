package top.sukiu.myhhu.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_clock_in_set.*
import kotlinx.coroutines.launch
import top.sukiu.myhhu.R
import top.sukiu.myhhu.util.UserData
import top.sukiu.myhhu.util.addData
import top.sukiu.myhhu.util.start
import top.sukiu.myhhu.util.transportStatusBar

class ClockInSetActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_clock_in_set)
        setSupportActionBar(clock_in_set_bar)
        supportActionBar?.title = "Setting"
        transportStatusBar(this, window, clock_in_set_bar)


        ShowMessage()
        SaveButton.setOnClickListener { saveButtonHandle() }
        CatchButton.setOnClickListener {
            start(CatchInfoActivity::class.java)
            finish()
        }
    }

    private fun ShowMessage() {
        lifecycleScope.launch {
            UserData.setData()
            account.setText(UserData.account)
            wid.setText(UserData.wid)
            Name.setText(UserData.Name)
            SelfAccount.setText(UserData.SelfAccount)
            Institute.setText(UserData.Institute)
            Grade.setText(UserData.Grade)
            Major.setText(UserData.Major)
            Classes.setText(UserData.Classes)
            Building.setText(UserData.Building)
            Room.setText(UserData.Room)
            PhoneNum.setText(UserData.PhoneNum)
            Address.setText(UserData.Address)
        }
    }


    private fun saveButtonHandle() {
        lifecycleScope.launch {
            addData("account", account.text.toString())
            addData("wid", wid.text.toString())
            addData("Name", Name.text.toString())
            addData("SelfAccount", SelfAccount.text.toString())
            addData("Institute", Institute.text.toString())
            addData("Grade", Grade.text.toString())
            addData("Major", Major.text.toString())
            addData("Classes", Classes.text.toString())
            addData("Building", Building.text.toString())
            addData("Room", Room.text.toString())
            addData("PhoneNum", PhoneNum.text.toString())
            addData("Address", Address.text.toString())
        }
        finish()
    }

}