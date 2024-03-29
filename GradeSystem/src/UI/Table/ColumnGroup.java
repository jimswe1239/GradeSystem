package UI.Table;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.Enumeration;
import java.util.Vector;

public class ColumnGroup
{
    /**Render of the merged header*/
    protected TableCellRenderer renderer=null;
    /**The actual minimum header of merged header*/
    protected Vector<Object> vector=null;
    /**Text of merged header cell*/
    protected String text=null;
    private int margin = 0;

    public ColumnGroup(String text) {
        this(null, text);
    }

    public ColumnGroup(TableCellRenderer renderer, String text)
    {
        if (renderer == null)
        {
            this.renderer = new DefaultTableCellRenderer() {
                private static final long serialVersionUID = 1L;

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
                    this.setText((value == null) ? "" : value.toString());
                    setBorder(UIManager.getBorder("TableHeader.cellBorder"));
                    return this;
                }
            };
        }
        else
        {
            this.renderer = renderer;
        }
        this.text = text;
        vector = new Vector<Object>();
    }
    /**
     * add header cell
     * @param obj
     */
    public void add(Object obj)
    {
        if (obj == null)
            return;
        vector.addElement(obj);
    }
    /**
     * get the column covered by merger header
     * @param column
     * @param group
     * @return
     */
    public Vector<ColumnGroup> getColumnGroups(TableColumn column, Vector<ColumnGroup> group)
    {
        group.addElement(this);
        if (vector.contains(column))
            return group;
        Enumeration<Object> enumeration = vector.elements();
        while (enumeration.hasMoreElements())
        {
            Object obj = enumeration.nextElement();
            if (obj instanceof ColumnGroup)
            {
                Vector<ColumnGroup> groups = ((ColumnGroup) obj).getColumnGroups(column,
                        (Vector<ColumnGroup>) group.clone());
                if (groups != null)
                {
                    return groups;
                }
            }
        }
        return null;
    }

    public TableCellRenderer getHeaderRenderer() {
        return renderer;
    }

    public Object getHeaderValue() {
        return text;
    }
    /**
     * get number of merged header cell
     * @return
     */
    public int getSize() {
        return vector == null ? 0 : vector.size();
    }
    /**
     * get size of merger header cel;
     * @return
     */
    public Dimension getSize(JTable table)
    {
        Component comp = renderer.getTableCellRendererComponent(table,
                getHeaderValue(), false, false, -1, -1);
        int height = comp.getPreferredSize().height;
        int width = 0;
        Enumeration<Object> enumeration = vector.elements();
        while (enumeration.hasMoreElements())
        {
            Object obj = enumeration.nextElement();
            if (obj instanceof TableColumn)
            {
                TableColumn aColumn = (TableColumn) obj;
                width += aColumn.getWidth();
                width += margin;
            } else
                {
                width += ((ColumnGroup) obj).getSize(table).width;
            }
        }
        return new Dimension(width, height);
    }

    public String getText() {
        return text;
    }

    public boolean removeColumn(ColumnGroup ptg, TableColumn tc)
    {
        boolean retFlag = false;
        if (tc != null)
        {
            for (int i = 0; i < ptg.vector.size(); i++)
            {
                Object tmpObj = ptg.vector.get(i);
                if (tmpObj instanceof ColumnGroup)
                {
                    retFlag = removeColumn((ColumnGroup) tmpObj, tc);
                    if (retFlag)
                    {
                        break;
                    }
                } else if (tmpObj instanceof TableColumn)
                {
                    if (tmpObj == tc)
                    {
                        ptg.vector.remove(i);
                        retFlag = true;
                        break;
                    }
                }
            }
        }
        return retFlag;
    }

    public boolean removeColumnGrp(ColumnGroup ptg, ColumnGroup tg)
    {
        boolean retFlag = false;
        if (tg != null)
        {
            for (int i = 0; i < ptg.vector.size(); i++)
            {
                Object tmpObj = ptg.vector.get(i);
                if (tmpObj instanceof ColumnGroup)
                {
                    if (tmpObj == tg)
                    {
                        ptg.vector.remove(i);
                        retFlag = true;
                        break;
                    }
                    else
                        {
                        retFlag = removeColumnGrp((ColumnGroup) tmpObj, tg);
                        if (retFlag)
                        {
                            break;
                        }

                    }
                } else if (tmpObj instanceof TableColumn)
                {
                    break;
                }
            }
        }
        return retFlag;
    }

    public void setColumnMargin(int margin)
    {
        this.margin = margin;
        Enumeration<Object> enumeration = vector.elements();
        while (enumeration.hasMoreElements())
        {
            Object obj = enumeration.nextElement();
            if (obj instanceof ColumnGroup)
            {
                ((ColumnGroup) obj).setColumnMargin(margin);
            }
        }
    }

    public void setHeaderRenderer(TableCellRenderer renderer)
    {
        if (renderer != null)
        {
            this.renderer = renderer;
        }
    }

    public void setText(String newText) {
        text = newText;
    }
}

