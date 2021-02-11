package top.sukiu.myhhu.node;

import com.chad.library.adapter.base.entity.node.BaseNode;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemNode extends BaseNode {


    private String name;


    public ItemNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return null;
    }
}
