package UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Logic.Component;
import Logic.Course;
import Logic.Grade;
import Logic.GradeMap;
import Logic.Score;
import Logic.Student;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewComment extends JFrame {

	private JPanel contentPane;
	private JTextArea textArea_comment;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
//				try {
//					ViewComment frame = new ViewComment();
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
	public ViewComment(Component c, Student s, Course course, MainWindow mw) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 309);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel student_name = new JLabel("New label");
		student_name.setBounds(30, 10, 123, 27);
		contentPane.add(student_name);
		student_name.setText(s.toString());
		
		JLabel course_comp_name = new JLabel("New label");
		course_comp_name.setBounds(357, 10, 67, 27);
		contentPane.add(course_comp_name);
		course_comp_name.setText(c.getName());
		
		
		textArea_comment = new JTextArea();
		textArea_comment.setBounds(41, 83, 347, 110);
		contentPane.add(textArea_comment);
		GradeMap gm = course.getGradeMap();
		Grade g = gm.getGrade(s);
		Score score = g.getsMap().get(c);
		if(score == null)
		{
			score = new Score();
			g.getsMap().put(c, score);
		}
		textArea_comment.setText(score.getComment());
		
		JLabel comment = new JLabel("Grader's comments");
		comment.setBounds(30, 56, 123, 27);
		contentPane.add(comment);
		
		JButton save = new JButton("Save");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GradeMap gm = course.getGradeMap();
				Grade g = gm.getGrade(s);
				Score score = g.getsMap().get(c);
				String comment_str = textArea_comment.getText();
				score.setComment(comment_str);
				
				JOptionPane.showMessageDialog(save,"Modify comment Succesfully!");
				ViewComment.this.setVisible(false);
				mw.refreshTableNStatistic();
				dispose();
			}
		});
		save.setBounds(60, 213, 93, 23);
		contentPane.add(save);
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewComment.this.setVisible(false);
				dispose();
			}
		});
		cancel.setBounds(277, 213, 93, 23);
		contentPane.add(cancel);
	}
}
