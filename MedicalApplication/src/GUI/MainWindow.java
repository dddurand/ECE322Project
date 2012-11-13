package GUI;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.border.Border;

import Database.Database;

public class MainWindow extends WindowAdapter {

	/* SWITCHING THIS ON BYPASSES DATABASE CONNECTION */
	public static final boolean DEBUG = false;

	public static final Font FONT = new Font(null, Font.PLAIN, 12);

	private static final String WINDOW_NAME = "CMPUT 291 Project";

	private final JFrame window;
	private final Database database;

	public MainWindow(Database database) {
		this.database = database;

		window = new JFrame(WINDOW_NAME);
		window.setName(WINDOW_NAME);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		MainMenu mainMenu = new MainMenu(database);
		mainMenu.setOpaque(true);
		mainMenu.setName("main_menu_frame");
		window.setContentPane(mainMenu);

		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		window.addWindowListener(this);
	}

	public void windowClosing(WindowEvent arg0) {
		if (database.isConnected()) {
			database.closeDatabase();
		}
	}

	/*
	 * Here is defined a border style that will be consistently used throughout the GUI.
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
}
