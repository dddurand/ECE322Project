package GUIPanes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import Database.Database;
import GUI.MainMenu;
import GUI.MainWindow;

@SuppressWarnings("serial")
public class Settings extends JPanel implements ActionListener {

	protected JTextField url;
	protected JTextField TablePrefix;
	protected JTextField user;
	protected JPasswordField password;
	protected JButton connect;
	protected JButton dconnect;
	protected MainMenu menu;

	protected final String DEFAULT_URL = "jdbc:mysql://sql2.freesqldatabase.com/sql2646";
	protected final String DEFAULT_USER = "sql2646";
	protected final String DEFAULT_PASS = "kR7%tR3!";

	public Settings(MainMenu Mmenu) {
		menu = Mmenu; // Dirty hack, just to have access to menu methods used in
						// connection

		this.setName("Settings");
		
		GridLayout layout = new GridLayout(8, 2);
		layout.setVgap(5);
		this.setLayout(layout);

		super.setBorder(MainWindow.getBorder("Connection Settings"));

		JLabel url_label = new JLabel("Server Address");
		url_label.setFont(MainWindow.getFont());
		url = new JTextField(DEFAULT_URL);
		add(url_label);
		add(url);

		JLabel TablePre_label = new JLabel("Table Prefix");
		TablePre_label.setFont(MainWindow.getFont());
		TablePrefix = new JTextField();
		add(TablePre_label);
		add(TablePrefix);

		JLabel user_label = new JLabel("Database User");
		user_label.setFont(MainWindow.getFont());
		user = new JTextField(DEFAULT_USER);
		add(user_label);
		add(user);

		JLabel password_label = new JLabel("Password");
		password_label.setFont(MainWindow.getFont());
		password = new JPasswordField(DEFAULT_PASS);
		add(password_label);
		add(password);

		add(new JPanel());

		connect = new JButton("Connect");
		connect.setFont(MainWindow.getFont());
		connect.addActionListener(this);

		JPanel button = new JPanel();
		BorderLayout border = new BorderLayout();
		button.setLayout(border);
		button.add(connect, BorderLayout.EAST);
		add(button);

		dconnect = new JButton("Disconnect");
		dconnect.setFont(MainWindow.getFont());
		dconnect.addActionListener(this);

		button.add(dconnect, BorderLayout.CENTER);
		dconnect.setEnabled(false);
		add(button);

		add(new JPanel());
		add(new JPanel());

		add(new JPanel());
		add(new JPanel());

		add(new JPanel());
		add(new JPanel());
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == connect) {
			// Send GUI input to database
			Database.setUserName(user.getText());
			Database.setUserpass(password.getText());
			Database.setUserUrl(url.getText());
			Database.setTablePrefix(TablePrefix.getText());

			// Attempt to connect
			Database.connectToDatabase();

			// If connected Proceed with changes
			if (Database.isConnected()) {

				// Update Buttons for full functionality
				TablePrefix.setEnabled(false);
				connect.setEnabled(false);
				url.setEnabled(false);
				user.setEnabled(false);
				password.setEnabled(false);
				menu.enableAllButtons();
				dconnect.setEnabled(true);
				password.setText(null);

				// Update Footer On Connect
				menu.footer_label.setText("Connected");
				menu.footer_label.setForeground(Color.GREEN);
				menu.footer.updateUI();

			} else {
				JOptionPane.showMessageDialog(this,
						"Unable to connect. Try again.", "Connection Error",
						JOptionPane.ERROR_MESSAGE);
				password.setText(null);
			}
		}

		// Disabling connection
		if (e.getSource() == dconnect) {
			if (Database.isConnected()) {
				Database.closeDatabase();

				if (!Database.isConnected()) {
					// Changing GUI to only allow connection screen
					dconnect.setEnabled(false);
					TablePrefix.setEnabled(true);
					connect.setEnabled(true);
					url.setEnabled(true);
					user.setEnabled(true);
					password.setEnabled(true);
					menu.OnlyAllowConnect();

					// Update Footer to Not Connected
					menu.footer_label.setText("Not Connected");
					menu.footer_label.setForeground(Color.RED);
					menu.footer.updateUI();

				}
			}
		}
	}
}
