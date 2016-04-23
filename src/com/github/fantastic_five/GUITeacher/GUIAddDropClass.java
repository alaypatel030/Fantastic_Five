package com.github.fantastic_five.GUITeacher;

/**
 * @author Christian Phillips
 * Group 5 
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.github.fantastic_five.StudentRegistrationMain;
import com.github.fantastic_five.GUI.UneditableTableModel;
import com.github.fantastic_five.GUI.UniversalBackButton;
import com.github.fantastic_five.GUIMisc.GUILogStatus;
import com.github.fantastic_five.Logic.Course;
import com.github.fantastic_five.Logic.Course.Day;
import com.github.fantastic_five.Logic.ScheduleManager;
import com.github.fantastic_five.Logic.UserProfile;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class GUIAddDropClass extends JPanel
{
	// private instance variables.
	private JTextField searchField;
	private JButton btnAdd;
	private JButton btnDrop;
	private JButton btnSearch;
	private JLabel lblCrn;
	private JLabel lblClassReq;
	private JTable searchTable;
	private static JTable addedTable;

	ArrayList<Course> courseSearchResult;

	/**
	 * This GUI class displays the panel for adding and removing courses that the teacher is teaching.
	 */
	public GUIAddDropClass()
	{
		setBounds(0, 0, 618, 434);
		setLayout(null);

		searchField = new JTextField();
		searchField.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				searchField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

			}
		});
		searchField.setBounds(98, 95, 128, 20);
		add(searchField);
		searchField.setColumns(10);
		searchField.addKeyListener(new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent e)
			{
				searchField.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
				if (e.getKeyChar() == KeyEvent.VK_ENTER)
				{
					btnSearch.doClick();
				}
			}

			@Override
			public void keyPressed(KeyEvent e)
			{
				/** Do Nothing */
			}

			@Override
			public void keyReleased(KeyEvent e)
			{
				/** Do Nothing */
			}
		});

		/**
		 * Creates another scroll pane
		 */
		JScrollPane addedScrollPane = new JScrollPane();
		addedScrollPane.setBounds(10, 216, 598, 107);
		add(addedScrollPane);

		/**
		 * Button & Logic for Remove Courses
		 */
		btnDrop = new JButton("Drop");
		btnDrop.setBounds(242, 335, 128, 23);
		btnDrop.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JDialog popup = new JDialog(StudentRegistrationMain.mainWindow, "Confirmation");
				popup.setBounds(100, 100, 307, 107);
				popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				popup.setLocationRelativeTo(null);
				popup.getContentPane().setLayout(null);
				popup.setResizable(false);
				popup.setVisible(true);
				popup.setAlwaysOnTop(true);

				JLabel txtpnAreYouSure = new JLabel();
				txtpnAreYouSure.setText("Are you sure?");
				txtpnAreYouSure.setForeground(Color.RED);
				txtpnAreYouSure.setFont(new Font("Verdana", Font.BOLD, 16));
				txtpnAreYouSure.setBounds(86, 11, 127, 20);
				popup.getContentPane().add(txtpnAreYouSure);

				// Logic for Yes button on popup
				JButton btnYes = new JButton("Yes");
				btnYes.setBounds(10, 49, 100, 23);
				btnYes.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						// Gets the selected row
						int rowSel = addedTable.getSelectedRow();
						if (rowSel > -1)
						{
							StudentRegistrationMain.mainCourseManager.removeInstructorFromCourse(StudentRegistrationMain.getCurrentLoggedInUser(), (int) addedTable.getModel().getValueAt(rowSel, 0));

							addedTable.setModel(new DefaultTableModel(getClassTable(), new String[] { "CRN", "Class", "Capacity", "Remaining", "Teacher", "Day", "Time" })
							{
								@Override
								public boolean isCellEditable(int row, int column)
								{
									return false;
								}
							});
							addedScrollPane.setViewportView(addedTable);
							revalidate();
							repaint();
						}
						popup.dispose();
					}
				});
				popup.getContentPane().add(btnYes);

				JButton btnNo = new JButton("No");
				btnNo.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						popup.dispose();
					}
				});
				btnNo.setBounds(191, 49, 100, 23);
				popup.getContentPane().add(btnNo);
			}
		});
		add(btnDrop);

		/**
		 * Adds a back button
		 */
		JButton btnBack = new UniversalBackButton();
		btnBack.setBounds(10, 389, 128, 23);
		add(btnBack);

		/**
		 * Adds a label named "Search By"
		 */
		JLabel lblSearchBy = new JLabel("Search By:");
		lblSearchBy.setBounds(10, 64, 116, 20);
		lblSearchBy.setForeground(Color.GRAY);
		lblSearchBy.setFont(new Font("Verdana", Font.BOLD, 13));
		add(lblSearchBy);

		/**
		 * Adds a label named "CRN:"
		 */
		lblCrn = new JLabel("CRN:");
		lblCrn.setBounds(42, 97, 46, 14);
		lblCrn.setFont(new Font("Verdana", Font.BOLD, 12));
		add(lblCrn);

		/**
		 * Adds a scroll pane
		 */
		JScrollPane searchScrollPane = new JScrollPane();
		searchScrollPane.setBounds(10, 128, 598, 43);
		add(searchScrollPane);

		/**
		 * Adds a table to display searched classes
		 */
		searchTable = new JTable();
		searchTable.setModel(new DefaultTableModel(getSearchResultTable(0), new String[] { "CRN", "Class", "Capacity", "Remaining", "Teacher", "Day", "Time" })
		{
			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		});
		searchScrollPane.setViewportView(searchTable);

		/**
		 * Button & Logic for Search button
		 */
		btnSearch = new JButton("Search");
		btnSearch.setBounds(242, 95, 128, 23);
		btnSearch.addActionListener(new ActionListener()
		{
			@Override

			public void actionPerformed(ActionEvent e)
			{
				try
				{
					int CRN = Integer.parseInt(searchField.getText());
					if (StudentRegistrationMain.mainCourseManager.getCourse(CRN) != null)
					{
						searchTable.setModel(new DefaultTableModel(getSearchResultTable(CRN), new String[] { "CRN", "Class", "Capacity", "Remaining", "Teacher", "Day", "Time" })
						{
							@Override
							public boolean isCellEditable(int row, int column)
							{
								return false;
							}
						});
						searchScrollPane.setViewportView(searchTable);
						revalidate();
						repaint();
					}
					else
					{
						JLabel errorMessage = new JLabel();
						errorMessage.setForeground(Color.RED);
						errorMessage.setText("CRN Not Found");
						errorMessage.setBounds(88, 102, 206, 20);
						revalidate();
						repaint();
						add(errorMessage);
					}
				}
				catch (NumberFormatException exception)
				{
					searchField.setBorder(BorderFactory.createLineBorder(Color.RED));
					revalidate();
					repaint();
				}
			}
		});
		add(btnSearch);

		/**
		 * Adds another table that displays courses the user has added
		 */
		addedTable = new JTable();
		addedTable.setModel(new DefaultTableModel(getClassTable(), new String[] { "CRN", "Class", "Capacity", "Remaining", "Teacher", "Day", "Time" }));
		addedScrollPane.setViewportView(addedTable);
		addedTable.setAutoCreateRowSorter(true);

		/**
		 * Button & Logic for Add courses
		 */
		btnAdd = new JButton("Add");
		btnAdd.setBounds(242, 183, 128, 23);
		btnAdd.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int rowSel = searchTable.getSelectedRow();

				if (rowSel > -1)
				{
					Set<Course> conflicts = ScheduleManager.getConflictingCourses((int) searchTable.getModel().getValueAt(searchTable.convertRowIndexToModel(rowSel), 0), StudentRegistrationMain.getCurrentLoggedInUser());
					if (conflicts.size() > 0)
					{
						JDialog conflict = new JDialog(StudentRegistrationMain.mainWindow, "Course Conflict");
						conflict.setBounds(100, 100, 343, 127);
						conflict.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						conflict.setLocationRelativeTo(null);
						conflict.getContentPane().setLayout(null);
						conflict.setResizable(false);
						conflict.setVisible(true);
						conflict.setAlwaysOnTop(true);

						JScrollPane scrollPane = new JScrollPane();
						scrollPane.setBounds(0, 0, 339, 99);
						conflict.getContentPane().add(scrollPane);

						JTable table = new JTable();
						table.setModel(new UneditableTableModel(getConflictTable((int) searchTable.getModel().getValueAt(table.convertRowIndexToModel(rowSel), 0)), new String[] { "CRN", "Class", "Time" }));
						scrollPane.setViewportView(table);
					}
					// Checks to make sure the user isn't taking MORE than 5 classes
					else if (StudentRegistrationMain.mainCourseManager.getCoursesWithInstructor(StudentRegistrationMain.getCurrentLoggedInUser()).size() <= 5)
					{
						StudentRegistrationMain.mainCourseManager.addInstructorToCourse(StudentRegistrationMain.getCurrentLoggedInUser(), (int) searchTable.getModel().getValueAt(searchTable.convertRowIndexToModel(rowSel), 0));
						addedTable.setModel(new UneditableTableModel(getClassTable(), new String[] { "CRN", "Class", "Capacity", "Remaining", "Teacher", "Day", "Time" }));
						searchTable.setModel(new UneditableTableModel(getSearchResultTable(Integer.parseInt(searchField.getText())), new String[] { "CRN", "Class", "Capacity", "Remaining", "Teacher", "Day", "Time" }));
						revalidate();
						repaint();
					}
				}
			}
		});
		add(btnAdd);

		/**
		 * Adds a GUILogin
		 */
		JPanel loginPanel = new GUILogStatus();
		loginPanel.setBounds(0, 0, 618, 24);
		add(loginPanel);

		/**
		 * Adds a label named "Add or Remove Courses
		 */
		JLabel lblCourseRemoval = new JLabel("Add/Drop Courses");
		lblCourseRemoval.setForeground(Color.GRAY);
		lblCourseRemoval.setFont(new Font("Verdana", Font.BOLD, 16));
		lblCourseRemoval.setHorizontalAlignment(SwingConstants.CENTER);
		lblCourseRemoval.setBounds(10, 30, 598, 23);
		add(lblCourseRemoval);
	}

	/**
	 * @return a two-dimensional object array for the table with properly pre-filled info
	 */
	public static Object[][] getClassTable()
	{
		Set<Course> enrolledCourses = StudentRegistrationMain.mainCourseManager.getCoursesWithInstructor(StudentRegistrationMain.getCurrentLoggedInUser());
		Object[][] cells = new Object[enrolledCourses.size()][7];
		int row = 0;
		for (Course c : enrolledCourses)
		{
			UserProfile teacher = StudentRegistrationMain.mainCourseManager.getInstructorWithCourse(c.getCRN());

			cells[row][0] = c.getCRN();
			cells[row][1] = c.getTitle();
			cells[row][2] = c.getStudentCap();
			cells[row][3] = c.getRemainingCap();
			cells[row][4] = teacher == null ? "TBA" : teacher.getFirstName().substring(0, 1) + " " + teacher.getLastName();
			cells[row][5] = getFormattedDays(c.getDays());
			cells[row][6] = c.getStartTime(Course.TWENTYFOUR_HR_CLOCK) + "-" + c.getEndTime(Course.TWENTYFOUR_HR_CLOCK);
			row++;
		}
		return cells;
	}

	private Object[][] getConflictTable(int CRN)
	{
		Set<Course> courseConflict = StudentRegistrationMain.mainCourseManager.getCoursesWithInstructor(StudentRegistrationMain.getCurrentLoggedInUser());
		Object[][] cells = new Object[courseConflict.size()][3];
		int row = 0;

		for (Course c : courseConflict)
		{
			cells[row][0] = c.getCRN();
			cells[row][1] = c.getTitle();
			cells[row][2] = c.getStartTime(Course.TWENTYFOUR_HR_CLOCK) + "-" + c.getEndTime(Course.TWENTYFOUR_HR_CLOCK);
			row++;
		}
		return cells;
	}

	private Object[][] getSearchResultTable(int CRN)
	{
		// Some local variables that help me later. Wastes memory, maybe - but saves typing a lot
		TreeSet<Course> courseOfferings = StudentRegistrationMain.mainCourseManager.copyCourseOfferings();
		courseOfferings.removeIf(new Predicate<Course>()
		{
			@Override
			public boolean test(Course toTest)
			{
				return toTest.getCRN() != CRN;
			}
		});
		int numCourses = courseOfferings.size();
		Object[][] cells = new Object[numCourses][7];

		int row = 0;
		// Loops through all courses and sets the columns in each row appropriately
		for (Course c : courseOfferings)
		{
			UserProfile teacher = StudentRegistrationMain.mainCourseManager.getInstructorWithCourse(c.getCRN());

			cells[row][0] = c.getCRN();
			cells[row][1] = c.getTitle();
			cells[row][2] = c.getStudentCap();
			cells[row][3] = c.getRemainingCap();
			cells[row][4] = teacher == null ? "TBA" : teacher.getFirstName().substring(0, 1) + " " + teacher.getLastName();
			cells[row][5] = getFormattedDays(c.getDays());
			cells[row][6] = c.getStartTime(Course.TWENTYFOUR_HR_CLOCK) + "-" + c.getEndTime(Course.TWENTYFOUR_HR_CLOCK);
			row++;
		}
		return cells;
	}

	static String getFormattedDays(TreeSet<Day> days)
	{
		String rVal = "";
		for (Day d : days)
			rVal += d.getAbbreviation() + " ";
		return rVal;
	}
}
