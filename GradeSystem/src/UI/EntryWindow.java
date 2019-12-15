package UI;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Logic.*;
import Logic.Component;

public class EntryWindow extends GSFrame
{
    private EntryWindow self = this;

    private JPanel vePanel = new JPanel();
    private GSButton buttonSelect;
    private GSButton buttonAdd;
    private GSCourseDropdown dropdown;
    private JLabel titleLabel = new JLabel("Course Name");
    private GSTextField titleField;
    private JLabel templateLabel = new JLabel("Template");
    private GSComponentDropdown templates;
    private School school;
    
    public EntryWindow(School s) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException
    {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        school = s;
        
        //buttonSelect
        {
            buttonSelect = new GSButton("View/Edit Course");
        }

        //buttonAdd
        {
            buttonAdd = new GSButton("Add New Course");
        }

        //dropdowns
        {
        	Course[] drowdownArray = school.getCourseArray();
        	dropdown = new GSCourseDropdown(drowdownArray);
        	
        	Component[] templateArray = school.getTemplateArray();
        	templates = new GSComponentDropdown(templateArray);
        }

        //textFields
        {
        	titleField = new GSTextField();
        }
        
        initComponent();
    }
    
    private void initComponent()
    {
        GridBagLayout globalGridBag = new GridBagLayout();
        GridBagConstraints globalC = new GridBagConstraints();
        JPanel panel = new JPanel(globalGridBag);
        globalC.insets = new Insets(5, 15, 5, 15);

        GridBagLayout veGridBag = new GridBagLayout();
        GridBagConstraints veC = new GridBagConstraints();
        JPanel vePanel = new JPanel(veGridBag);
        vePanel.setBorder(BorderFactory.createTitledBorder("View/Edit"));
        // View and edit course
        {
            veC.insets = new Insets(5, 5, 10, 10);
            veC.gridx = 0;
            veC.gridy = 0;
            veC.gridwidth = 1;
            veC.gridheight = 1;
            veGridBag.addLayoutComponent(dropdown, veC);
            vePanel.add(dropdown);

            veC.gridx = 0;
            veC.gridy = 1;
            veGridBag.addLayoutComponent(buttonSelect, veC);
            vePanel.add(buttonSelect);
            buttonSelect.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    MainWindow toOpen = null;
                    try {
                        toOpen = new MainWindow((Course) dropdown.getSelectedItem(), school, self);
                    } catch (ClassNotFoundException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (InstantiationException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (IllegalAccessException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (UnsupportedLookAndFeelException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                    setVisible(false);
                    toOpen.setVisible(true);
                }

            });
        }
        globalC.gridx = 0;
        globalC.gridy = 0;
        panel.add(vePanel);

        GridBagLayout addGridBag = new GridBagLayout();
        GridBagConstraints addC = new GridBagConstraints();
        JPanel addPanel = new JPanel(addGridBag);
        addPanel.setBorder(BorderFactory.createTitledBorder("Add"));
        // Add course
        {
            addC.insets = new Insets(5, 5, 10, 10);

            addC.gridx = 0;
            addC.gridy = 0;
            addGridBag.addLayoutComponent(titleLabel, addC);
            addPanel.add(titleLabel);

            addC.gridx = 1;
            addC.gridy = 0;
            addGridBag.addLayoutComponent(titleField, addC);
            addPanel.add(titleField);

            addC.gridx = 0;
            addC.gridy = 1;
            addC.gridwidth = GridBagConstraints.RELATIVE;
            addGridBag.addLayoutComponent(templateLabel, addC);
            addPanel.add(templateLabel);

            addC.gridx = 1;
            addC.gridy = 1;
            addC.gridwidth = GridBagConstraints.REMAINDER;
            addGridBag.addLayoutComponent(templates, addC);
            addPanel.add(templates);

            addC.gridx = 0;
            addC.gridy = 2;
            addGridBag.addLayoutComponent(buttonAdd, addC);
            addPanel.add(buttonAdd);
            buttonAdd.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    MainWindow toOpen = null;
                    try {
//			    school = school.get();
                        Course newCourse = new Course(titleField.getText(), (Component) templates.getSelectedItem());
                        school.addCourse(newCourse);
                        toOpen = new MainWindow(newCourse, school, self);
                    } catch (ClassNotFoundException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (InstantiationException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (IllegalAccessException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (UnsupportedLookAndFeelException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                    toOpen.setVisible(true);
                    setVisible(false);
                }

            });
        }
        globalC.gridx = 1;
        globalC.gridy = 0;
        panel.add(addPanel);

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
    }

    private static School initSchoolForTest() {
    	School ret = new School();
    	ret=ret.get();
    	if(ret==null)
    	    ret=new School();
    	Course c1 = initCourse1ForTest();
    	Course c2 = initCourse2ForTest();
    	ret.addCourse(c1);
    	ret.addCourse(c2);
    	ret.getTotalTemplates().exportComponent(c1.getRoot());
    	return ret;
    }
    
    private static Course initCourse1ForTest()
    {
        Component root = new Component("CS591");
        Component participation = new Component("Dancing");
        root.addComponentAndScale(participation, 10.0);
        Component homework = new Component("Homework");
        root.addComponentAndScale(homework, 50);
            Component hw1 = new Component("Hw1");
            homework.addComponentAndScale(hw1, 40);
                Component classDesign = new Component("Class Design");
                hw1.addComponentAndScale(classDesign, 40);
            Component hw2 = new Component("Hw2");
            homework.addComponentAndScale(hw2, 30);
            Component hw3 = new Component("Hw3");
            homework.addComponentAndScale(hw3, 30);
        Component exam = new Component("Exam");
        root.addComponentAndScale(exam, 40);
        
        Course course1 = new Course("CS591", root);
        return course1;
    }
    
    private static Course initCourse2ForTest()
    {
        Component root = new Component("CS112");
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
        
        Course course2 = new Course("CS112", root);
        course2.addStudent(new Student("Yf","F"), course2.defaultSection());
        course2.addStudent(new Student("J", "S"), course2.defaultSection());
        course2.addSection();
        Section section = course2.getSections().get(1);
        course2.addStudent(new Student("Rq", "Y"), section);
        course2.addStudent(new Student("T", "C"), section);
        
        return course2;
    }

    public void refreshDropDowns()
    {
        Course[] dropDownArray = school.getCourseArray();
        dropdown.setModel(new DefaultComboBoxModel<Course>(dropDownArray));
        dropdown.revalidate();
        dropdown.updateUI();

        Component[] templateArray = school.getTemplateArray();
        templates.setModel(new DefaultComboBoxModel<>(templateArray));
        templates.revalidate();
        templates.updateUI();
        repaint();
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
    	School s = new School().get();
    	if(s==null)
    	{
            s = new School();
        }
        EntryWindow test = new EntryWindow(s);
        test.setVisible(true);
    }
    
    class GSButton extends JButton
    {
		public GSButton(String string)
        {
            super(string);
        }

    }
    
    class GSTextField extends JTextField
    {
		public GSTextField()
        {
            super();
            this.setColumns(10);
        }

    }
    
    class GSCourseDropdown extends JComboBox<Course>
    {
		public GSCourseDropdown(Course[]  course)
        {
            super(course);
            
        }
    }
    
    class GSComponentDropdown extends JComboBox<Component>
    {
		public GSComponentDropdown(Component[] components)
        {
            super(components);
        }
    }

}


