import Database.Database;
import GUI.MainWindow;

public class Main {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		String url = "jdbc:mysql://mysql17.000webhost.com/a8095807_ece322";
		String driverName = "com.mysql.jdbc.Driver";
		String userName = "a8095807_ece322";
		String pass = "Bewareofbugs1";
		String tablePrex = "";
		Database database = new Database(url, driverName, userName, pass, tablePrex);
		// database.connectToDatabase();
		new MainWindow(database);
	}
}
