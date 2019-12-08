package UI;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import Logic.Component;

public class MainWindow extends GSFrame
{
    private Component rootNode = new Component("name");

    private GSTree tree;
    private JScrollPane treePanel;
    private GSTable table;
    private JScrollPane tablePanel;
    private JPanel statisticPanel;
    private JPanel curvePanel;
    private JPanel buttonPanel;
    private JButton okButton;
    private JButton cancelButton;
    public MainWindow() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException
    {
        rootNode = initTreeFroTest();
        initComponent();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initComponent()
    {
        GridBagLayout globalGridBag = new GridBagLayout();
        GridBagConstraints globalC = new GridBagConstraints();
        JPanel panel = new JPanel(globalGridBag);
        //tree
        {
            ComponentNode root = initTree(rootNode);
            tree = new GSTree(root);
            GSTreeCellRenderer re = new GSTreeCellRenderer();
            tree.setCellRenderer(re);
            tree.setBackground(new Color(36, 41, 46));
            tree.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));

            treePanel = new JScrollPane();
            treePanel.setViewportView(tree);

            globalC.gridx = 0;
            globalC.gridy = 0;
            globalC.gridwidth = 1;
            globalC.gridheight = 3;
            globalGridBag.addLayoutComponent(treePanel, globalC);
            panel.add(treePanel);
        }

        //table
        {
            table = new GSTable();
            tablePanel = new JScrollPane();
            tablePanel.setViewportView(table);

            globalC.gridx = 1;
            globalC.gridy = 0;
            globalC.weightx = 2;
            globalC.gridheight = 1;
            globalGridBag.addLayoutComponent(tablePanel, globalC);
            panel.add(tablePanel);
        }

        //statistic
        {
            statisticPanel = new JPanel();
            statisticPanel.setBackground(Color.red);

            globalC.gridx = 1;
            globalC.gridy = 1;
            globalC.gridwidth = 1;
            globalC.gridheight = 1;
            globalGridBag.addLayoutComponent(statisticPanel, globalC);
            panel.add(statisticPanel);
        }

        //curve
        {
            curvePanel = new JPanel();
            curvePanel.setBackground(Color.blue);

            globalC.gridx = 2;
            globalC.gridy = 1;
            globalC.gridwidth = 1;
            globalC.gridheight = 1;
            globalGridBag.addLayoutComponent(curvePanel, globalC);
            panel.add(curvePanel);
        }

        //button
        {
            GridBagLayout buttonGridBag = new GridBagLayout();
            GridBagConstraints buttonC = new GridBagConstraints();
            buttonPanel = new JPanel(buttonGridBag);
            {
                okButton = new JButton("OK");
                cancelButton = new JButton("Cancel");

                buttonC.gridx = 0;
                buttonC.gridy = 0;
                buttonC.gridwidth = 1;
                buttonC.gridheight = 1;
                buttonGridBag.addLayoutComponent(okButton, buttonC);
                buttonC.gridx = 1;
                buttonC.gridy = 0;
                buttonGridBag.addLayoutComponent(cancelButton, buttonC);
                buttonPanel.add(okButton);
                buttonPanel.add(cancelButton);
            }

            globalC.gridx = 1;
            globalC.gridy = 2;
            globalC.gridwidth = 2;
            globalC.gridheight = 1;
            globalGridBag.addLayoutComponent(buttonPanel, globalC);
            panel.add(buttonPanel);
        }

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

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        MainWindow test = new MainWindow();
        test.setVisible(true);
    }
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


