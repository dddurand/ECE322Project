package GUIPanes;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Database.Database;
import GUI.MainWindow;
import Identifiers.Identifier;
import Other.AutoCompleteJComboBox;

@SuppressWarnings("serial")
public class PrescriptionPane extends JPanel implements ActionListener {

	protected AutoCompleteJComboBox doc_id; // Doctor
	protected AutoCompleteJComboBox patient; // Patient
	protected AutoCompleteJComboBox test; // Test

	protected JButton prescribe; // Done button

	private final Database database;

	public PrescriptionPane(Database database) {
		this.database = database;

		GridLayout layout = new GridLayout(8, 2);
		layout.setVgap(5);
		super.setLayout(layout);

		super.setBorder(MainWindow.getBorder("Prescription"));

		// Doctor
		JLabel doctor_label = new JLabel("Employee");
		doctor_label.setFont(MainWindow.FONT);
		doc_id = new AutoCompleteJComboBox(database);
		doc_id.setMode('d');

		add(doctor_label);
		add(doc_id);

		// Patient
		JLabel patient_label = new JLabel("Patient");
		patient_label.setFont(MainWindow.FONT);
		patient = new AutoCompleteJComboBox(database);
		patient.setMode('p');
		add(patient_label);
		add(patient);

		// Test
		JLabel test_label = new JLabel("Test");
		test_label.setFont(MainWindow.FONT);
		test = new AutoCompleteJComboBox(database);
		add(test_label);
		add(test);
		test.setMode('t');

		// Blank space
		add(new JPanel());
		add(new JPanel());

		// Finish buttons
		prescribe = new JButton("Prescribe Test");
		prescribe.setFont(MainWindow.FONT);
		prescribe.addActionListener(this);
		add(new JPanel());
		JPanel button = new JPanel();
		BorderLayout border = new BorderLayout();
		button.setLayout(border);
		button.add(prescribe, BorderLayout.EAST);
		add(button);

		add(new JPanel());
		add(new JPanel());

		add(new JPanel());
		add(new JPanel());

		add(new JPanel());
		add(new JPanel());
	}

	public void actionPerformed(ActionEvent e) {
		if (!database.isConnected()) {
			JOptionPane.showMessageDialog(this,
					"Not connected to Database, please try again later", "Error",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		// Check if patient is an identifier, if fails a warning returns error and terminates event
		try {
			((Identifier) patient.getSelectedItem()).getId();
		} catch (Exception a) {
			JOptionPane.showMessageDialog(this, "Please correctly select patient",
					"Prescription Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Check if testID is an identifier, if fails a warning returns error and terminates event
		try {
			((Identifier) test.getSelectedItem()).getId();
		} catch (Exception b) {
			JOptionPane.showMessageDialog(this, "Please correctly select Test",
					"Prescription Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Check if DocID is an identifier, if fails a warning returns error and terminates event
		try {
			((Identifier) doc_id.getSelectedItem()).getId();
		} catch (Exception c) {
			JOptionPane.showMessageDialog(this, "Please correctly select Medical Employee",
					"Prescription Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Call to database to check if patient can particpate in test selected
		if (database.patientCanHaveTest((Identifier) patient.getSelectedItem(),
				(Identifier) test.getSelectedItem())) {
			// Call to database to create prescription
			database.insertPrescription((Identifier) doc_id.getSelectedItem(),
					(Identifier) patient.getSelectedItem(), (Identifier) test.getSelectedItem());
			// Success!
			JOptionPane.showMessageDialog(this, "Prescription Accepted", "Success",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			// Error patient cannot partipate in test
			JOptionPane.showMessageDialog(this, "Patient cannot do this test",
					"Prescription Error", JOptionPane.ERROR_MESSAGE);
		}

	}
}
