package UI;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import Logic.Component;
import Logic.Course;

public class MainWindow extends GSFrame
{
    private GSTree tree;
    private JScrollPane treePanel;
    private GSTable table;
    private JScrollPane tablePanel;
    public MainWindow(Course course) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException
    {
        initComponent(course.getRoot());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//we should ask if they want to save changes?
    }

    private void initComponent(Component rootComponent)
    {
        //tree
        {
            ComponentNode root = initTree(rootComponent);
            tree = new GSTree(root);
            GSTreeCellRenderer re = new GSTreeCellRenderer();
            tree.setCellRenderer(re);
            tree.setBackground(new Color(36, 41, 46));
            tree.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));

            treePanel = new JScrollPane();
            treePanel.setViewportView(tree);
            //treePanel.setBackground(new Color(28,40,51));
        }

        //table
        {
            table = new GSTable();

            tablePanel = new JScrollPane();
            tablePanel.setViewportView(table);
            //tablePanel.setBackground(new Color());
        }

        GridBagLayout globalGridBag = new GridBagLayout();
        GridBagConstraints globalC = new GridBagConstraints();
        JPanel panel = new JPanel(globalGridBag);
        globalC.gridx = 0;
        globalC.gridy = 0;
        globalC.gridwidth = 1;
        globalC.gridheight = 3;
        globalGridBag.addLayoutComponent(treePanel, globalC);

        globalGridBag.addLayoutComponent(tablePanel, globalC);
        panel.add(treePanel);
        panel.add(tablePanel);
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

    private static Course initCourseForTest() {
    	return new Course("Test Course", initTreeForTest());
    }
    
    private static Component initTreeForTest()
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

    //public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
    //    MainWindow test = new MainWindow(initCourseForTest());
    //    test.setVisible(true);
    //}
}

class GSTree extends JTree
{
    public GSTree(ComponentNode root)
    {
        super(root);
        setFeatures();
    }

    private void setFeatures()
    {
        setBackground(new Color(28,40,51));
        putClientProperty("lineStyle","None");
    }
}

class ComponentNode extends DefaultMutableTreeNode
{
    public ComponentNode(Component node)
    {
        super(node);
    }
}

class GSTreeCellRenderer extends DefaultTreeCellRenderer
{
    public GSTreeCellRenderer()
    {
        super();
        setFeatures();
    }

    private void setFeatures()
    {
        setBackground(new Color(28,40,51));
        setBackgroundNonSelectionColor(new Color(28,40,51));
        setTextNonSelectionColor(Color.WHITE);
        setOpenIcon(null);
    }
}

class GSTable extends JTable
{
    public GSTable()
    {
        super();
    }
}


