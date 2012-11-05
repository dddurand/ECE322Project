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

	protected JToggleButton prescription, medicalTest, patientInformation,
			search, settings;
	protected JPanel activeContent;

	protected JPanel prescription_pane, medical_test_pane, settings_pane,
			patient_pane, search_pane;

	public JLabel footer_label;
	public JPanel footer;

	public MainMenu() {
		super.setLayout(new BorderLayout());

		/* buttons */
		JPanel group = new JPanel();

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
		prescription_pane = new PrescriptionPane();
		medical_test_pane = new MedicalTestPane();
		settings_pane = new Settings(this);
		patient_pane = new PatientPane();
		search_pane = new SearchPane();

		activeContent = settings_pane;

		/* footer */

		footer = new JPanel();
		footer_label = new JLabel("No Connection");
		footer_label.setForeground(Color.RED);
		footer_label.setFont(MainWindow.getFont());
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

		if (Database.isConnected()) {

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
