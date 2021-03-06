package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import Database.Database;
import GUIPanes.MedicalTestPane;
import GUIPanes.PatientPane;
import GUIPanes.PrescriptionPane;
import GUIPanes.SearchPane;
import GUIPanes.Settings;

@SuppressWarnings("serial")
public class MainMenu extends JPanel implements ActionListener {

	private final JToggleButton prescription, medicalTest, patientInformation, search, settings;
	private final JPanel prescription_pane, medical_test_pane, settings_pane, patient_pane,
			search_pane;

	private final Database database;

	private JPanel activeContent = null;

	public final JPanel footer;
	public final JLabel footer_label;

	public MainMenu(Database database) {
		this.database = database;
		super.setLayout(new BorderLayout());

		/* buttons */
		JPanel group = new JPanel();
		group.setName("main_group");

		prescription = new JToggleButton("Prescription");
		prescription.addActionListener(this);
		if (!MainWindow.DEBUG)
			prescription.setEnabled(false);

		medicalTest = new JToggleButton("Medical Test");
		medicalTest.addActionListener(this);
		if (!MainWindow.DEBUG)
			medicalTest.setEnabled(false);

		patientInformation = new JToggleButton("Patient Information");
		patientInformation.addActionListener(this);
		if (!MainWindow.DEBUG)
			patientInformation.setEnabled(false);

		search = new JToggleButton("Search");
		search.addActionListener(this);
		if (!MainWindow.DEBUG)
			search.setEnabled(false);

		settings = new JToggleButton("Connect");
		settings.setSelected(true);
		settings.addActionListener(this);

		/* content panes */
		prescription_pane = new PrescriptionPane(database);
		prescription_pane.setName("prescription_pane");

		medical_test_pane = new MedicalTestPane(database);
		medical_test_pane.setName("medical_test_pane");

		settings_pane = new Settings(this, database);
		settings_pane.setName("settings_pane");

		patient_pane = new PatientPane(database);
		patient_pane.setName("patient_pane");

		search_pane = new SearchPane(database);
		search_pane.setName("search_pane");

		activeContent = settings_pane;

		/* footer */

		footer = new JPanel();
		footer.setName("footer");
		footer_label = new JLabel("No Connection");
		footer_label.setForeground(Color.RED);
		footer_label.setFont(MainWindow.FONT);
		footer.add(footer_label);

		/* populate window */
		group.add(settings);
		group.add(prescription);
		group.add(medicalTest);
		group.add(patientInformation);
		group.add(search);

		add(group, BorderLayout.NORTH);
		add(activeContent, BorderLayout.CENTER);
		add(footer, BorderLayout.SOUTH);
	}

	public void actionPerformed(ActionEvent e) {
		this.disableAllButtons();
		((AbstractButton) e.getSource()).setSelected(true);

		this.remove(activeContent);

		if (e.getSource() == prescription)
			activeContent = prescription_pane;
		else if (e.getSource() == medicalTest)
			activeContent = medical_test_pane;
		else if (e.getSource() == settings)
			activeContent = settings_pane;
		else if (e.getSource() == patientInformation)
			activeContent = patient_pane;
		else if (e.getSource() == search)
			activeContent = search_pane;

		this.add(activeContent);

		if (database.isConnected()) {
			footer_label.setText("Connected");
			footer_label.setForeground(Color.GREEN);
			footer.updateUI();
		} else {
			footer_label.setText("Not Connected");
			footer_label.setForeground(Color.RED);
			footer.updateUI();
		}

		updateUI();
	}

	private void disableAllButtons() {
		prescription.setSelected(false);
		medicalTest.setSelected(false);
		patientInformation.setSelected(false);
		search.setSelected(false);
		settings.setSelected(false);
	}

	public void OnlyAllowConnect() {
		prescription.setEnabled(false);
		medicalTest.setEnabled(false);
		patientInformation.setEnabled(false);
		search.setEnabled(false);
	}

	/*
	 * Called once a successful connection is made from settings object
	 */
	public void enableAllButtons() {
		prescription.setEnabled(true);
		medicalTest.setEnabled(true);
		patientInformation.setEnabled(true);
		search.setEnabled(true);
		settings.setEnabled(true);
	}
}
