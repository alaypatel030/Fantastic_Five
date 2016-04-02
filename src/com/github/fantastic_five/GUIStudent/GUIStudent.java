/**
 * @author Alay Patel (leader)
 * Group 5 
 */

package com.github.fantastic_five.GUIStudent;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.github.fantastic_five.StudentRegistrationMain;
import com.github.fantastic_five.GUIMisc.GUILoggedIn;

@SuppressWarnings("serial")
public class GUIStudent extends JPanel
{

	/**
	 * This GUIStudent class extends the JPanel. This is the first display that the Student user will see when he/she logs in to his/her account.
	 * 
	 * @return A window Containing the main actions a student can do
	 */
	public GUIStudent()
	{
		setLayout(null);
		setBounds(0, 0, 618, 434);

		/**
		 * Button & Logic for View Courses
		 */
		JButton btnView = new JButton("View Courses");
		btnView.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				StudentRegistrationMain.replaceMainWindowContents(new GUIViewCourses());
			}// end of actionPeformed
		});// end of addActionListener
		btnView.setBounds(178, 186, 243, 23);
		add(btnView);

		/**
		 * Button & Logic for Add or Remove Course
		 */
		JButton btnAddremoveCourse = new JButton("Add / Remove Course");
		btnAddremoveCourse.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				StudentRegistrationMain.replaceMainWindowContents(new GUIAddRemoveCourse());
			}// end of actionPeformed
		});// end of addActionListener
		btnAddremoveCourse.setBounds(178, 220, 243, 23);
		add(btnAddremoveCourse);

		/**
		 * Button & Logic for for View Schedule
		 */
		JButton btnViewSchedule = new JButton("View Schedule");
		btnViewSchedule.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				StudentRegistrationMain.replaceMainWindowContents(new GUIViewSchedule());
			}// end of actionPeformed
		});// end of addActionListener
		btnViewSchedule.setBounds(178, 254, 243, 23);
		add(btnViewSchedule);

		/**
		 * Panel Label, essentially
		 */
		JLabel lblStudent = new JLabel("Student");
		lblStudent.setBounds(232, 78, 46, 14);
		lblStudent.setForeground(Color.GRAY);
		lblStudent.setFont(new Font("Verdana", Font.BOLD, 16));
		lblStudent.setHorizontalAlignment(SwingConstants.CENTER);
		lblStudent.setBounds(178, 96, 243, 23);
		add(lblStudent);

		/**
		 * Adds the login panel
		 */
		JPanel loginPanel = new GUILoggedIn();
		loginPanel.setBounds(0, 0, 618, 24);
		add(loginPanel);

	}// end of GUIStudent()
}// end of JPanel extension of GUIStudent
