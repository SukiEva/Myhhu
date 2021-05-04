package top.sukiu.myhhu.activity


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_show_results.*
import top.sukiu.myhhu.R
import top.sukiu.myhhu.adapter.HomeAdapter
import top.sukiu.myhhu.bean.HomeEntity
import top.sukiu.myhhu.util.*


class HomeActivity : AppCompatActivity(), OnItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(home_bar)
        supportActionBar?.title = "Myhhu"
        getHitokoto(handler)

        transportStatusBar(this, window, home_bar)
        mRecyclerView.adapter = homeAdapter
    }

    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 111) {
                supportActionBar?.subtitle = msg.obj.toString()
                LogUtil.i("Hitikoto", "Successfully Get Hitikoto")
            } else {
                supportActionBar?.subtitle = "所幸南风知我意，吹梦到西洲。"
                LogUtil.i("Hitikoto", "Failed to Get Hitikoto")
            }
        }
    }

    /**
     * RV适配器
     */
    private val homeAdapter by lazy {
        HomeAdapter(homeItemData).apply {
            animationEnable = true
            setOnItemClickListener(this@HomeActivity)
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val item = adapter.data[position] as HomeEntity
        if (item.headerTitle == "快捷入口") {
            start(SetEntryActivity::class.java)
            return
        }
        if (!item.isHeader) {
            var url = sp(item.name) as String
            when (item.name) {
                "服务" -> url = "http://www.hhu.edu.cn/xyfw/list.htm"
                "成绩" -> {
                    startActivity(Intent(this@HomeActivity, item.activity))
                    return
                }
                "课表" -> {
                    startActivity(Intent(this@HomeActivity, item.activity))
                    return
                }
                "打卡" -> {
                    startActivity(Intent(this@HomeActivity, item.activity))
                    return
                }
            }
            if (url == "") {
                alert(this, message = "网址不能为空")
                return
            }
            url.let {
                LogUtil.i("HomeActivity -> ", url)
                start(ServiceActivity::class.java, mapOf("weburl" to url))
                return
            }
        }
    }

    private val homeItemData: ArrayList<HomeEntity>
        get() = checkWebData()

    private fun checkWebData(): ArrayList<HomeEntity> {
        var stringSet = getSPSet("WEB")
        if (stringSet == null || stringSet.size == 0) {
            setSP("奥蓝系统", "http://smst.hhu.edu.cn/Mobile/login.aspx")
            setSP("教务系统", "http://202.119.114.197/login")
            setSP("创训管理", "http://sjjx.hhu.edu.cn/cxcy/Index.aspx")
            setSP("信息门户", "http://myold.hhu.edu.cn/index.portal")
            stringSet = mutableSetOf("奥蓝系统", "教务系统", "创训管理", "信息门户")
            setSPSet("WEB", stringSet)
        }
        val webList = mutableListOf(
            HomeEntity(headerTitle = "基础功能"),
            HomeEntity("成绩", LoginActivity::class.java, R.drawable.home_course),
            HomeEntity("课表", ScheduleActivity::class.java, R.drawable.home_schedule),
            HomeEntity("服务", ServiceActivity::class.java, R.drawable.home_service),
            HomeEntity("打卡", ClockInActivity::class.java, R.drawable.home_daka),
            HomeEntity(headerTitle = "快捷入口"),
        )
        for (web in stringSet)
            if (web != "") {
                var image = R.drawable.home_other
                when (web) {
                    "奥蓝系统" -> image = R.drawable.home_alxt
                    "教务系统" -> image = R.drawable.home_jwxt
                    "创训管理" -> image = R.drawable.home_cxgl
                    "信息门户" -> image = R.drawable.home_xxmh
                }
                webList.add(HomeEntity(web, imageResource = image))
            }
        return webList as ArrayList<HomeEntity>
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setting ->
                start(AboutActivity::class.java)
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_bar_menu, menu)
        return true
    }
}


