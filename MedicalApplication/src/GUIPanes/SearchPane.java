package GUIPanes;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Database.Database;
import GUI.MainWindow;
import Identifiers.Search1Identifier;
import Identifiers.Search2Identifier;
import Identifiers.Search3Identifier;
import Identifiers.SearchIdentifier;

@SuppressWarnings("serial")
public class SearchPane extends JPanel implements ActionListener {

	private int selectedMode = 1;

	/* Main pane */
	JRadioButton search1;
	JRadioButton search2;
	JRadioButton search3;

	/* Search 1 */
	JTextField patientName;
	JTextField testType;

	/* Search 2 */
	JTextField doctor;
	JSpinner startDate;
	JSpinner endDate;

	JButton search;
	JPanel form;

	/* Results */
	JTable results;
	JButton back;

	private final Database database;

	public SearchPane(Database database) {
		this.database = database;

		/* Main Pane */
		search1 = new JRadioButton("Search by Patient or Test");
		search1.setSelected(true);
		search1.setFont(MainWindow.FONT);
		search1.addActionListener(this);

		search2 = new JRadioButton("Search Doctor Prescriptions");
		search2.setFont(MainWindow.FONT);
		search2.addActionListener(this);

		search3 = new JRadioButton("Search Alarming Age");
		search3.setFont(MainWindow.FONT);
		search3.addActionListener(this);

		ButtonGroup group = new ButtonGroup();
		group.add(search1);
		group.add(search2);
		group.add(search3);

		/* Initialise each search mode */
		patientName = new JTextField();
		testType = new JTextField();

		doctor = new JTextField();
		SpinnerDateModel sdatemod = new SpinnerDateModel();
		startDate = new JSpinner(sdatemod);
		startDate.setEditor(new JSpinner.DateEditor(startDate, "dd/MM/yyyy"));

		SpinnerDateModel sdatemod1 = new SpinnerDateModel();
		endDate = new JSpinner(sdatemod1);
		endDate.setEditor(new JSpinner.DateEditor(endDate, "dd/MM/yyyy"));

		/* Form pane */
		search = new JButton("Search");
		search.setFont(MainWindow.FONT);
		search.addActionListener(this);

		/* Result */
		back = new JButton("Back");
		back.setFont(MainWindow.FONT);
		back.addActionListener(this);

		this.renderMain();
	}

	private void renderMain() {
		this.removeAll();

		super.setBorder(MainWindow.getBorder("Search Engine"));
		BorderLayout layout = new BorderLayout();
		layout.setHgap(50);
		super.setLayout(layout);

		JPanel radioPanel = new JPanel();
		radioPanel.setLayout(new GridLayout(0, 1));
		JPanel outterRadioPanel = new JPanel();
		outterRadioPanel.setLayout(new BorderLayout());
		radioPanel.add(search1);
		radioPanel.add(search2);
		radioPanel.add(search3);
		outterRadioPanel.add(radioPanel, BorderLayout.NORTH);

		this.add(outterRadioPanel, BorderLayout.WEST);

		form = new JPanel();
		GridLayout formLayout = new GridLayout(8, 2);
		formLayout.setVgap(5);
		form.setLayout(formLayout);
		this.add(form, BorderLayout.CENTER);

		if (this.selectedMode == 1)
			this.renderSearch1();
		else if (this.selectedMode == 2)
			this.renderSearch2();
		else if (this.selectedMode == 3)
			this.renderSearch3();
	}

	private void renderSearch1() {
		form.removeAll();

		JLabel label_patientName = new JLabel("Patient Name");
		label_patientName.setFont(MainWindow.FONT);
		form.add(label_patientName);
		form.add(patientName);

		JLabel label_testType = new JLabel("Test Type");
		label_testType.setFont(MainWindow.FONT);
		form.add(label_testType);
		form.add(testType);

		form.add(new JPanel());
		form.add(new JPanel());

		form.add(new JPanel());
		JPanel button = new JPanel();
		BorderLayout border = new BorderLayout();
		button.setLayout(border);
		button.add(search, BorderLayout.EAST);
		form.add(button);

		form.add(new JPanel());
		form.add(new JPanel());

		form.add(new JPanel());
		form.add(new JPanel());

		form.add(new JPanel());
		form.add(new JPanel());

		this.updateUI();
	}

	private void renderSearch2() {
		form.removeAll();

		JLabel label_doctorName = new JLabel("Doctor Name or ID");
		label_doctorName.setFont(MainWindow.FONT);
		form.add(label_doctorName);
		form.add(doctor);

		JLabel label_startDate = new JLabel("Start Date");
		label_startDate.setFont(MainWindow.FONT);
		form.add(label_startDate);
		form.add(startDate);

		JLabel label_endDate = new JLabel("End Date");
		label_endDate.setFont(MainWindow.FONT);
		form.add(label_endDate);
		form.add(endDate);

		form.add(new JPanel());
		form.add(new JPanel());

		form.add(new JPanel());
		JPanel button = new JPanel();
		BorderLayout border = new BorderLayout();
		button.setLayout(border);
		button.add(search, BorderLayout.EAST);
		form.add(button);

		form.add(new JPanel());
		form.add(new JPanel());

		form.add(new JPanel());
		form.add(new JPanel());

		this.updateUI();
	}

	private void renderSearch3() {
		form.removeAll();

		JLabel label_testType = new JLabel("Test Type");
		label_testType.setFont(MainWindow.FONT);
		form.add(label_testType);
		form.add(testType);

		form.add(new JPanel());
		form.add(new JPanel());

		form.add(new JPanel());
		JPanel button = new JPanel();
		BorderLayout border = new BorderLayout();
		button.setLayout(border);
		button.add(search, BorderLayout.EAST);
		form.add(button);

		form.add(new JPanel());
		form.add(new JPanel());

		form.add(new JPanel());
		form.add(new JPanel());

		form.add(new JPanel());
		form.add(new JPanel());

		form.add(new JPanel());
		form.add(new JPanel());

		this.updateUI();
	}

	private void renderSearchResult(Vector<SearchIdentifier> resultSet) {
		this.removeAll();

		setLayout(new BorderLayout());

		DefaultTableModel ptable = new DefaultTableModel();

		results = new JTable(ptable);

		if (this.selectedMode == 1) {

			ptable.addColumn("Name");
			ptable.addColumn("Test");
			ptable.addColumn("Test Date");
			ptable.addColumn("Result");

			for (int i = 0; i < resultSet.size(); i++) {
				Search1Identifier row = (Search1Identifier) resultSet.elementAt(i);

				ptable.addRow(new Object[] { row.getPatientName(), row.getTestName(),
						row.getTestDate(), row.getResult() });
			}

			results.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			results.getColumnModel().getColumn(0).setPreferredWidth(145);
			results.getColumnModel().getColumn(1).setPreferredWidth(150);
			results.getColumnModel().getColumn(2).setPreferredWidth(100);
			results.getColumnModel().getColumn(3).setPreferredWidth(200);
		} else if (this.selectedMode == 2) {

			ptable.addColumn("Name");
			ptable.addColumn("Test");
			ptable.addColumn("Prescribe Date");

			for (int i = 0; i < resultSet.size(); i++) {
				Search2Identifier row = (Search2Identifier) resultSet.elementAt(i);

				ptable.addRow(new Object[] { row.getPatientName(), row.getTestName(),
						row.getPrescribeDate() });
			}

			results.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			results.getColumnModel().getColumn(0).setPreferredWidth(245);
			results.getColumnModel().getColumn(1).setPreferredWidth(250);
			results.getColumnModel().getColumn(2).setPreferredWidth(100);
		} else if (this.selectedMode == 3) {

			ptable.addColumn("Name");
			ptable.addColumn("Address");
			ptable.addColumn("Phone");

			for (int i = 0; i < resultSet.size(); i++) {
				Search3Identifier row = (Search3Identifier) resultSet.elementAt(i);

				ptable.addRow(new Object[] { row.getPatientName(), row.getPatientName(),
						row.getAddress(), row.getPhone() });
			}

			results.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			results.getColumnModel().getColumn(0).setPreferredWidth(245);
			results.getColumnModel().getColumn(1).setPreferredWidth(250);
			results.getColumnModel().getColumn(2).setPreferredWidth(100);
		}

		results.setEnabled(false);
		JScrollPane scrollPane = new JScrollPane(results);

		add(scrollPane, BorderLayout.CENTER);
		JPanel button = new JPanel();
		BorderLayout border = new BorderLayout();
		button.setLayout(border);
		button.add(back, BorderLayout.WEST);
		add(button, BorderLayout.SOUTH);

		this.updateUI();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == search1) {
			selectedMode = 1;
			this.renderSearch1();
		} else if (e.getSource() == search2) {
			selectedMode = 2;
			this.renderSearch2();
		} else if (e.getSource() == search3) {
			selectedMode = 3;
			this.renderSearch3();
		} else if (e.getSource() == search) {
			Vector<Object> arguments = new Vector<Object>();
			if (this.selectedMode == 1) {
				arguments.add(patientName.getText());
				arguments.add(testType.getText());
			} else if (this.selectedMode == 2) {
				arguments.add(doctor.getText());
				arguments.add(startDate.getValue());
				arguments.add(endDate.getValue());
			} else if (this.selectedMode == 3) {
				arguments.add(testType.getText());
			}
			Vector<SearchIdentifier> results = database.searchPane(selectedMode, arguments);

			this.renderSearchResult(results);
		} else if (e.getSource() == back)
			this.renderMain();
	}
}
