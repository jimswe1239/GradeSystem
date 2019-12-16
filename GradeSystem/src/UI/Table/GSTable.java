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
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.xml.ws.handler.LogicalHandler;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Enumeration;

public class GSTable extends JTable
{
    private GridSplit gridSplit;
    private Course course;
    private GSComponentNode node;
    private MainWindow mainWindow;

    public GSTable(GridSplit gridSplit, TableModel tbl)
    {
        super(tbl);
        this.gridSplit = gridSplit;
        setUI(new DSTableUI());
    }

    public void setParaOfTableMode(MainWindow mainWindow, Course course, GSComponentNode node)
    {
        GSTableModel tableModel = (GSTableModel) getModel();

        tableModel.setMainWindow(mainWindow);
        tableModel.setCourse(course);
        tableModel.setNode(node);
    }

    public void setMainWindow(MainWindow mainWindow)
    {
        this.mainWindow = mainWindow;
    }

    public void setCourse(Course course)
    {
        this.course = course;
    }

    public void setNode(GSComponentNode node)
    {
        this.node = node;
    }

    public GridSplit getGridSplit() {
        return gridSplit;
    }

    @Override
    public int columnAtPoint(Point p) {
        int y = super.columnAtPoint(p);
        if(gridSplit==null){
            return y;
        }
        // 褰撴寚瀹氫綅缃笉鍦═able鍐呮椂锛岃繑鍥烇紞1
        if (y < 0)
            return y;
        int x = super.rowAtPoint(p);
        // 鑾峰彇鎸囧畾浣嶇疆鍙鍗曞厓鏍肩殑鍒楀��
        return gridSplit.visibleColCell(x, y);

    }

    @Override
    public int rowAtPoint(Point p) {
        int x = super.rowAtPoint(p);
        if (gridSplit == null) {
            return x;
        }
        if (x < 0)
            return x;
        int y = super.columnAtPoint(p);
        return gridSplit.visibleRowCell(x, y);
    }

    @Override
    public boolean isCellEditable(int row, int col)
    {
        if(col < 2 || col == getColumnCount() - 1)
        {
            return false;
        }
        return true;
    }

//    private void event()
//    {
//        int row = getEditingRow();
//        int column = getEditingColumn();
//
//        DefaultCellEditor obj = (DefaultCellEditor) (getColumnModel().getColumn(column).getCellEditor());
//        if (obj != null)
//        {
//            JComponent com = (JComponent) obj.getComponent();
//            double value = 0;
//            if (com instanceof JTextField)
//            {
//                String text = ((JTextField) com).getText();
//                if(!text.equals(""))
//                {
//                    value = Double.valueOf(text);
//                }
//                else
//                {
//                    value = 0;
//                }
//            }
//            System.out.println("row:" + row + ", column:" + column + ", value:" + value);
//            Student student = getStudent(row);
//            System.out.println(student);
//            Logic.Component component = getComponent(column, node);
//            System.out.println(component.getName());
//            course.getGradeMap().putScore(student, new Score(value), component);
//            mainWindow.refreshTableNStatistic();
//        }
//    }

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

    public Section getSection(int row)
    {
        for(Section section : course.getSections())
        {
            int stuNum = section.getStudentList().size();
            if(row < stuNum)
            {
                return section;
            }
            else
            {
                row = row - stuNum;
            }
        }

        return null;
    }

    public Logic.Component getLogicComponent(int column)
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

    @Override
    public boolean isCellSelected(int row, int col)
    {
        if(col==0)
        {
            return false;
        }
        return getSelectedColumn()==col&&getSelectedRow()==row;
    }

    @Override
    public Rectangle getCellRect(int row, int column, boolean includeSpacing) {
        if (gridSplit == null) {
            return super.getCellRect(row, column, includeSpacing);
        }

        int colCell = gridSplit.visibleColCell(row, column);// 鎸囧畾鍗曞厓鏍肩殑鍙鍗曞厓鏍煎垪鍊�
        int rowCell = gridSplit.visibleRowCell(row, column);// 鎸囧畾鍗曞厓鏍肩殑鍙鍗曞厓鏍艰鍊�

        Rectangle rec = super.getCellRect(rowCell, colCell, includeSpacing);

        // 濡傛灉鎸囧畾鍗曞厓鏍煎垪瀹戒笉涓�1锛岀疮璁″嚭璺ㄥ垪鍗曞厓鏍肩殑瀹藉害
        for (int i = 1; i < gridSplit.spanCol(rowCell, colCell); i++) {
            rec.width += getColumnModel().getColumn(colCell + i).getWidth();
        }
        // 濡傛灉鎸囧畾鍗曞厓鏍艰瀹戒笉涓�1锛岀疮璁″嚭璺ㄨ鍗曞厓鏍肩殑瀹藉害
        for (int i = 1; i < gridSplit.spanRow(rowCell, colCell); i++) {
            rec.height += getRowHeight(rowCell + i);
        }
        return rec;
    }

    @Override
    protected JTableHeader createDefaultTableHeader()
    {
        return new GroupableTableHeader(columnModel);
    }

    @Override
    public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column)
    {
        int modelRow = convertRowIndexToModel(row);
        int modelColumn = convertColumnIndexToModel(column);
        Component comp = super.prepareRenderer(renderer, row, column);
        if (!isRowSelected(modelRow)) {
            if (modelColumn == 0)                   //姝ゅ鍔犲叆鏉′欢鍒ゆ柇
                comp.setBackground(new Color(238,238,238));
            else                                                     //涓嶇鍚堟潯浠剁殑淇濇寔鍘熻〃鏍兼牱寮�
                comp.setBackground(getBackground());
        }
        return comp;
    }
}
