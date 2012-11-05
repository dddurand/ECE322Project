package GUI;

import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;
import javax.swing.border.Border;

import Database.Database;

public class MainWindow implements WindowListener {

	/* SWITCHING THIS ON BYPASSES DATABASE CONNECTION */
	public static final boolean DEBUG = false;

	private JFrame window;

	private final String windowName = "CMPUT 291 Project";

	public MainWindow() {
		window = new JFrame(windowName);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		MainMenu mainMenu = new MainMenu();
		mainMenu.setOpaque(true);
		window.setContentPane(mainMenu);

		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);

		window.addWindowListener(this);
	}

	/*
	 * Here is defined a border style that will be consistently used throughout
	 * the GUI.
	 * 
	 * @param: String title set the title of the titled border
	 */
	public static Border getBorder(String title) {
		Border emptyBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border border = BorderFactory.createEtchedBorder();
		border = BorderFactory.createTitledBorder(border, title);
		border = BorderFactory.createCompoundBorder(emptyBorder, border);
		border = BorderFactory.createCompoundBorder(border, emptyBorder);

		return border;
	}

	/*
	 * Here is defined the font that will be consistently used throughout the
	 * GUI.
	 */
	public static Font getFont() {
		Font font = new Font(null, Font.PLAIN, 12);

		return font;
	}

	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowClosed(WindowEvent arg0) {

	}

	public void windowClosing(WindowEvent arg0) {
		if (Database.isConnected())
			Database.closeDatabase();
	}

	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}
}
