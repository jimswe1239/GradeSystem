package UI.Table;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;

public class GSTable extends JTable
{
    private GridSplit gridSplit;

    public GSTable(GridSplit gridSplit, TableModel tbl)
    {
        super(tbl);
        this.gridSplit = gridSplit;
        setUI(new DSTableUI());
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
        // 当指定位置不在Table内时，返回－1
        if (y < 0)
            return y;
        int x = super.rowAtPoint(p);
        // 获取指定位置可视单元格的列值
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
    public boolean isCellEditable(int row, int col) {
        if(col==0)
        {
            return false;
        }
        else
        {
            return true;
        }
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

        int colCell = gridSplit.visibleColCell(row, column);// 指定单元格的可视单元格列值
        int rowCell = gridSplit.visibleRowCell(row, column);// 指定单元格的可视单元格行值

        Rectangle rec = super.getCellRect(rowCell, colCell, includeSpacing);

        // 如果指定单元格列宽不为1，累计出跨列单元格的宽度
        for (int i = 1; i < gridSplit.spanCol(rowCell, colCell); i++) {
            rec.width += getColumnModel().getColumn(colCell + i).getWidth();
        }
        // 如果指定单元格行宽不为1，累计出跨行单元格的宽度
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
            if (modelColumn == 0)                   //此处加入条件判断
                comp.setBackground(new Color(238,238,238));
            else                                                     //不符合条件的保持原表格样式
                comp.setBackground(getBackground());
        }
        return comp;
    }
}