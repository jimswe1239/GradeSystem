package UI.Table;

import Logic.Course;
import Logic.Score;
import Logic.Section;
import Logic.Student;
import UI.GSComponentNode;
import UI.MainWindow;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Enumeration;

public class GSTableModel extends DefaultTableModel
{
    private Course course;
    private GSComponentNode node;
    private MainWindow mainWindow;

    public GSTableModel(Object[][] data, Object[] columnNames)
    {
        super(data, columnNames);
        addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e)
            {
                int row = e.getFirstRow();
                int column = e.getColumn();
                double value = Double.valueOf((String) getValueAt(row, column));
                System.out.println("row: " + row);
                System.out.println("column: " + column);
                System.out.println("value: " + getValueAt(row, column));

                Student student = getStudent(row);
                System.out.println(student);
                Logic.Component component = getComponent(column, node);
                System.out.println(component.getName());
                course.getGradeMap().putScore(student, new Score(value), component);
                mainWindow.refreshTableNStatistic();
                mainWindow.setModified();
            }
        });
    }

    public void setCourse(Course course)
    {
        this.course = course;
    }

    public void setMainWindow(MainWindow mainWindow)
    {
        this.mainWindow = mainWindow;
    }

    public void setNode(GSComponentNode node)
    {
        this.node = node;
    }

    public Student getStudent(int row)
    {
        Student student = null;
        for(Section section : course.getSections())
        {
            int studentNum = section.getStudentList().size();
            if(studentNum < (row+1))
            {
                row -= studentNum;
                continue;
            }
            else
            {
                student = section.getStudentList().get(row);
                return student;
            }
        }

        return student;
    }

    public Logic.Component getComponent(int column, GSComponentNode node)
    {
        column -= 2;
        Logic.Component component = null;

        Enumeration children = node.depthFirstEnumeration();

        while(children.hasMoreElements())
        {
            GSComponentNode next = (GSComponentNode) children.nextElement();
            if(next.isLeaf())
            {
                if(column != 0)
                {
                    column--;
                }
                else
                {
                    component = (Logic.Component) next.getUserObject();
                    return component;
                }
            }
        }

        return component;
    }
}
