package top.sukiu.myhhu.activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.entity.node.BaseNode
import kotlinx.android.synthetic.main.activity_show_results.*
import org.jetbrains.anko.startActivity
import top.sukiu.myhhu.R
import top.sukiu.myhhu.adapter.NodeTreeAdapter
import top.sukiu.myhhu.bean.Course
import top.sukiu.myhhu.bean.Grades
import top.sukiu.myhhu.bean.Rank
import top.sukiu.myhhu.node.FirstNode
import top.sukiu.myhhu.node.SecondNode
import top.sukiu.myhhu.node.ThirdNode
import java.util.*


class ShowResultsActivity : AppCompatActivity() {


    var grades: MutableList<Course>? = null
    var terms: MutableMap<String, MutableList<Course>> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TransportStatusBar()
        setContentView(R.layout.activity_show_results)
        setSupportActionBar(results_bar)
        supportActionBar?.title = "Grades"

        val nodeAdapter = NodeTreeAdapter()

        rv_list.layoutManager = LinearLayoutManager(this)
        rv_list.adapter = nodeAdapter

        nodeAdapter.setList(getEntity())
        val info = intent.getSerializableExtra("rank") as Rank
        to_rank.setOnClickListener {
            startActivity<ShowRankActivity>(
                "rank" to info
            )
        }
    }

    private fun devideGrades() {
        val courses = intent.getSerializableExtra("grades") as Grades
        grades = courses.grades
        if (grades == null) {
            terms.set(
                "还没有成绩哟>_<", mutableListOf(
                    Course(
                        "课程名", "必修",
                        100.0, 5.0, 1.0,
                        "2019-2020"
                    )
                )
            )
            return
        }
        grades!!.sortByDescending { it.academicYearCode }
        for (grade in grades!!) {
            val termcode = grade.academicYearCode
            if (!terms.containsKey(termcode)) { // 学期还未出现
                val tmp = mutableListOf<Course>()
                tmp.add(grade)
                terms.set(termcode, tmp)
            } else {
                val tmp = terms.get(termcode)
                tmp!!.add(grade)
                terms.set(termcode, tmp)
            }
        }
    }

    private fun getEntity(): List<BaseNode> {
        devideGrades()
        val list: MutableList<BaseNode> = ArrayList()
        var flag = true
        for ((key, value) in terms) {
            val secondNodeList: MutableList<BaseNode> = ArrayList()
            value.sortBy { it.courseAttributeName }
            for (course in value) {
                val thirdNodeList: MutableList<BaseNode> = ArrayList()
                val AttributeName = ThirdNode("课程属性: " + course.courseAttributeName)
                val PointScore = ThirdNode("绩点: " + course.gradePointScore.toString())
                val Credit = ThirdNode("学分: " + course.credit.toString())
                thirdNodeList.add(AttributeName)
                thirdNodeList.add(Credit)
                thirdNodeList.add(PointScore)
                val seNode = SecondNode(thirdNodeList, course.courseName, course.courseScore.toString())
                secondNodeList.add(seNode)
            }
            val entity = FirstNode(secondNodeList, key) // 学期
            if (flag) { // 展开第一列
                entity.isExpanded = true
                flag = false
            }
            list.add(entity)
        }
        return list
    }

    @Suppress("DEPRECATION")
    private fun TransportStatusBar() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

}