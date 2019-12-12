package UI;

import Logic.Component;

import javax.swing.tree.DefaultMutableTreeNode;

public class GSComponentNode extends DefaultMutableTreeNode
{
    public GSComponentNode(Component node)
    {
        super(node);
    }

    public String getName()
    {
        Component component = (Component) getUserObject();
        return component.getName();
    }
}