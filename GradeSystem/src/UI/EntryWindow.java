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
    private GSButton buttonSelect;
    private GSButton buttonAdd;
    private GSCourseDropdown dropdown;
    private GSTextField titleField;
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
        globalC.gridx = 0;
        globalC.gridy = 0;
        globalC.gridwidth = 1;
        globalC.gridheight = 3;
        globalGridBag.addLayoutComponent(dropdown, globalC);
        
        globalC.gridx = 3;
        globalC.gridy = 3;
        globalC.gridwidth = 1;
        globalC.gridheight = 3;
        globalGridBag.addLayoutComponent(templates, globalC);
        
        globalC.gridx = 1;
        globalC.gridy = 0;
        globalC.gridwidth = 1;
        globalC.gridheight = 3;
        globalGridBag.addLayoutComponent(buttonSelect, globalC);

        globalC.gridx = 5;
        globalC.gridy = 3;
        globalC.gridwidth = 1;
        globalC.gridheight = 3;
        globalGridBag.addLayoutComponent(titleField, globalC);
        
        buttonSelect.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
        	MainWindow toOpen = null;
			try {
				toOpen = new MainWindow((Course)dropdown.getSelectedItem(), school);
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
          }

        });
        
        globalC.gridx = 1;
        globalC.gridy = 1;
        globalC.gridwidth = 1;
        globalC.gridheight = 3;
        //globalGridBag.addLayoutComponent(buttonAdd, globalC);
        buttonAdd.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
        	MainWindow toOpen = null;
			try {
//			    school = school.get();
				Component exportedTemplate = school.getTotalTemplates().importComponent(((Component)templates.getSelectedItem()));
				Course newCourse = new Course(titleField.getText(), exportedTemplate);
				school.addCourse(newCourse);
				toOpen = new MainWindow(newCourse, school);
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
          }

        });
        
        
        panel.add(buttonSelect);
        panel.add(buttonAdd);
        panel.add(dropdown);
        panel.add(titleField);
        panel.add(templates);
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

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
    	School s = new School().get();
    	if(s==null) {
    	    s=new School();
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


