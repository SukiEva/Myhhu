package top.sukiu.myhhu.adapter;

import android.content.SharedPreferences;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.DraggableModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import top.sukiu.myhhu.R;
import top.sukiu.myhhu.util.MyApplication;

import static android.content.Context.MODE_PRIVATE;

public class DragAndSwipeAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements DraggableModule {


    public DragAndSwipeAdapter(List<String> data) {
        super(R.layout.item_draggable_view, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, @NotNull String item) {
        Log.d("Convert", "yeh");
        SharedPreferences sp = MyApplication.context.getSharedPreferences("Clock", MODE_PRIVATE);
        helper.setImageResource(R.id.iv_head, R.drawable.home_other);
        helper.setText(R.id.tv, item);
        helper.setText(R.id.weburl, sp.getString(item, ""));
    }
}
