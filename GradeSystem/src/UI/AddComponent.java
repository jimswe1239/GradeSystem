package UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import Logic.Component;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.tree.TreePath;
import java.awt.Color;

public class AddComponent extends JFrame {

	private JPanel contentPane;
	private JTextArea textArea;
	private JTextArea textArea_1;
	private JTextArea textArea_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
//					AddComponent frame = new AddComponent();
//					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AddComponent(GSComponentNode node, Component root, MainWindow mw) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 504, 319);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 490, 194);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblAddComponent = new JLabel("Add a Component");
		lblAddComponent.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 14));
		lblAddComponent.setBounds(10, 10, 134, 21);
		panel.add(lblAddComponent);
		
		textArea = new JTextArea();
		textArea.setBounds(168, 42, 129, 21);
		panel.add(textArea);
		textArea.setColumns(10);
		
		JLabel lblComponentName = new JLabel("Component Name");
		lblComponentName.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 12));
		lblComponentName.setBounds(40, 44, 104, 15);
		panel.add(lblComponentName);
		
		textArea_1 = new JTextArea();
		textArea_1.setColumns(10);
		textArea_1.setBounds(168, 73, 129, 21);
		panel.add(textArea_1);
		
		JLabel lblScalepercentage = new JLabel("Scale (Percentage\r\n)");
		lblScalepercentage.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 12));
		lblScalepercentage.setBounds(40, 75, 104, 15);
		panel.add(lblScalepercentage);
		
		textArea_2 = new JTextArea();
		textArea_2.setForeground(Color.WHITE);
		textArea_2.setColumns(10);
		textArea_2.setBounds(168, 104, 198, 80);
		textArea_2.setLineWrap(true);
		panel.add(textArea_2);
		
		JLabel lblSpecialNote = new JLabel("Special Note");
		lblSpecialNote.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 12));
		lblSpecialNote.setBounds(40, 106, 104, 15);
		panel.add(lblSpecialNote);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.setFont(new Font("Verdana", Font.PLAIN, 12));
		btnNewButton.setBounds(73, 219, 97, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("cancel");
		btnNewButton_1.setFont(new Font("Verdana", Font.PLAIN, 12));
		btnNewButton_1.setBounds(289, 219, 97, 23);
		contentPane.add(btnNewButton_1);
		
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String componentName = textArea.getText();
				String componentScaleStr = textArea_1.getText();
				String specialNote = textArea_2.getText();
				int percentage;
				
				try {
					percentage = Integer.parseInt(componentScaleStr);
				}
				catch(NumberFormatException e1) {
					JOptionPane.showMessageDialog(btnNewButton,"Incorrect format about the percentage!");
					return;
				}
				//if OK
				Component newComponent = new Component(componentName);
				root.addComponentAndScale(newComponent, percentage);
				node.add(new GSComponentNode(newComponent));
				JOptionPane.showMessageDialog(btnNewButton,"Add new component: "+componentName+" Succesfully!");
				mw.refreshTree(node);
				mw.refreshTableNStatistic();
				AddComponent.this.setVisible(false);
				dispose();
			}
			
		});
		
		btnNewButton_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				AddComponent.this.setVisible(false);
				dispose();
			}
		});
	}
}
