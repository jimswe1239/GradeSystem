package UI;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import Logic.Component;
import Logic.Course;
import UI.Table.*;
import com.sun.xml.internal.bind.v2.TODO;

public class MainWindow extends GSFrame
{
    private Component rootNode = new Component("name");
    GSComponentNode treeRoot;

    private GSTree tree;
    private JScrollPane treePanel;
    private GSTable table;
    private JScrollPane tablePanel;

    private JPanel statisticPanel;
    private JLabel averageLabel = new JLabel("Average");
    private JTextField averageField;
    private JLabel medianLabel = new JLabel("Median");
    private JTextField medianField;
    private JLabel deviationLabel = new JLabel("Deviation");
    private JTextField deviationField;
    private JPanel curvePanel;
    private JPanel buttonPanel;
    private JButton okButton;
    private JButton cancelButton;
    public MainWindow(Course course) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException
    {
        rootNode = course.getRoot();
        initComponent(rootNode);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//we should ask if they want to save changes?
        setMinimumSize(getSize());
    }

    private void initComponent(Component rootComponent)
    {
        GridBagLayout globalGridBag = new GridBagLayout();
        GridBagConstraints globalC = new GridBagConstraints();
        JPanel panel = new JPanel(globalGridBag);
        //tree
        {
            treeRoot = initTree(rootNode);
            tree = new GSTree(treeRoot);
            GSTreeCellRenderer re = new GSTreeCellRenderer();
            tree.setCellRenderer(re);
            tree.setBackground(new Color(36, 41, 46));
            tree.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
            tree.addTreeSelectionListener(new TreeSelectionListener()
            {
                @Override
                public void valueChanged(TreeSelectionEvent e)
                {
                    GSComponentNode node = (GSComponentNode) tree.getLastSelectedPathComponent();
                    if(node == null)
                    {
                        return;
                    }

                    Component component = (Component) (node.getUserObject());
                    //TODO: add function of refreshing table relating to the select component
                    System.out.println(component);
                }
            });

            treePanel = new JScrollPane();
            treePanel.setViewportView(tree);

            globalC.gridx = 0;
            globalC.gridy = 0;
            globalC.gridwidth = 1;
            globalC.gridheight = 3;
            globalC.weighty = 1.0;
            globalC.fill = GridBagConstraints.BOTH;
            globalGridBag.addLayoutComponent(treePanel, globalC);
            panel.add(treePanel);
        }

        //table
        {
            table = refreshTable(treeRoot);
            tablePanel = new JScrollPane();
            tablePanel.setViewportView(table);

            globalC.gridx = 1;
            globalC.gridy = 0;
            globalC.gridwidth = 2;
            globalC.weightx = 1.0;
            globalC.weighty = 0.5;
            globalC.gridheight = 1;
            globalC.fill = GridBagConstraints.BOTH;
            globalGridBag.addLayoutComponent(tablePanel, globalC);
            panel.add(tablePanel);
        }

        //statistic
        {
            statisticPanel = new JPanel();
            //statisticPanel.setBackground(Color.red);

            //statistic information
            {
                GridBagLayout statisticGridBag = new GridBagLayout();
                GridBagConstraints statisticC = new GridBagConstraints();
                statisticC.anchor = GridBagConstraints.CENTER;
                statisticC.gridwidth = 1;
                statisticC.gridheight = 1;
                statisticC.insets = new Insets(5, 5, 10, 10);
                statisticPanel.setLayout(statisticGridBag);

                //Average
                {
                    statisticC.gridx = 0;
                    statisticC.gridy = 0;
                    statisticGridBag.addLayoutComponent(averageLabel, statisticC);
                    statisticPanel.add(averageLabel);

                    averageField = new JTextField(5);
                    statisticC.gridx = 1;
                    statisticC.gridy = 0;
                    statisticGridBag.addLayoutComponent(averageField, statisticC);
                    statisticPanel.add(averageField);
                }

                //Median
                {
                    statisticC.gridx = 2;
                    statisticC.gridy = 0;
                    statisticGridBag.addLayoutComponent(medianLabel, statisticC);
                    statisticPanel.add(medianLabel);

                    medianField = new JTextField(5);
                    statisticC.gridx = 3;
                    statisticC.gridy = 0;
                    statisticGridBag.addLayoutComponent(medianField, statisticC);
                    statisticPanel.add(medianField);
                }

                //Deviation
                {
                    statisticC.gridx = 0;
                    statisticC.gridy = 1;
                    statisticGridBag.addLayoutComponent(deviationLabel, statisticC);
                    statisticPanel.add(deviationLabel);

                    deviationField = new JTextField(5);
                    statisticC.gridx = 1;
                    statisticC.gridy = 1;
                    statisticGridBag.addLayoutComponent(deviationField, statisticC);
                    statisticPanel.add(deviationField);
                }
            }

            globalC.gridx = 1;
            globalC.gridy = 1;
            globalC.weighty = 0.3;
            globalC.gridwidth = 1;
            globalC.gridheight = 1;
            globalC.fill = GridBagConstraints.HORIZONTAL;
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
            globalC.fill = GridBagConstraints.BOTH;
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
            globalC.weighty = 0.2;
            globalC.gridwidth = 2;
            globalC.gridheight = 1;
            globalC.fill = GridBagConstraints.BOTH;
            globalGridBag.addLayoutComponent(buttonPanel, globalC);
            panel.add(buttonPanel);
        }

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
    }

    private GSComponentNode initTree(Component node)
    {
        GSComponentNode treeNode = new GSComponentNode(node);
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

    private GSTable refreshTable(GSComponentNode node)
    {
        String[] headerString = refreshTableHeader(node);
//        String[] headerString = new String[] {"section", "student", "participation", "class design", "correct", "comment", "hw2", "hw3", "exam1", "exam2", "total"};
        String[][] content = new String[][]{{"section", "student", "participation", "class design", "correct", "comment", "hw2", "hw3", "exam1", "exam2", "total"}};

        TableContent tableContent = new TableContent();
        {
            tableContent.append("A1", 1, 3);
            tableContent.append("Stu1");
            tableContent.println();
            tableContent.append("Stu2");
            tableContent.println();
            tableContent.append("Stu3");
            tableContent.println();
            tableContent.append("A2", 1, 3);
            tableContent.append("Stu4");
            tableContent.println();
            tableContent.append("Stu5");
            tableContent.println();
            tableContent.append("Stu6");
            tableContent.println();
        }

        GSTable table = tableContent.createTable(headerString);

        TableColumnModel cm = table.getColumnModel();
        ColumnGroup no = new ColumnGroup(" ");
        no.add(cm.getColumn(0));
        no.add(cm.getColumn(1));
//        ColumnGroup cs591 = new ColumnGroup("CS591");
//        cs591.add(cm.getColumn(2));
//        ColumnGroup homework = new ColumnGroup("Homework");
//        ColumnGroup hw1 = new ColumnGroup("Hw1");
//        hw1.add(cm.getColumn(3));
//        hw1.add(cm.getColumn(4));
//        hw1.add(cm.getColumn(5));
//        homework.add(hw1);
//        homework.add(cm.getColumn(6));
//        homework.add(cm.getColumn(7));
//        cs591.add(homework);
//        ColumnGroup exam = new ColumnGroup("Exam");
//        exam.add(cm.getColumn(8));
//        exam.add(cm.getColumn(9));
//        cs591.add(exam);
//        cs591.add(cm.getColumn(10));

        GroupableTableHeader header = (GroupableTableHeader) table.getTableHeader();
        int[] columnIndex = new int[1];
        columnIndex[0] = 2;
        ColumnGroup columnGroup = getColumnGroup(node, cm, columnIndex);
        columnGroup.add(cm.getColumn(columnIndex[0]));
        header.addColumnGroup(no);
        //header.addColumnGroup(cs591);
        header.addColumnGroup(columnGroup);
        table.getTableHeader().setUI(new GroupableTableHeaderUI());

        return table;
    }

    private String[] refreshTableHeader(GSComponentNode node)
    {
        ArrayList<String> headerList = new ArrayList<String>();
        headerList.add("section");
        headerList.add("student");
        Enumeration children = node.depthFirstEnumeration();
        while (children.hasMoreElements())
        {
            GSComponentNode next = (GSComponentNode)children.nextElement();
            if(next.isLeaf())
            {
                headerList.add(next.getName());
            }
        }
        headerList.add("total");
        String[] headerString = headerList.toArray(new String[headerList.size()]);
        return headerString;
    }

    private ColumnGroup getColumnGroup(GSComponentNode node, TableColumnModel cm, int[] columnIndex)
    {
        ColumnGroup columnGroup = new ColumnGroup(node.getName());

        Enumeration children = node.children();
        while(children.hasMoreElements())
        {
            GSComponentNode next = (GSComponentNode) children.nextElement();
            if(next.isLeaf())
            {
                columnGroup.add(cm.getColumn(columnIndex[0]++));
                continue;
            }
            columnGroup.add(getColumnGroup(next, cm, columnIndex));
        }
        return columnGroup;
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
            Component exam1 = new Component("Exam1");
            exam.addComponentAndScale(exam1, 50);
            Component exam2 = new Component("Exam2");
            exam.addComponentAndScale(exam2, 50);
        return root;
    }

    //public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
    //    MainWindow test = new MainWindow(initCourseForTest());
    //    test.setVisible(true);
    //}
}

class GSTree extends JTree
{
    public GSTree(GSComponentNode root)
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

class GSComponentNode extends DefaultMutableTreeNode
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



