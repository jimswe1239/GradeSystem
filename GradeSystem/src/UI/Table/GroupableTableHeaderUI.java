package UI.Table;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.*;
import java.awt.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class GroupableTableHeaderUI extends BasicTableHeaderUI
{
    private int m_height;

    private Dimension createHeaderSize(long width)
    {
        TableColumnModel columnModel = header.getColumnModel();
        width += columnModel.getColumnMargin() * columnModel.getColumnCount();
        if (width > Integer.MAX_VALUE)
        {
            width = Integer.MAX_VALUE;
        }
        return new Dimension((int) width, getHeaderHeight());
    }

    private int getColCountUnderColGroup(ColumnGroup cg, int iCount)
    {
        Vector v = cg.vector;
        for (int i = 0; i < v.size(); i++)
        {
            Object obj = v.elementAt(i);
            if (obj instanceof ColumnGroup)
            {
                iCount = getColCountUnderColGroup((ColumnGroup) obj, iCount);
            } else
                {
                iCount++;
            }
        }
        return iCount;
    }

    public int getHeaderHeight()
    {
        int height = 0;
        TableColumnModel columnModel = header.getColumnModel();
        for (int column = 0; column < columnModel.getColumnCount(); column++)
        {
            TableColumn aColumn = columnModel.getColumn(column);
            TableCellRenderer renderer = aColumn.getHeaderRenderer();
            if (renderer == null)
            {
                renderer = new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table, Object value, boolean isSelected,
                            boolean hasFocus, int row, int column) {
                        JTableHeader header = table.getTableHeader();
                        if (header != null)
                        {
                            setForeground(header.getForeground());
                            setBackground(header.getBackground());
                            setFont(header.getFont());
                        }
                        setHorizontalAlignment(JLabel.CENTER);
                        setText((value == null) ? "" : value.toString());
                        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
                        return this;
                    }
                };
            }
            Component comp = renderer.getTableCellRendererComponent(header
                            .getTable(), aColumn.getHeaderValue(), false, false, -1,
                    column);
            int cHeight = comp.getPreferredSize().height;
            Enumeration enumeration = ((GroupableTableHeader) header)
                    .getColumnGroups(aColumn);
            if (enumeration != null)
            {
                while (enumeration.hasMoreElements())
                {
                    ColumnGroup cGroup = (ColumnGroup) enumeration
                            .nextElement();
                    cHeight += cGroup.getSize(header.getTable()).height;
                }
            }
            height = Math.max(height, cHeight);
        }
        height = Math.max(height, m_height);
        return height;
    }

    @Override
    public Dimension getPreferredSize(JComponent c)
    {
        long width = 0;
        Enumeration enumeration = header.getColumnModel().getColumns();
        while (enumeration.hasMoreElements())
        {
            TableColumn aColumn = (TableColumn) enumeration.nextElement();
            width = width + aColumn.getWidth();
        }

        return createHeaderSize(width);
    }

    @Override
    public void paint(Graphics g, JComponent c)
    {
        Rectangle clipBounds = g.getClipBounds();
        if (header.getColumnModel() == null)
        {
            return;
        }
        ((GroupableTableHeader) header).setColumnMargin();
        int column = 0;
        Dimension size = header.getSize();
        Rectangle cellRect = new Rectangle(0, 0, size.width, size.height);
        Hashtable h = new Hashtable();
        int columnMargin = header.getColumnModel().getColumnMargin();
        Enumeration enumeration = header.getColumnModel().getColumns();
        while (enumeration.hasMoreElements())
        {
            cellRect.height = size.height;
            cellRect.y = 0;
            TableColumn aColumn = (TableColumn) enumeration.nextElement();
            Enumeration cGroups = ((GroupableTableHeader) header)
                    .getColumnGroups(aColumn);
            if (cGroups != null)
            {
                int groupHeight = 0;
                while (cGroups.hasMoreElements())
                {
                    ColumnGroup cGroup = (ColumnGroup) cGroups.nextElement();
                    Rectangle groupRect = (Rectangle) h.get(cGroup);
                    if (groupRect == null)
                    {
                        groupRect = new Rectangle(cellRect);
                        Dimension d = cGroup.getSize(header.getTable());
                        if (!System.getProperty("java.vm.version").startsWith(
                                "1.2"))
                        {
                            int iColCount = getColCountUnderColGroup(cGroup, 0);
                            groupRect.width = d.width - iColCount
                                    * columnMargin;
                        }
                        else
                            {
                            groupRect.width = d.width;
                        }
                        groupRect.height = d.height;
                        h.put(cGroup, groupRect);
                    }
                    paintCell(g, groupRect, cGroup);
                    groupHeight += groupRect.height;
                    cellRect.height = size.height - groupHeight;
                    cellRect.y = groupHeight;
                }
            }
            if (!System.getProperty("java.vm.version").startsWith("1.2"))
            {
                cellRect.width = aColumn.getWidth();
            }
            else
                {
                cellRect.width = aColumn.getWidth() + columnMargin;
            }

            if (cellRect.intersects(clipBounds))
            {
                paintCell(g, cellRect, column);
            }
            cellRect.x += cellRect.width;
            column++;
        }
    }

    private void paintCell(Graphics g, Rectangle cellRect, int columnIndex)
    {
        TableColumn aColumn = header.getColumnModel().getColumn(columnIndex);
        TableCellRenderer renderer = aColumn.getHeaderRenderer();
        //
        if (renderer == null)
        {
            renderer = new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table,
                                                               Object value, boolean isSelected, boolean hasFocus,
                                                               int row, int column) {
                    JTableHeader header = table.getTableHeader();
                    if (header != null)
                    {
                        setForeground(header.getForeground());
                        setBackground(header.getBackground());
                        setFont(header.getFont());
                    }
                    setHorizontalAlignment(JLabel.CENTER);
                    setText((value == null) ? "" : value.toString());
                    setBorder(UIManager.getBorder("TableHeader.cellBorder"));
                    return this;
                }
            };
        }

        String headerValue = aColumn.getHeaderValue().toString();
        Component component = renderer.getTableCellRendererComponent(header
                .getTable(), headerValue, false, false, -1, columnIndex);

        rendererPane.add(component);
        rendererPane.paintComponent(g, component, header, cellRect.x,
                cellRect.y, cellRect.width, cellRect.height, true);
    }

    private void paintCell(Graphics g, Rectangle cellRect, ColumnGroup cGroup)
    {
        TableCellRenderer renderer = cGroup.getHeaderRenderer();
        if (renderer == null)
        {
            renderer = new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table,
                                                               Object value, boolean isSelected, boolean hasFocus,
                                                               int row, int column) {
                    JTableHeader header = table.getTableHeader();
                    if (header != null)
                    {
                        setForeground(header.getForeground());
                        setBackground(header.getBackground());
                        setFont(header.getFont());
                    }
                    setHorizontalAlignment(JLabel.CENTER);
                    setText((value == null) ? "" : value.toString());
                    setBorder(UIManager.getBorder("TableHeader.cellBorder"));
                    return this;
                }
            };
        }

        String headerValue = cGroup.getHeaderValue().toString();
        Component component = renderer.getTableCellRendererComponent(header
                .getTable(), headerValue, false, false, -1, -1);

        rendererPane.add(component);
        rendererPane.paintComponent(g, component, header, cellRect.x,
                cellRect.y, cellRect.width, cellRect.height, true);
    }

    public void setHeaderHeight(int iHeight) {
        m_height = iHeight;
    }
}
