package UI.Table;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.util.Enumeration;
import java.util.Vector;

public class GroupableTableHeader extends JTableHeader
{
    private static final String uiClassID = "GroupableTableHeaderUI";
    protected Vector columnGroups = null;

    public GroupableTableHeader(TableColumnModel model)
    {
        super(model);
        setUI(new GroupableTableHeaderUI());
        setReorderingAllowed(false);
        setRequestFocusEnabled(false);
    }

    public void addColumnGroup(ColumnGroup g)
    {
        if (columnGroups == null)
        {
            columnGroups = new Vector();
        }
        columnGroups.addElement(g);
    }

    public void clearColumnGroups() {
        columnGroups = null;
    }

    public ColumnGroup[] getColumnGroups()
    {
        ColumnGroup[] retg = null;
        if (columnGroups.size() > 0)
        {
            retg = new ColumnGroup[columnGroups.size()];
            columnGroups.copyInto(retg);
        }
        return retg;
    }

    public Enumeration getColumnGroups(TableColumn col)
    {
        if (columnGroups == null)
        {
            return null;
        }
        Enumeration enum1 = columnGroups.elements();
        while (enum1.hasMoreElements())
        {
            ColumnGroup cGroup = (ColumnGroup) enum1.nextElement();
            Vector v_ret = (Vector) cGroup.getColumnGroups(col, new Vector());
            if (v_ret != null)
            {
                return v_ret.elements();
            }
        }
        return null;
    }

    public boolean isFocusTraversable() {
        return super.isFocusTraversable() && isRequestFocusEnabled();
    }

    public void setColumnMargin()
    {
        if (columnGroups == null)
        {
            return;
        }
        int columnMargin = getColumnModel().getColumnMargin();
        Enumeration enum1 = columnGroups.elements();
        while (enum1.hasMoreElements())
        {
            ColumnGroup cGroup = (ColumnGroup) enum1.nextElement();
            cGroup.setColumnMargin(columnMargin);
        }
    }

    public void setReorderingAllowed(boolean b) {
        reorderingAllowed = b;
    }
}
