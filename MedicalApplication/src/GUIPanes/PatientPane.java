package GUIPanes;

import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import Database.Database;
import GUI.MainWindow;
import Identifiers.PatientIdentifier;

import java.sql.*;

@SuppressWarnings("serial")
public class PatientPane extends JPanel implements ActionListener {

	// Search mode
	protected JTextField patient;
	protected JButton search; // Search button
	protected JButton newPatient; // New patient button

	/* Results Mode */
	protected JButton back;
	protected JButton next;
	protected JTable results;

	/* New patient mode */
	protected JTextField patientNo;
	protected JButton back_newPatient;
	protected JButton save_newPatient;

	/* Patient information mode */
	protected JLabel patientID;
	protected JTextField patientName;
	protected JTextField patientAddress;
	protected JTextField patientPhone;
	protected JSpinner patientBday;
	protected JList testNotAllowed;
	protected JButton saveNotAllowed;
	protected JButton backNotAllowed;
	protected JButton notAllowedTestsButton;
	protected JButton back_patient;
	protected JButton save;

	// protected PatientIdentifier patIDer;

	public PatientPane() {
		// Search mode
		patient = new JTextField();
		search = new JButton("Search");
		search.addActionListener(this);

		newPatient = new JButton("New Patient");
		newPatient.addActionListener(this);
		newPatient.setFont(MainWindow.getFont());

		/* New Patient mode */
		patientNo = new JTextField();
		back_newPatient = new JButton("Back");
		back_newPatient.addActionListener(this);
		back_newPatient.setFont(MainWindow.getFont());
		save_newPatient = new JButton("Save");
		save_newPatient.addActionListener(this);
		save_newPatient.setFont(MainWindow.getFont());

		/* Results Mode */
		back = new JButton("Back");
		back.setFont(MainWindow.getFont());
		back.addActionListener(this);

		next = new JButton("Next");
		next.setFont(MainWindow.getFont());
		next.addActionListener(this);

		/* Patient information mode */
		patientID = new JLabel();
		patientName = new JTextField();
		patientAddress = new JTextField();
		patientPhone = new JTextField();
		patientBday = new JSpinner();

		testNotAllowed = new JList();
		testNotAllowed.setFont(MainWindow.getFont());
		notAllowedTestsButton = new JButton("Edit");
		notAllowedTestsButton.setFont(MainWindow.getFont());
		notAllowedTestsButton.addActionListener(this);

		saveNotAllowed = new JButton("Save");
		saveNotAllowed.setFont(MainWindow.getFont());
		saveNotAllowed.addActionListener(this);

		backNotAllowed = new JButton("Back");
		backNotAllowed.setFont(MainWindow.getFont());
		backNotAllowed.addActionListener(this);

		back_patient = new JButton("Back");
		back_patient.setFont(MainWindow.getFont());
		back_patient.addActionListener(this);
		save = new JButton("Save");
		save.setFont(MainWindow.getFont());
		save.addActionListener(this);

		renderSearch();
	}

	private void renderSearch() {
		GridLayout layout = new GridLayout(8, 2);
		layout.setVgap(5);
		super.setLayout(layout);

		super.setBorder(MainWindow.getBorder("Search Patient"));

		// Patient search
		JLabel patient_label = new JLabel("Name or ID of patient");
		patient_label.setFont(MainWindow.getFont());

		add(patient_label);
		add(patient);

		add(new JPanel());
		add(new JPanel());

		// Finish buttons
		search.setFont(MainWindow.getFont());
		search.addActionListener(this);
		JPanel button1 = new JPanel();
		BorderLayout border1 = new BorderLayout();
		button1.setLayout(border1);
		button1.add(newPatient, BorderLayout.WEST);
		add(button1);
		JPanel button = new JPanel();
		BorderLayout border = new BorderLayout();
		button.setLayout(border);
		button.add(search, BorderLayout.EAST);
		add(button);

		add(new JPanel());
		add(new JPanel());

		// Blank space
		add(new JPanel());
		add(new JPanel());

		// Blank space
		add(new JPanel());
		add(new JPanel());

		// Blank space
		add(new JPanel());
		add(new JPanel());
	}

	private void renderResults() {
		setLayout(new BorderLayout());

		Vector<PatientIdentifier> patientInfo = Database.patientQuery(patient
				.getText());

		DefaultTableModel ptable = new DefaultTableModel();

		results = new JTable(ptable);

		ptable.addColumn("ID");

		ptable.addColumn("Name");

		ptable.addColumn("Address");

		ptable.addColumn("Bday");

		ptable.addColumn("Phone #");

		for (int i = 0; i < patientInfo.size(); i++) {

			ptable.addRow(new Object[] { patientInfo.elementAt(i).ID(),
					patientInfo.elementAt(i).Name(),
					patientInfo.elementAt(i).Address(),
					patientInfo.elementAt(i).Bday(),
					patientInfo.elementAt(i).PhoneNum() });
		}

		results.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		results.getColumnModel().getColumn(0).setPreferredWidth(95);
		results.getColumnModel().getColumn(1).setPreferredWidth(100);
		results.getColumnModel().getColumn(2).setPreferredWidth(200);
		results.getColumnModel().getColumn(3).setPreferredWidth(100);
		results.getColumnModel().getColumn(4).setPreferredWidth(100);

		results.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);

		if (patientInfo.size() >= 1)
			results.getSelectionModel().setSelectionInterval(0, 0);

		results.setRowSelectionAllowed(true);
		results.setColumnSelectionAllowed(false);

		// results.setEnabled(false);
		JScrollPane scrollPane = new JScrollPane(results);

		add(scrollPane, BorderLayout.CENTER);
		JPanel button = new JPanel();
		BorderLayout border = new BorderLayout();
		button.setLayout(border);
		button.add(back, BorderLayout.WEST);
		button.add(next, BorderLayout.EAST);
		add(button, BorderLayout.SOUTH);

	}

	private void renderPatientInformation() {
		GridLayout layout = new GridLayout(8, 2);
		layout.setVgap(5);
		super.setLayout(layout);

		super.setBorder(MainWindow.getBorder("Patient Information"));

		int row = results.getSelectedRow();

		String ID = (String) results.getModel().getValueAt(row, 0);
		String Name = (String) (results.getModel().getValueAt(row, 1));
		String Address = (String) results.getModel().getValueAt(row, 2);
		Date Bday = (Date) results.getModel().getValueAt(row, 3);
		String PhoneNum = (String) results.getModel().getValueAt(row, 4);

		// patIDer = new PatientIdentifier(ID,Name,Address,Bday,PhoneNum);

		/* Patient ID (not editable) */
		JLabel patient_id = new JLabel("Patient ID");
		patient_id.setFont(MainWindow.getFont());
		patientID = new JLabel(ID);
		patientID.setFont(MainWindow.getFont());
		add(patient_id);
		add(patientID);

		/* Patient name */
		JLabel patient_name_label = new JLabel("Name");
		patient_name_label.setFont(MainWindow.getFont());
		add(patient_name_label);
		patientName.setText(Name);
		add(patientName);

		/* Patient address */
		JLabel patient_address_label = new JLabel("Address");
		patient_address_label.setFont(MainWindow.getFont());
		add(patient_address_label);
		patientAddress.setText(Address);
		add(patientAddress);

		/* Patient phone */
		JLabel patient_phone_label = new JLabel("Phone");
		patient_phone_label.setFont(MainWindow.getFont());
		add(patient_phone_label);
		patientPhone.setText(PhoneNum);
		add(patientPhone);

		/* Patient birthdate */
		JLabel patient_bday_label = new JLabel("Birthday");
		patient_bday_label.setFont(MainWindow.getFont());
		add(patient_bday_label);
		SpinnerDateModel sdatemod = new SpinnerDateModel();
		patientBday = new JSpinner(sdatemod);
		patientBday
				.setEditor(new JSpinner.DateEditor(patientBday, "dd/MM/yyyy"));
		patientBday.setValue(Bday);
		add(patientBday);

		JLabel notests = new JLabel("Tests Not Allowed");
		notests.setFont(MainWindow.getFont());
		add(notests);
		add(notAllowedTestsButton);

		// Save button
		JPanel button1 = new JPanel();
		BorderLayout border1 = new BorderLayout();
		button1.setLayout(border1);
		button1.add(back_patient, BorderLayout.WEST);
		add(button1);

		JPanel button = new JPanel();
		BorderLayout border = new BorderLayout();
		button.setLayout(border);
		button.add(save, BorderLayout.EAST);
		add(button);
	}

	private void renderNewPatient() {
		GridLayout layout = new GridLayout(8, 2);
		layout.setVgap(5);
		super.setLayout(layout);

		super.setBorder(MainWindow.getBorder("New Patient"));

		/* Patient ID (not editable) */
		JLabel patient_id = new JLabel("Patient ID");
		patient_id.setFont(MainWindow.getFont());
		patientNo.setText("");
		add(patient_id);
		add(patientNo);

		/* Patient name */
		JLabel patient_name_label = new JLabel("Name");
		patient_name_label.setFont(MainWindow.getFont());
		patientName.setText("");
		add(patient_name_label);
		add(patientName);

		/* Patient address */
		JLabel patient_address_label = new JLabel("Address");
		patient_address_label.setFont(MainWindow.getFont());
		patientAddress.setText("");
		add(patient_address_label);
		add(patientAddress);

		/* Patient phone */
		JLabel patient_phone_label = new JLabel("Phone");
		patient_phone_label.setFont(MainWindow.getFont());
		patientPhone.setText("");
		add(patient_phone_label);
		add(patientPhone);

		/* Patient birthdate */
		JLabel patient_bday_label = new JLabel("Birthday");
		patient_bday_label.setFont(MainWindow.getFont());
		add(patient_bday_label);
		SpinnerDateModel sdatemod = new SpinnerDateModel();
		patientBday = new JSpinner(sdatemod);
		patientBday
				.setEditor(new JSpinner.DateEditor(patientBday, "dd/MM/yyyy"));
		add(patientBday);

		add(new JPanel());
		add(new JPanel());

		// Save button
		JPanel button1 = new JPanel();
		BorderLayout border1 = new BorderLayout();
		button1.setLayout(border1);
		button1.add(back_newPatient, BorderLayout.WEST);
		add(button1);

		JPanel button = new JPanel();
		BorderLayout border = new BorderLayout();
		button.setLayout(border);
		button.add(save_newPatient, BorderLayout.EAST);
		add(button);

		add(new JPanel());
		add(new JPanel());
	}

	private void renderNotAllowedTests() {
		super.setLayout(new BorderLayout());

		super.setBorder(MainWindow.getBorder("Tests Not Allowed"));
		
		JScrollPane scroll = new JScrollPane(testNotAllowed);
		Vector<String> allTests;
		if (Database.isConnected()) {
			allTests = Database.getTests();
		} else {
			allTests = null;
		}
		scroll.setSize(scroll.getWidth(), 300);
		testNotAllowed.setListData(allTests);
		add(scroll, BorderLayout.CENTER);
		
		try {
			Statement stmt = Database.getConnection().createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			int selected_row = results.getSelectedRow();
			String ID = (String) results.getModel().getValueAt(selected_row, 0);
			
			ResultSet rset = stmt.executeQuery("SELECT t.test_name FROM test_type t, not_allowed n WHERE n.health_care_no = \'"+ID.toString()+"\' "
					+"AND n.type_id = t.type_id");
			
			//Get number of rows
			rset.last();
			int rows = rset.getRow();
			rset.beforeFirst();
			int count = 0;
			
			//Set selected items
			int[] selectedIndexes = new int[rows];
			while(rset.next()){
				for(int i = 0; i < allTests.size(); i++){
					if(rset.getString(1).equals(allTests.get(i))){
						selectedIndexes[count] = i;
						count++;
					}
				}
			}
			testNotAllowed.setSelectedIndices(selectedIndexes);

			stmt.close();

		} catch (SQLException e) {
			System.out.println(e);
		}

		JPanel buttons = new JPanel();
		buttons.setLayout(new BorderLayout());
		buttons.add(saveNotAllowed, BorderLayout.EAST);
		buttons.add(backNotAllowed, BorderLayout.WEST);
		add(buttons, BorderLayout.SOUTH);
	}

	private boolean saveInfo() {
		try {
			Statement stmt = Database.getConnection().createStatement();
			java.sql.Date sqldate = new java.sql.Date(
					((java.util.Date) patientBday.getValue()).getTime());
			// Execute the Update
			// System.out.println(sqldate.toString());
			int rows = stmt.executeUpdate("UPDATE patient SET name = '"
					+ patientName.getText() + "', address='"
					+ patientAddress.getText() + "', birth_day=to_date('"
					+ sqldate.toString() + "','YYYY-MM-DD'), phone = '"
					+ patientPhone.getText() + "' WHERE health_care_no = "
					+ patientID.getText());

			stmt.close();
			return true;
		} catch (SQLException e) {
			System.out.println(e);

			return false;
		}

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == search) {
			super.removeAll();
			renderResults();
			updateUI();
		} else if (e.getSource() == back) {
			super.removeAll();
			renderSearch();
			updateUI();
		} else if (e.getSource() == next) {
			super.removeAll();
			renderPatientInformation();
			updateUI();
		} else if (e.getSource() == back_patient) {
			super.removeAll();
			renderResults();
			updateUI();
		} else if (e.getSource() == notAllowedTestsButton) {
			super.removeAll();
			renderNotAllowedTests();
			updateUI();
		} else if (e.getSource() == backNotAllowed) {
			super.removeAll();
			renderResults();
			updateUI();
		} else if (e.getSource() == back_newPatient) {
			super.removeAll();
			renderSearch();
			updateUI();
		} else if (e.getSource() == saveNotAllowed) {
			try{
				Statement stmt = Database.getConnection().createStatement();

				// Execute the Update
				// System.out.println(sqldate.toString());
				int selected_row = results.getSelectedRow();
				String ID = (String) results.getModel().getValueAt(selected_row, 0);
				
				//Delete all entries for the current user - flush and reinput after
				stmt.executeUpdate("DELETE FROM not_allowed WHERE health_care_no = \'"+ID.toString()+"\'");
				
				//Insert a new entry for each selected test
				Object[] selectedValues = testNotAllowed.getSelectedValues();
				
				for(int i = 0; i < selectedValues.length; i++){
					//Get type id
					ResultSet rset = stmt.executeQuery("SELECT type_id FROM test_type WHERE test_name = \'"+(String)selectedValues[i]+"\'");
					rset.next();
					int type_id = rset.getInt(1);
					System.out.println((String)selectedValues[i]+": "+type_id);
					
					//Add new entry into DB
					stmt.executeUpdate("INSERT INTO not_allowed VALUES (\'"+ID.toString()+"\',\'"+type_id+"\')");
				}

				stmt.close();
				
				JOptionPane.showMessageDialog(this, "Patient Tests Not Allowed Changed",
						"Success", JOptionPane.INFORMATION_MESSAGE);
			}
			catch(Exception e1){
				System.out.println(e1);
			}
		}

		else if (e.getSource() == save_newPatient) {

			if (Database.PatientUnique(patientNo.getText())) {

				Vector<String> CannotDo = new Vector<String>();

				java.util.Date utilDate = (java.util.Date) patientBday
						.getValue();
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

				boolean success = Database.insertNewPatient(
						patientNo.getText(), patientName.getText(),
						patientAddress.getText(), patientPhone.getText(),
						sqlDate, CannotDo);

				if (success) {
					JOptionPane.showMessageDialog(this, "Patient Added",
							"Success", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this,
							"Patient Was Unable to be Added", "Success",
							JOptionPane.INFORMATION_MESSAGE);
				}

			} else {
				JOptionPane.showMessageDialog(this, "Patient Not Unique",
						"Error", JOptionPane.ERROR_MESSAGE);
			}

		} else if (e.getSource() == newPatient) {
			super.removeAll();
			renderNewPatient();
			updateUI();
		} else if (e.getSource() == save) {

			boolean saved = saveInfo();
			if (saved) {
				JOptionPane.showMessageDialog(this,
						"Patient Information Saved", "Success",
						JOptionPane.INFORMATION_MESSAGE);
			} else
				JOptionPane.showMessageDialog(this,
						"Patient Information NOT Saved!", "Failure",
						JOptionPane.ERROR_MESSAGE);

		}
	}
}
