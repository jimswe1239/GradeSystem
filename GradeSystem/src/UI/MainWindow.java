package UI;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import java.awt.*;
import java.awt.event.*;
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
    private School school;
    private GSComponentNode curNode;
    private EntryWindow father;
    private boolean modified = false;
    private MainWindow self = this;

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
    private JButton saveButton;
    private JButton cancelButton;
    public MainWindow(Course course, School school, EntryWindow father) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException
    {
        this.father = father;
        this.school = school;
        this.course = course;
        //this.gradeMapCache = course.getGradeMap().deepCopy();
        rootNode = course.getRoot();
        initComponent();
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                if(modified)
                {
                    int ret = JOptionPane.showConfirmDialog(null, "Do you want to save change?", "Attention", JOptionPane.OK_OPTION, JOptionPane.NO_OPTION);
                    if (ret == JOptionPane.OK_OPTION)
                    {
                        school.save();
                    }
                }
                closeFrame();
            }
        });
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
                    
                }
            });
            tree.addMouseListener(new MouseAdapter() {
            	public void mouseClicked(MouseEvent e) {
            		JPopupMenu menu = new JPopupMenu();
    				JMenuItem add,delete,changeScale,rename;
    				JMenuItem category, template;
    				JMenu m = new JMenu("Save as");
    				category = new JMenuItem("Template");
    				m.add(category = new JMenuItem("Template"));
    				menu.add(add = new JMenuItem("Add"));
    	            menu.add(delete = new JMenuItem("Delete"));
    	            menu.add(rename = new JMenuItem("Rename"));
    	            menu.add(changeScale = new JMenuItem("Change scale"));
    	            menu.add(m);
    				
            		if(e.getButton()==MouseEvent.BUTTON3) {
            			TreePath path = tree.getPathForLocation(e.getX(), e.getY());
            			tree.setSelectionPath(path);
            			GSComponentNode node = (GSComponentNode) tree.getLastSelectedPathComponent();
            			if(node!=null) {
            				//generate the menu
            	            menu.show(tree, e.getX(), e.getY());    
            			}
            		}
            		category.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            School temp=school.get();
                            temp.getTotalTemplates().exportComponent(course.getRoot());
                            temp.save();
                        }
                    });
            		//add the function of add
            		add.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							GSComponentNode node = (GSComponentNode) tree.getLastSelectedPathComponent();
							AddComponent addComponent = new AddComponent(node, (Component)node.getUserObject(), MainWindow.this);
							addComponent.setVisible(true);
						}
            			
            		});
            		delete.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							//delete from the component
							GSComponentNode node = (GSComponentNode) tree.getLastSelectedPathComponent();
							Component toDelete = (Component)node.getUserObject();
							GSComponentNode parentNode = (GSComponentNode)node.getParent();
							Component parentComponent = (Component)parentNode.getUserObject();
							parentComponent.deleteComponent(toDelete);
							//delete from the tree
							DefaultTreeModel tm = (DefaultTreeModel)tree.getModel();
							tm.removeNodeFromParent(node);
							refreshTree(parentNode);
                            modified = true;
//							tree.setSelectionPath(new TreePath(parentNode));
//							tree.updateUI();
						}
            			
            		});
            		rename.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            GSComponentNode node = (GSComponentNode) tree.getLastSelectedPathComponent();
                            RenameWindow renameWindow = new RenameWindow(self, node);
                            setEnabled(false);
                            renameWindow.setVisible(true);
                        }
                    });
            		changeScale.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// change scale of the selected component
							GSComponentNode node = (GSComponentNode) tree.getLastSelectedPathComponent();
							Component toChange = (Component)node.getUserObject();
							ChangeComponentScale ccs = new ChangeComponentScale(node, toChange, MainWindow.this);
							setEnabled(false);
							ccs.setVisible(true);
						}
            		});
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

            
            tablePanel.addMouseListener(new MouseAdapter() {
            	public void mouseClicked(MouseEvent e) {
            		if(e.getButton()==MouseEvent.BUTTON3) {
            			
            			JPopupMenu menu = new JPopupMenu();
            			JMenuItem addRow,addCol;
            			menu.add(addRow = new JMenuItem("Add Student"));
            			
            			menu.show(tablePanel, e.getX(), e.getY());
            			
            			addRow.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								AddStudent as = new AddStudent(course, MainWindow.this);
								as.setVisible(true);
							}
            				
            			});
            		}
            	}
            });
            
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
                saveButton = new JButton("Save");
                saveButton.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {

                        school.save();
                        refreshTableNStatistic();
                    }
                });
                cancelButton = new JButton("Cancel");
                saveButton.setPreferredSize(cancelButton.getPreferredSize());

                buttonC.gridx = 0;
                buttonC.gridy = 0;
                buttonGridBag.addLayoutComponent(saveButton, buttonC);
                buttonC.gridx = 1;
                buttonC.gridy = 0;
                buttonGridBag.addLayoutComponent(cancelButton, buttonC);
                buttonPanel.add(saveButton);
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

    public void setModified()
    {
        modified = true;
    }

    public School getSchool()
    {
        return school;
    }

    public EntryWindow getFather()
    {
        return father;
    }

    public void refreshTree(GSComponentNode node)
    {
        tree.setSelectionPath(new TreePath(node));
        tree.updateUI();
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

        GSTable table = tableContent.createTable(headerString);
        table.setParaOfTableMode(this, course, node);
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

        addMenuOnTable(table);

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
                    tableContent.append(finalScore + " (" + scoreToLetter(finalScore) + ")");
                }
                tableContent.println();
            }
        }

        return tableContent;
    }

    private String scoreToLetter(double finalScore) {
		String ret = "";
    	if(finalScore>=90) {
			ret = "A";
		}
    	else if(finalScore>=80) {
			ret = "B";
		}
    	else if(finalScore>=70) {
			ret = "C";
		}
    	else if(finalScore>=60) {
			ret = "D";
		}
    	else {
    		ret = "F";
    	}

    	if(finalScore == 100) {
    		
    	}
    	else if(finalScore%10>=7 && !ret.equals("A") && !ret.equals("F")) {
    		ret = ret + "+";
    	}
    	else if(finalScore%10<=2 && !ret.equals("F")){
    		ret = ret + "-";
    	}
		return ret;
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

    private void closeFrame()
    {
        father.refreshDropDowns();
        father.setVisible(true);
        dispose();

    }

    //public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
    //    MainWindow test = new MainWindow(initCourseForTest());
    //    test.setVisible(true);
    //}
    public void addMenuOnTable(GSTable table) {
    	table.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
        		if(e.getButton()==MouseEvent.BUTTON3) {
        			
        			JPopupMenu menu = new JPopupMenu();
        			JMenuItem addRow,addCol,comment;
        			menu.add(addRow = new JMenuItem("Add Student"));
        			menu.add(addCol = new JMenuItem("Drop Student"));
        			menu.add(comment = new JMenuItem("Modify Comment"));
        			menu.show(table, e.getX(), e.getY());
        			
        			addRow.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							AddStudent as = new AddStudent(course, MainWindow.this);
							setEnabled(false);
							as.setVisible(true);
						}
        			});
        			
        			addCol.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							int row = table.getSelectedRow();
							String name = (String)table.getValueAt(row, 1);
							//String sectionName = (String)table.getValueAt(row, 0);
							
							//Section section = course.findSectionByStr(sectionName);
                            Section section = table.getSection(row);
							section.removeStudent(section.getStudentByFullName(name));
							MainWindow.this.refreshTableNStatistic();
						}
        			});
        			comment.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							int row = table.getSelectedRow();
							int column = table.getSelectedColumn();
							String name = (String)table.getValueAt(row, 1);
							Component c = table.getLogicComponent(column);
							Section section = table.getSection(row);
							Student s = section.getStudentByFullName(name);
							ViewComment viewComment = new ViewComment(c,s,course);
							viewComment.setVisible(true);
						}
        			});
        		}
        	}
        });
    }
}

class GSTree extends JTree
{
	//selection menu
	JMenuItem add = null, delete = null, save = null; 

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
        //setOpenIcon(null);
    }

    @Override
    public java.awt.Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
    {
        JComponent c = (JComponent)super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        if (sel)
        {
            c.setOpaque(false);
            c.setForeground(getTextSelectionColor());
        }
        else
            {
                c.setOpaque(true);
            if (!((GSComponentNode) value).isLeaf() && !((GSComponentNode) value).checkChildrenSumIs100())
            {
                c.setForeground(getTextNonSelectionColor());
                c.setBackground(Color.YELLOW);
            } else
                {
                c.setForeground(getTextNonSelectionColor());
                c.setBackground(getBackgroundNonSelectionColor());
            }
        }
        return c;
    }
    
    
}



