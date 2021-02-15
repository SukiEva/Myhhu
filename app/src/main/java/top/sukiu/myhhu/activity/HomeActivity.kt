package top.sukiu.myhhu.activity


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_show_results.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.browse
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jsoup.Jsoup
import top.sukiu.myhhu.R
import top.sukiu.myhhu.adapter.HomeAdapter
import top.sukiu.myhhu.bean.HomeEntity


class HomeActivity : AppCompatActivity(), OnItemClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TransportStatusBar()
        setContentView(R.layout.activity_home)
        setSupportActionBar(home_bar)
        setOneyan()
        supportActionBar?.title = "Myhhu"
        mRecyclerView.adapter = homeAdapter
    }

    @SuppressLint("HandlerLeak")
    @Suppress("DEPRECATION")
    private val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 111) {
                supportActionBar?.subtitle = msg.obj.toString()
            } else
                supportActionBar?.subtitle = "所幸南风知我意，吹梦到西洲。"
        }
    }

    private fun setOneyan() {
        Thread(
            object : Runnable {
                override fun run() {
                    try {
                        val connect = Jsoup.connect("https://v1.hitokoto.cn/?encode=text&c=i")
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4209.2 Safari/537.36")
                            .timeout(2000)
                            .ignoreContentType(true)
                        val one = connect.get().text()
                        val meg = Message.obtain()
                        meg.what = 111
                        meg.obj = one
                        handler.sendMessage(meg)
                    } catch (e: Exception) {
                        AnkoLogger<HomeActivity>().info { "Yiyan Error:" + e.stackTrace }
                        val meg = Message.obtain()
                        meg.what = 112
                        handler.sendMessage(meg)
                        e.printStackTrace()
                    }
                }
            }
        ).start()
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
        if (!item.isHeader) {
            val webname = item.name
            when {
                webname.equals("奥蓝系统") -> {
                    browse("http://smst.hhu.edu.cn/LOGIN.ASPX")
                    return
                }
                webname.equals("教务系统") -> {
                    browse("http://202.119.114.197/login")
                    return
                }
                webname.equals("创训管理") -> {
                    browse("http://sjjx.hhu.edu.cn/cxcy/Index.aspx")
                    return
                }
                webname.equals("信息门户") -> {
                    browse("http://my.hhu.edu.cn/login.portal")
                    return
                }
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
            HomeEntity("奥蓝系统", HomeActivity::class.java, R.drawable.home_alxt),
            HomeEntity("教务系统", HomeActivity::class.java, R.drawable.home_jwxt),
            HomeEntity("创训管理", HomeActivity::class.java, R.drawable.home_cxgl),
            HomeEntity("信息门户", HomeActivity::class.java, R.drawable.home_xxmh)
        )

    @Suppress("DEPRECATION")
    private fun TransportStatusBar() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(Color.TRANSPARENT)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when {
            item.itemId == R.id.setting -> startActivity<AboutActivity>()
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_bar_menu, menu)
        return true
    }
}


