package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RenameWindow extends JFrame
{
    private MainWindow father;

    private JLabel oldNameLabel = new JLabel("Old Name");
    private JTextField oldNameText = new JTextField(10);
    private JLabel newNameLabel= new JLabel("New Name");
    private JTextField newNameText= new JTextField(10);
    private JButton okButton = new JButton("OK");
    private JButton cancelButton = new JButton("Cancel");

    public RenameWindow(MainWindow father, GSComponentNode node)
    {
        this.father = father;
        Logic.Component component = (Logic.Component)node.getUserObject();
        oldNameText.setText(component.getName());
        oldNameText.setEditable(false);

        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        JPanel panel = new JPanel(gridBagLayout);

        gridBagConstraints.insets = new Insets(5, 5, 10, 10);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
        gridBagLayout.addLayoutComponent(oldNameLabel, gridBagConstraints);
        panel.add(oldNameLabel);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagLayout.addLayoutComponent(oldNameText, gridBagConstraints);
        panel.add(oldNameText);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
        gridBagLayout.addLayoutComponent(newNameLabel, gridBagConstraints);
        panel.add(newNameLabel);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagLayout.addLayoutComponent(newNameText, gridBagConstraints);
        panel.add(newNameText);

        GridBagLayout buttonLayout = new GridBagLayout();
        GridBagConstraints buttonConstraint = new GridBagConstraints();
        JPanel buttonPanel = new JPanel(buttonLayout);
        // buttons
        {
            buttonConstraint.insets = new Insets(5, 5, 10, 10);
            buttonConstraint.gridx = 0;
            buttonConstraint.gridy = 0;
            buttonConstraint.gridwidth = GridBagConstraints.RELATIVE;
            buttonLayout.addLayoutComponent(okButton, buttonConstraint);
            buttonPanel.add(okButton);
            okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String newName = newNameText.getText();
                    if(newName.equals(""))
                    {
                        JOptionPane.showConfirmDialog(null, "Please enter new name!", "Attention", JOptionPane.OK_OPTION, JOptionPane.NO_OPTION);
                    }
                    else
                    {
                        component.rename(newName);
                        father.refreshTree(node);
                        father.refreshTableNStatistic();
                        father.setEnabled(true);
                        father.setModified();
                        dispose();
                    }
                }
            });

            buttonConstraint.gridx = 1;
            buttonConstraint.gridy = 0;
            buttonConstraint.gridwidth = GridBagConstraints.REMAINDER;
            buttonLayout.addLayoutComponent(cancelButton, buttonConstraint);
            buttonPanel.add(cancelButton);
            okButton.setPreferredSize(cancelButton.getPreferredSize());
        }
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagLayout.addLayoutComponent(buttonPanel, gridBagConstraints);
        panel.add(buttonPanel);

        setContentPane(panel);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }
}
