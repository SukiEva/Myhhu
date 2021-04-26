package top.sukiu.myhhu.activity

import android.graphics.Canvas
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.listener.OnItemSwipeListener
import kotlinx.android.synthetic.main.activity_set_entry.*
import top.sukiu.myhhu.R
import top.sukiu.myhhu.adapter.DragAndSwipeAdapter
import top.sukiu.myhhu.util.*


class SetEntryActivity : AppCompatActivity() {

    private val TAG = "SetEntryActivity"
    private var mAdapter: DragAndSwipeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_entry)
        setSupportActionBar(set_entry_bar)
        supportActionBar?.title = "Set Entry"

        transportStatusBar(this, window, set_entry_bar)
        val mRecyclerView = rv
        mRecyclerView.layoutManager = LinearLayoutManager(this);

        val onItemSwipeListener = object : OnItemSwipeListener {
            private var item = ""

            override fun onItemSwipeStart(viewHolder: RecyclerView.ViewHolder?, pos: Int) {
                item = mAdapter?.getItem(pos).toString()
                LogUtil.d(TAG, "view swiped start: $pos $item")
            }

            override fun clearView(viewHolder: RecyclerView.ViewHolder?, pos: Int) {
                LogUtil.d(TAG, "View reset: $pos")
            }

            override fun onItemSwiped(viewHolder: RecyclerView.ViewHolder?, pos: Int) {
                LogUtil.d(TAG, "View Swiped: $pos")
                removeSP(item)
                val stringSet = getSPSet("WEB")
                stringSet!!.remove(item)
                setSPSet("WEB", stringSet)
                toast("重启应用生效")
            }

            override fun onItemSwipeMoving(
                canvas: Canvas?,
                viewHolder: RecyclerView.ViewHolder?,
                dX: Float,
                dY: Float,
                isCurrentlyActive: Boolean
            ) {
                canvas?.drawColor(
                    ContextCompat.getColor(
                        this@SetEntryActivity,
                        R.color.color_light_blue
                    )
                );
            }
        }

        val mData: List<String> = generateData()
        mAdapter = DragAndSwipeAdapter(mData)
        mAdapter?.let {
            it.draggableModule.isSwipeEnabled = true
            it.draggableModule.isDragEnabled = false
            it.draggableModule.setOnItemSwipeListener(onItemSwipeListener)
            it.draggableModule.itemTouchHelperCallback
                .setSwipeMoveFlags(ItemTouchHelper.START or ItemTouchHelper.END)
            mRecyclerView.adapter = mAdapter
        }

    }

    private fun generateData(): List<String> {
        val data: ArrayList<String> = ArrayList()
        val stringSet = getSPSet("WEB")
        stringSet?.let {
            for (web in stringSet)
                data.add(web)
        }
        return data
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.adding -> alertEdit(this)
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.entry_bar_meun, menu)
        return true
    }

}