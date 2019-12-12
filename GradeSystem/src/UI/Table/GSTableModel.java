package UI.Table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GSTableModel extends DefaultTableModel
{
    public GSTableModel(Object[][] data, Object[] columnNames)
    {
        super(data, columnNames);
    }
}
