package UI;

import Logic.Component;

import java.util.Enumeration;

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
    public boolean checkChildrenSumIs100() {
//    	Enumeration enumeration;
//    	enumeration = this.children();
//    	while(enumeration.hasMoreElements()) {
//    		GSComponentNode node = (GSComponentNode) enumeration
//					.nextElement();
//    		
//    	}
    	boolean childrenResult = true;
    	GSComponentNode node;
    	double sum = 0;
    	int childrenCount = this.getChildCount();
    	for(int i=0; i<childrenCount; i++) {
    		node = (GSComponentNode)this.getChildAt(i);
    		Component c=(Component)node.getUserObject();
    		sum+=c.getPercentage();
    		
    		if(node.checkChildrenSumIs100()==false) {
    			childrenResult = false;
    		}
    	}
    	if(sum!=100) {
    		//TODO: Make the node highlight
    		return false;

    	}
    	
    	return true;
    }
}