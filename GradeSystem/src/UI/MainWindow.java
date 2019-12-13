package UI;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import Logic.*;
import Logic.Component;
import UI.Table.*;
import com.sun.xml.internal.bind.v2.TODO;

public class MainWindow extends GSFrame
{
    private Component rootNode = new Component("name");
    GSComponentNode treeRoot;
    private Course course;
    private GradeMap gradeMapCache;
    private School school;
    private GSComponentNode curNode;

    private GSTree tree;
    private JScrollPane treePanel;
    private GSTable table;
    private JScrollPane tablePanel;
    private JPanel statisticPanel;
    private JLabel averageLabel = new JLabel("Average");
    private JTextField averageField;
    private JLabel medianLabel = new JLabel("Median");
    private JTextField medianField;
    private JLabel standardDeviationLabel = new JLabel("Standard Deviation");
    private JTextField standardDeviationField;
    private JPanel curvePanel;
    private JLabel curveLabel = new JLabel("Curve Number");
    private JTextField curveField;
    private JButton curveButton;
    private JPanel buttonPanel;
    private JButton okButton;
    private JButton cancelButton;
    public MainWindow(Course course, School school) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException
    {
        this.school = school;
        this.course = course;
        //this.gradeMapCache = course.getGradeMap().deepCopy();
        rootNode = course.getRoot();
        initComponent();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//we should ask if they want to save changes?
        setMinimumSize(getSize());
        treePanel.setMinimumSize(treePanel.getSize());
    }

    private void initComponent()
    {
        GridBagLayout globalGridBag = new GridBagLayout();
        GridBagConstraints globalC = new GridBagConstraints();
        JPanel panel = new JPanel(globalGridBag);
        //tree
        {
            treeRoot = initTree(rootNode);
            curNode = treeRoot;
            tree = new GSTree(treeRoot);
            GSTreeCellRenderer re = new GSTreeCellRenderer();
            tree.setCellRenderer(re);
            //tree.setBackground(new Color(36, 41, 46));
            tree.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
            tree.addTreeSelectionListener(new TreeSelectionListener()
            {
                @Override
                public void valueChanged(TreeSelectionEvent e)
                {
                    GSComponentNode node = (GSComponentNode) tree.getLastSelectedPathComponent();
                    curNode = node;
                    if(node == null)
                    {
                        return;
                    }

                    Component component = (Component) (node.getUserObject());
                    table = refreshTable(node);
                    refreshStatisticInfo(node);
                    tablePanel.getViewport().add(table, null);
                    tablePanel.revalidate();
                    System.out.println(component);
                }
            });

            treePanel = new JScrollPane();
//            Dimension d = treePanel.getSize();
//            System.out.println("width: " + d.width);
//            System.out.println("height: " + d.height);
//            treePanel.setMinimumSize(d);
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
            globalC.weightx = 0.7;
            globalC.weighty = 0.5;
            globalC.gridheight = 1;
            globalC.fill = GridBagConstraints.BOTH;
            globalGridBag.addLayoutComponent(tablePanel, globalC);
            panel.add(tablePanel);
        }

        //statistic
        {
            statisticPanel = new JPanel();
            statisticPanel.setBorder(BorderFactory.createTitledBorder("Statistic"));
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
                    averageField.setEditable(false);
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
                    medianField.setEditable(false);
                    statisticC.gridx = 3;
                    statisticC.gridy = 0;
                    statisticGridBag.addLayoutComponent(medianField, statisticC);
                    statisticPanel.add(medianField);
                }

                //Deviation
                {
                    statisticC.gridx = 0;
                    statisticC.gridy = 1;
                    statisticGridBag.addLayoutComponent(standardDeviationLabel, statisticC);
                    statisticPanel.add(standardDeviationLabel);

                    standardDeviationField = new JTextField(5);
                    standardDeviationField.setEditable(false);
                    statisticC.gridx = 1;
                    statisticC.gridy = 1;
                    statisticGridBag.addLayoutComponent(standardDeviationField, statisticC);
                    statisticPanel.add(standardDeviationField);
                }
            }

            refreshStatisticInfo(treeRoot);

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
            {
                GridBagLayout curveGridBag = new GridBagLayout();
                GridBagConstraints curveC = new GridBagConstraints();
                curveC.anchor = GridBagConstraints.CENTER;
                curveC.gridwidth = 1;
                curveC.gridheight = 1;
                curveC.insets = new Insets(5, 5, 10, 10);
                curvePanel.setLayout(curveGridBag);
                curvePanel.setBorder(BorderFactory.createTitledBorder("Curve"));
                curveField = new JTextField(5);
                curveButton = new JButton("OK");
                curveButton.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        double curveNumber = Double.valueOf(curveField.getText());
                        //TODO: call curve function here
                        course.setEndBonus(curveNumber);
//                        table = refreshTable(curNode);
//                        refreshStatisticInfo(curNode);
//                        tablePanel.getViewport().add(table, null);
//                        tablePanel.revalidate();
                        refreshTableNStatistic();
                    }
                });

                curveC.gridx = 0;
                curveC.gridy = 0;
                curveGridBag.addLayoutComponent(curveLabel, curveC);
                curvePanel.add(curveLabel);

                curveC.gridx = 1;
                curveC.gridy = 0;
                curveGridBag.addLayoutComponent(curveField, curveC);
                curvePanel.add(curveField);

                curveC.gridx = 0;
                curveC.gridy = 1;
                curveC.gridwidth = 2;
                curveC.gridheight = 1;
                curveGridBag.addLayoutComponent(curveButton, curveC);
                curvePanel.add(curveButton);
            }
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
            buttonC.anchor = GridBagConstraints.CENTER;
            buttonC.gridwidth = 1;
            buttonC.gridheight = 1;
            buttonC.insets = new Insets(5, 5, 10, 10);
            buttonPanel = new JPanel(buttonGridBag);
            {
                okButton = new JButton("OK");
                okButton.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {

                        school.save();
                        refreshTableNStatistic();
                    }
                });
                cancelButton = new JButton("Cancel");
                okButton.setPreferredSize(cancelButton.getPreferredSize());

                buttonC.gridx = 0;
                buttonC.gridy = 0;
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

    public void refreshTableNStatistic()
    {
        table = refreshTable(curNode);
        refreshStatisticInfo(curNode);
        tablePanel.getViewport().add(table, null);
        tablePanel.revalidate();
    }

    private GSTable refreshTable(GSComponentNode node)
    {
        String[] headerString = refreshTableHeader(node);

        TableContent tableContent = refreshTableContent(node);
//        TableContent tableContent = new TableContent();
//        {
//            tableContent.append("A1", 1, 3);
//            tableContent.append("Stu1");
//            tableContent.println();
//            tableContent.append("Stu2");
//            tableContent.println();
//            tableContent.append("Stu3");
//            tableContent.println();
//            tableContent.append("A2", 1, 3);
//            tableContent.append("Stu4");
//            tableContent.println();
//            tableContent.append("Stu5");
//            tableContent.println();
//            tableContent.append("Stu6");
//            tableContent.println();
//        }

        GSTable table = tableContent.createTable(headerString);
        table.setNode(node);
        table.setCourse(course);
        table.setMainWindow(this);

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

        header.addColumnGroup(no);
        //header.addColumnGroup(cs591);
        if(!node.isLeaf())
        {
            ColumnGroup columnGroup = getColumnGroup(node, cm, columnIndex);
            columnGroup.add(cm.getColumn(columnIndex[0]));
            header.addColumnGroup(columnGroup);
        }
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

    private TableContent refreshTableContent(GSComponentNode node)
    {
        TableContent tableContent = new TableContent();

        ArrayList<Section> sections =  course.getSections();
        for(Section section : sections) // traverse all sections
        {
            ArrayList<Student> students = section.getStudentList();
            int studentNum = section.getStudentList().size();
            if(studentNum == 0)
            {
                continue;
            }
            tableContent.append(section.name, 1, studentNum);   // add the section row header
            for(Student student : students) // traverse all students in this section
            {
                tableContent.append(student.toString());    // student's name
                //TODO: load table content

                Enumeration items = node.depthFirstEnumeration();
                while(items.hasMoreElements())
                {
                    GSComponentNode next = (GSComponentNode)items.nextElement();
                    if(next.isLeaf())
                    {
                        Component component = (Component) next.getUserObject();
                        if(course.getGradeOfOneStudent(student, component)==-1)
                        {
                            tableContent.append("");
                        }
                        else
                            {
                            tableContent.append(course.getGradeOfOneStudent(student, component));
                        }
                    }
                }

                double finalScore = -1;
                try
                {
                    finalScore = course.getFinalScore(student, (Component) node.getUserObject());
                }
                catch (NullPointerException e)
                {
                    finalScore = -1;
                }
                if(finalScore == -1)
                {
                    tableContent.append("");
                }
                else
                {
                    tableContent.append(finalScore);
                }
                tableContent.println();
            }
        }

        return tableContent;
    }

    private void refreshStatisticInfo(GSComponentNode node)
    {
        double average = 0;
        double median = 0;
        double standardDeviation = 0;
        try
        {
            average = course.getFinalAverage((Component) node.getUserObject());
            median = course.getFinalMedian((Component) node.getUserObject());
            standardDeviation = course.getFinalStandardDeviation((Component) node.getUserObject());
        }
        catch (NullPointerException e)
        {
            average = 0;
            median = 0;
            standardDeviation = 0;
        }

        averageField.setText(String.valueOf(average));
        medianField.setText(String.valueOf(median));
        standardDeviationField.setText(String.valueOf(standardDeviation));
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
        //setBackground(new Color(28,40,51));
        putClientProperty("lineStyle","None");
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
        //setBackgroundNonSelectionColor(new Color(28,40,51));
        //setTextNonSelectionColor(Color.WHITE);
        setOpenIcon(null);
    }
}



