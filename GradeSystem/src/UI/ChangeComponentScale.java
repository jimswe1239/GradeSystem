package UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Logic.Component;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChangeComponentScale extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
//				try {
//					ChangeComponentScale frame = new ChangeComponentScale();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ChangeComponentScale(GSComponentNode node, Component root, MainWindow mw) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 335, 214);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Old Scale");
		lblNewLabel.setBounds(36, 28, 73, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewScale = new JLabel("New Scale");
		lblNewScale.setBounds(36, 64, 73, 15);
		contentPane.add(lblNewScale);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(150, 28, 58, 15);
		contentPane.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(150, 61, 66, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String componentScaleStr = textField.getText();
				int percentage;
				try {
					percentage = Integer.parseInt(componentScaleStr);
				}
				catch(NumberFormatException e1) {
					JOptionPane.showMessageDialog(btnNewButton,"Incorrect format about the percentage!");
					return;
				}
				root.changePercentageAndScale(percentage);
				JOptionPane.showMessageDialog(btnNewButton,"Change the scale of component: "+root.getName()+" Succesfully!");
				mw.refreshTree(node);
				mw.refreshTableNStatistic();
				ChangeComponentScale.this.setVisible(false);
				dispose();
			}
		});
		btnNewButton.setBounds(42, 123, 97, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ChangeComponentScale.this.setVisible(false);
				dispose();
			}
		});
		btnNewButton_1.setBounds(168, 123, 97, 23);
		contentPane.add(btnNewButton_1);
	}
}
