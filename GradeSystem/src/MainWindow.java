import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MainWindow extends JFrame
{
    private JTree tree;
    public MainWindow()
    {
        ComponentNode root = initTree(initTreeFroTest());
        tree = new JTree(root);

        GridBagLayout treeGridBag = new GridBagLayout();
        GridBagConstraints treeC = new GridBagConstraints();
        JPanel treePanel = new JPanel();
        treeGridBag.addLayoutComponent(tree, treeC);
        treePanel.add(tree);

        GridBagLayout globalGridBag = new GridBagLayout();
        GridBagConstraints globalC = new GridBagConstraints();
        JPanel panel = new JPanel(globalGridBag);
        globalC.gridy = GridBagConstraints.REMAINDER;
        globalGridBag.addLayoutComponent(treePanel, globalC);
        panel.add(treePanel);

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
    }

    ComponentNode initTree(Component node)
    {
        ComponentNode treeNode = new ComponentNode(node);
        if(!node.children.isEmpty())
        {
            HashMap<Component,Double> children = node.children;
            for (Map.Entry<Component, Double> entry : children.entrySet())
            {
                DefaultMutableTreeNode next = initTree(entry.getKey());
                treeNode.add(next);
            }
        }

        return treeNode;
    }

    private Component initTreeFroTest()
    {
        Component root = new Component("CS591");
        Component participation = new Component("Participation");
        root.addComponentAndScale(participation, 10.0);
        Component homework = new Component("Homework");
        root.addComponentAndScale(homework, 50);
            Component hw1 = new Component("Hw1");
            homework.addComponentAndScale(hw1, 40);
                Component classDesign = new Component("Class Design");
                hw1.addComponentAndScale(classDesign, 40);
                Component correct = new Component("Correct");
                hw1.addComponentAndScale(correct, 30);
                Component comment = new Component("Comment");
                hw1.addComponentAndScale(comment, 30);
            Component hw2 = new Component("Hw2");
            homework.addComponentAndScale(hw2, 30);
            Component hw3 = new Component("Hw3");
            homework.addComponentAndScale(hw3, 30);
        Component exam = new Component("Exam");
        root.addComponentAndScale(exam, 40);
        return root;
    }

    public static void main(String[] args)
    {
        MainWindow test = new MainWindow();
        test.setVisible(true);
    }

    class ComponentNode extends DefaultMutableTreeNode
    {
        public ComponentNode(Component node)
        {
            super(node);
        }
    }
}


