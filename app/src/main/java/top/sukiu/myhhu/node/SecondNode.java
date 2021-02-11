package top.sukiu.myhhu.node;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SecondNode extends BaseExpandNode {

    private List<BaseNode> childNode;
    private String title;
    private String content;

    public SecondNode(List<BaseNode> childNode, String title, String content) {
        this.childNode = childNode;
        this.title = title;
        this.content = content;
        setExpanded(false);
    }

    public String getTitle() {
        return title;
    }
    public String getContent(){return content;}
    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return childNode;
    }
}
