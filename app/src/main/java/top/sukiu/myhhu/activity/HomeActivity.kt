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
import top.sukiu.myhhu.util.LogUtil
import top.sukiu.myhhu.util.getHitokoto
import top.sukiu.myhhu.util.start
import top.sukiu.myhhu.util.transportStatusBar


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

    val handler = object : Handler(Looper.getMainLooper()) {
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
        var url: String? = null
        if (!item.isHeader) {
            when (item.name) {
                "服务" -> url = "http://www.hhu.edu.cn/xyfw/list.htm"
                "奥蓝系统" -> url = "http://smst.hhu.edu.cn/Mobile/login.aspx"
                "教务系统" -> url = "http://202.119.114.197/login"
                "创训管理" -> url = "http://sjjx.hhu.edu.cn/cxcy/Index.aspx"
                "信息门户" -> url = "http://my.hhu.edu.cn/login.portal"
            }
            url?.let {
                LogUtil.i("HomeActivity -> ", url)
                start(ServiceActivity::class.java, mapOf("weburl" to url))
                return
            }
            startActivity(Intent(this@HomeActivity, item.activity))
        }
    }

    private val homeItemData: ArrayList<HomeEntity>
        get() = arrayListOf(
            HomeEntity(headerTitle = "基础功能"),
            HomeEntity("成绩", LoginActivity::class.java, R.drawable.home_course),
            HomeEntity("课表", ScheduleActivity::class.java, R.drawable.home_schedule),
            HomeEntity("服务", ServiceActivity::class.java, R.drawable.home_service),
            HomeEntity("打卡", ClockInActivity::class.java, R.drawable.home_daka),
            HomeEntity(headerTitle = "快捷入口"),
            HomeEntity("奥蓝系统", ServiceActivity::class.java, R.drawable.home_alxt),
            HomeEntity("教务系统", ServiceActivity::class.java, R.drawable.home_jwxt),
            HomeEntity("创训管理", ServiceActivity::class.java, R.drawable.home_cxgl),
            HomeEntity("信息门户", ServiceActivity::class.java, R.drawable.home_xxmh)
        )


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


