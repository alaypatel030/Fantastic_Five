/**
 * @author Alay Patel (leader)
 * Group 5 
 */

package com.github.fantastic_five.GUIStudent;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.github.fantastic_five.StudentRegistrationMain;
import com.github.fantastic_five.GUIMisc.GUILogStatus;
import com.github.fantastic_five.Logic.Course;
import com.github.fantastic_five.Logic.MiscUtils;
import com.github.fantastic_five.Logic.UserProfile;

@SuppressWarnings("serial")
public class GUIAddDropCourse extends JPanel
{
	/**
	 * Private instant variables
	 */
	private JTextField searchField;
	private JButton btnAdd;
	private JButton btnDrop;
	private JButton btnBack;
	private JButton btnSearch;
	private JLabel lblCrn;
	private JTable searchTable;
	private static JTable addedTable;

	ArrayList<Course> courseSearchResult;

	/**
	 * This GUI class displays the panel for adding and removing courses.
	 *  Here student can search course by CRN that he/she want to add or Drop, 
	 *  and would allow them to do so.
	 */
	public GUIAddDropCourse()
	{

		setBounds(0, 0, 618, 434);
		setLayout(null);

		searchField = new JTextField();
		searchField.setBounds(88, 82, 206, 20);
		add(searchField);
		searchField.setColumns(10);
		searchField.addKeyListener(new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent e)
			{
				if (e.getKeyChar() == KeyEvent.VK_ENTER)
				{
					btnSearch.doClick();
				}//end of if
			}//end of keyTyped

			@Override
			public void keyPressed(KeyEvent e)
			{
				/** Do Nothing */
			}//end of keyPressed

			@Override
			public void keyReleased(KeyEvent arg0)
			{
				/** Do Nothing */
				
			}//end of keyReleased
		});//end of addKeyListener

		/**
		 * Creates an another ScrollPane
		 */
		JScrollPane addedScrollPane = new JScrollPane();
		addedScrollPane.setBounds(41, 227, 540, 107);
		add(addedScrollPane);

		/**
		 * Button & Logic for Remove for the list below
		 */

		btnDrop = new JButton("Drop");
		btnDrop.setBounds(180, 345, 254, 23);
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

				/**
				 *  Logic for Yes button inside Confirmation Pop-up
				 */
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
							StudentRegistrationMain.mainCourseManager.removeLearnerFromCourse(MiscUtils.getCurrentLoggedInUser(), (int) searchTable.getModel().getValueAt(rowSel, 0));
							/**
							 * Makes Table-Cell Non-editable
							 */							
							addedTable.setModel(new DefaultTableModel(getClassTable(), new String[] { "CRN", "Class", "Capacity", "Remaining", "Teacher", "Day", "Time", "Room" })
							{
								@Override
								public boolean isCellEditable(int row, int column)
								{
									return false;
								}//end of isCellEdittable
							});//end of setModel
							addedScrollPane.setViewportView(addedTable);
							revalidate();
							repaint();
						}//end of if
						popup.dispose();
					}//end of actionPerformed
				});//end of addActionListener
				popup.getContentPane().add(btnYes);
				
				/*
				 * Logic for No Button inside Confirmation Pop-up
				 */

				JButton btnNo = new JButton("No");
				btnNo.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						popup.dispose();
					}//end of actionPerformed
				});//end of addActionListener
				btnNo.setBounds(191, 49, 100, 23);
				popup.getContentPane().add(btnNo);
			}// end of the actionPerformed
		});// end of the actionPerformed
		add(btnDrop);

		/**
		 * adds a back button with logic behind it.
		 */
		btnBack = new JButton("Back");
		btnBack.setBounds(41, 389, 128, 23);
		btnBack.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				StudentRegistrationMain.replaceMainWindowContents(new GUIStudent());
			}// end of mouseClicked
		});// end of addMouseListener
		add(btnBack);

		/**
		 * adds a label named, "Search By"
		 */
		JLabel lblSearchBy = new JLabel("Search By:");
		lblSearchBy.setBounds(41, 56, 116, 20);
		lblSearchBy.setForeground(Color.GRAY);
		lblSearchBy.setFont(new Font("Verdana", Font.BOLD, 13));
		add(lblSearchBy);

		/**
		 * Adds a label named, "CRN:"
		 */
		lblCrn = new JLabel("CRN:");
		lblCrn.setBounds(43, 84, 46, 14);
		lblCrn.setFont(new Font("Verdana", Font.BOLD, 12));
		add(lblCrn);

		/**
		 * Adds a ScrollPane
		 */
		JScrollPane searchScrollPane = new JScrollPane();
		searchScrollPane.setBounds(41, 132, 539, 50);
		add(searchScrollPane);

		/**
		 * Creates a Table which shall display result of the course that user has searched for
		 */
		searchTable = new JTable();
		searchTable.setModel(new DefaultTableModel(getSearchResultTable(0), new String[] { "CRN", "Class", "Capacity", "Remaining", "Teacher", "Day", "Time", "Room" })

		{
			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}//end of isCellEditable
		});//end of setModel

		/*
		 * Added a Button named "Search" would search for the entered CRN from the Course data. 
		 */
		searchScrollPane.setViewportView(searchTable);

		btnSearch = new JButton("Search");
		btnSearch.setBounds(306, 81, 128, 23);
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
						searchTable.setModel(new DefaultTableModel(getSearchResultTable(CRN), new String[] { "CRN", "Class", "Capacity", "Remaining", "Teacher", "Day", "Time", "Room" })
						{
							@Override
							public boolean isCellEditable(int row, int column)
							{
								return false;
							}//end of isCellEditable
						});//end of setModel
						searchScrollPane.setViewportView(searchTable);
						revalidate();
						repaint();
					}//end of if 
					else
					{
						JLabel errorMessage = new JLabel();
						errorMessage.setForeground(Color.RED);
						errorMessage.setText("CRN Not Found");
						errorMessage.setBounds(88, 102, 206, 20);
						revalidate();
						repaint();
						add(errorMessage);
					}//end of else
				}//end of try
				catch (NumberFormatException exception)
				{
					JLabel notNumbers = new JLabel();
					notNumbers.setForeground(Color.RED);
					notNumbers.setText("Invalid CRN");
					notNumbers.setBounds(90, 102, 206, 20);
					revalidate();
					repaint();
					add(notNumbers);
					// searchField.setText("CRN Must be Numbers Only");

					// TODO: Also needs to notify if searched CRN is wrong!!
				}//end of catch
			}//end of acitonPerformed
		});//end of addActionListener

		add(btnSearch);

		/**
		 * Creates an another Table which shall course that user has added.
		 */
		addedTable = new JTable();
		addedTable.setModel(new DefaultTableModel(getClassTable(), new String[] { "CRN", "Class", "Capacity", "Remaining", "Teacher", "Day", "Time", "Room" }));
		addedScrollPane.setViewportView(addedTable);
		
		/**
		 * Makes Table-Cell Non-editable
		 */
		DefaultTableModel tableModel = new DefaultTableModel()
		{
		    @Override
		    public boolean isCellEditable(int row, int column)
		    {
		       //all cells false
		       return false;
		    }//end of isCellEditable
		};//end of tableModel

		addedTable.setModel(tableModel);

		/**
		 * Button & Logic for Add Courses to list below.
		 */
		btnAdd = new JButton("Add");
		btnAdd.setBounds(180, 193, 254, 23);
		btnAdd.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int rowSel = searchTable.getSelectedRow();

				if (rowSel > -1)
				{
					StudentRegistrationMain.mainCourseManager.addLearnerToCourse(MiscUtils.getCurrentLoggedInUser(), (int) searchTable.getModel().getValueAt(rowSel, 0));
					/**
					 * Makes Table-Cell Non-editable
					 */
					addedTable.setModel(new DefaultTableModel(getClassTable(), new String[] { "CRN", "Class", "Capacity", "Remaining", "Teacher", "Day", "Time", "Room" })
					{
						@Override
						public boolean isCellEditable(int row, int column)
						{
							return false;
						}//end of isCellEditable
					});//end setModel
					addedScrollPane.setViewportView(addedTable);
					revalidate();
					repaint();
				}//end of if
			}// end of the actionPerformed
		});// end of the addActionListener
		add(btnAdd);

		/**
		 * Adds a GUILogIn
		 */
		JPanel loginPanel = new GUILogStatus();
		loginPanel.setBounds(0, 0, 618, 24);
		add(loginPanel);

		/**
		 * Adds a Label named, "Add or Remove Courses."
		 */
		JLabel lblCourseRemoval = new JLabel("Add or Remove Courses");
		lblCourseRemoval.setForeground(Color.GRAY);
		lblCourseRemoval.setFont(new Font("Verdana", Font.BOLD, 16));
		lblCourseRemoval.setHorizontalAlignment(SwingConstants.CENTER);
		lblCourseRemoval.setBounds(177, 30, 243, 23);
		add(lblCourseRemoval);

	}// end of GUIAddorRemoveCourse()
	
	/**
	 * 
	 * @return fills or removes information from the table when user adds or drops course. 
	 */

	public static Object[][] getClassTable()
	{
		Set<Course> enrolledCourses = StudentRegistrationMain.mainCourseManager.getCoursesWithLearner(MiscUtils.getCurrentLoggedInUser());
		Object[][] cells = new Object[enrolledCourses.size()][7];
		int row = 0;
		for (Course c : enrolledCourses)
		{
			UserProfile teacher = StudentRegistrationMain.mainCourseManager.getInstructorWithCourse(c.getCRN());

			cells[row][0] = c.getCRN();
			cells[row][1] = c.getTitle();
			cells[row][2] = c.getStudentCap();
			cells[row][3] = c.getRemainingCap();
			if (teacher != null)
				cells[row][4] = teacher.getFirstName().substring(0, 1) + " " + teacher.getLastName();
			cells[row][5] = c.getDays();
			cells[row][6] = c.getStartTime(Course.TWENTYFOUR_HR_CLOCK) + "-" + c.getEndTime(Course.TWENTYFOUR_HR_CLOCK);
			row++;
		}//end of for loop
		return cells;
	}//end of public static. 
	

	/**
	 * @return a two-dimensional object array for the table with properly pre-filled info
	 */
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
			}//end of test
		});//end of removeIf
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
			cells[row][5] = c.getDays();
			cells[row][6] = c.getStartTime(Course.TWENTYFOUR_HR_CLOCK) + "-" + c.getEndTime(Course.TWENTYFOUR_HR_CLOCK);
			row++;
		}//end of for loop
		return cells;
	}//end of getSearchResultTable
}// end of JPanel extension of GUIAddorRemoveCourse()