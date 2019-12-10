package UI.Table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class TableDemo extends JFrame{

    String[] header;
    String[][] content;

    public TableDemo() {
        super("test");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //JTable table = initTable();

        JTable table = initCS591();

        JScrollPane panel = new JScrollPane(table);
        getContentPane().add(panel, BorderLayout.CENTER);
    }

    private JTable initTable()
    {
        header = new String[] {"name", "age", "country", "home", "telephone"};
        content = new String[][]{{"name1","18","China", "shangHai", "111"}, {"name1","18","China", "shangHai", "111"}};

        DefaultTableModel dm = new DefaultTableModel(content, header);

        JTable table = new JTable(dm);

        TableColumnModel cm = table.getColumnModel();
        ColumnGroup g_name = new ColumnGroup("Source");
        ColumnGroup g_person = new ColumnGroup("Person");
        g_name.add(g_person);
        g_person.add(cm.getColumn(0));
        g_person.add(cm.getColumn(1));
        g_name.add(cm.getColumn(2));
        ColumnGroup g_name2 = new ColumnGroup("Target");
        g_name2.add(cm.getColumn(3));
        g_name2.add(cm.getColumn(4));

        GroupableTableHeader header = (GroupableTableHeader) table.getTableHeader();
        header.addColumnGroup(g_name);
        header.addColumnGroup(g_name2);
        table.getTableHeader().setUI(new GroupableTableHeaderUI());

        return table;
    }

    private JTable initCS591()
    {
        header = new String[] {"section", "student", "participation", "class design", "correct", "comment", "hw2", "hw3", "exam1", "exam2", "total"};
//        content = new String[][]{{"A1"}};
        content = new String[][]{{"section", "student", "participation", "class design", "correct", "comment", "hw2", "hw3", "exam1", "exam2", "total"}};

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

        GSTable table = tableContent.createTable(header);

        TableColumnModel cm = table.getColumnModel();
        ColumnGroup no = new ColumnGroup(" ");
        no.add(cm.getColumn(0));
        no.add(cm.getColumn(1));
        ColumnGroup cs591 = new ColumnGroup("CS591");
        cs591.add(cm.getColumn(2));
        ColumnGroup homework = new ColumnGroup("Homework");
        ColumnGroup hw1 = new ColumnGroup("Hw1");
        hw1.add(cm.getColumn(3));
        hw1.add(cm.getColumn(4));
        hw1.add(cm.getColumn(5));
        homework.add(hw1);
        homework.add(cm.getColumn(6));
        homework.add(cm.getColumn(7));
        cs591.add(homework);
        ColumnGroup exam = new ColumnGroup("Exam");
        exam.add(cm.getColumn(8));
        exam.add(cm.getColumn(9));
        cs591.add(exam);
        cs591.add(cm.getColumn(10));

        GroupableTableHeader header = (GroupableTableHeader) table.getTableHeader();
        header.addColumnGroup(no);
        header.addColumnGroup(cs591);
        table.getTableHeader().setUI(new GroupableTableHeaderUI());

        return table;
    }

    public static void main(String[] args) {
        TableDemo demo = new TableDemo();
        demo.setVisible(true);
    }
}