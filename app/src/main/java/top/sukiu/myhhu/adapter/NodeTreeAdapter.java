package top.sukiu.myhhu.adapter;

import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import org.jetbrains.annotations.NotNull;
import top.sukiu.myhhu.adapter.provider.FirstProvider;
import top.sukiu.myhhu.adapter.provider.SecondProvider;
import top.sukiu.myhhu.adapter.provider.ThirdProvider;
import top.sukiu.myhhu.node.FirstNode;
import top.sukiu.myhhu.node.SecondNode;
import top.sukiu.myhhu.node.ThirdNode;

import java.util.List;

public class NodeTreeAdapter extends BaseNodeAdapter {

    public NodeTreeAdapter() {
        super();
        addNodeProvider(new FirstProvider());
        addNodeProvider(new SecondProvider());
        addNodeProvider(new ThirdProvider());
    }

    @Override
    protected int getItemType(@NotNull List<? extends BaseNode> data, int position) {
        BaseNode node = data.get(position);
        if (node instanceof FirstNode) {
            return 1;
        } else if (node instanceof SecondNode) {
            return 2;
        } else if (node instanceof ThirdNode) {
            return 3;
        }
        return -1;
    }

    public static final int EXPAND_COLLAPSE_PAYLOAD = 110;
}
