/*
 * Created by JFormDesigner on Fri Dec 06 01:31:36 EST 2019
 */

package UI;

import java.awt.*;
import javax.swing.*;

/**
 * @author unknown
 */
public class MainUI extends GSFrame {
    public MainUI() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        super();
        initComponents();
        backGround.setVisible(true);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        backGround = new JFrame();
        treePanel = new JScrollPane();
        tree = new JTree();
        tablePanel = new JScrollPane();
        table = new JTable();
        panel1 = new JPanel();
        label1 = new JLabel();
        textField1 = new JTextField();
        label2 = new JLabel();
        textField2 = new JTextField();
        label3 = new JLabel();
        textField3 = new JTextField();
        label4 = new JLabel();
        textField4 = new JTextField();
        panel2 = new JPanel();
        button1 = new JButton();
        button2 = new JButton();

        //======== backGround ========
        {
            backGround.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            Container backGroundContentPane = backGround.getContentPane();
            backGroundContentPane.setLayout(new GridBagLayout());
            ((GridBagLayout)backGroundContentPane.getLayout()).columnWidths = new int[] {131, 457, 0, 0};
            ((GridBagLayout)backGroundContentPane.getLayout()).rowHeights = new int[] {360, 64, 0, 0};
            ((GridBagLayout)backGroundContentPane.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0, 1.0E-4};
            ((GridBagLayout)backGroundContentPane.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0, 1.0E-4};

            //======== treePanel ========
            {

                //---- tree ----
                tree.setBackground(new Color(36, 41, 46));
                tree.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));
                treePanel.setViewportView(tree);
            }
            backGroundContentPane.add(treePanel, new GridBagConstraints(0, 0, 1, 3, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

            //======== tablePanel ========
            {
                tablePanel.setViewportView(table);
            }
            backGroundContentPane.add(tablePanel, new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //======== panel1 ========
            {
                panel1.setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.
                swing.border.EmptyBorder(0,0,0,0), "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn",javax.swing.border
                .TitledBorder.CENTER,javax.swing.border.TitledBorder.BOTTOM,new java.awt.Font("Dia\u006cog"
                ,java.awt.Font.BOLD,12),java.awt.Color.red),panel1. getBorder
                ()));panel1. addPropertyChangeListener(new java.beans.PropertyChangeListener(){@Override public void propertyChange(java
                .beans.PropertyChangeEvent e){if("\u0062ord\u0065r".equals(e.getPropertyName()))throw new RuntimeException
                ();}});
                panel1.setLayout(new GridBagLayout());
                ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0};
                ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
                ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

                //---- label1 ----
                label1.setText("text");
                panel1.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));
                panel1.add(textField1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- label2 ----
                label2.setText("text");
                panel1.add(label2, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));
                panel1.add(textField2, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- label3 ----
                label3.setText("text");
                panel1.add(label3, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));
                panel1.add(textField3, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- label4 ----
                label4.setText("text");
                panel1.add(label4, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));
                panel1.add(textField4, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));
            }
            backGroundContentPane.add(panel1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //======== panel2 ========
            {
                panel2.setLayout(new GridBagLayout());
                ((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0, 0};
                ((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0};
                ((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
                ((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                //---- button1 ----
                button1.setText("text");
                panel2.add(button1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- button2 ----
                button2.setText("text");
                panel2.add(button2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            backGroundContentPane.add(panel2, new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
            backGround.pack();
            backGround.setLocationRelativeTo(backGround.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JFrame backGround;
    private JScrollPane treePanel;
    private JTree tree;
    private JScrollPane tablePanel;
    private JTable table;
    private JPanel panel1;
    private JLabel label1;
    private JTextField textField1;
    private JLabel label2;
    private JTextField textField2;
    private JLabel label3;
    private JTextField textField3;
    private JLabel label4;
    private JTextField textField4;
    private JPanel panel2;
    private JButton button1;
    private JButton button2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        MainUI t = new MainUI();
    }
}
