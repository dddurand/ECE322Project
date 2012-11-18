package Database.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Database.Database;
import Identifiers.Identifier;

public class DatabaseTest {

	private Database db;

	@Before
	public void setUpBeforeClass() throws Exception {
		String url = "jdbc:mysql://sql2.freesqldatabase.com/sql2646";
		String driverName = "com.mysql.jdbc.Driver";
		String userName = "sql2646";
		String pass = "kR7%tR3!";
		String tablePrex = "";
		db = new Database(url, driverName, userName, pass, tablePrex);
		db.connectToDatabase();
	}

	@After
	public void tearDownAfterClass() throws Exception {
		db.closeDatabase();
	}

	@Test
	public void testIsConnected() {
		assertTrue(db.isConnected());
	}

	@Test
	public void testGetUniqueTestRecordId() {
		assertEquals("1", db.getUniqueTestRecordId());
	}

	@Test
	public void testGetConnection() {
		Connection connection = db.getConnection();
		assertNotNull(connection);
	}

	@Test
	public void testInsertNewPatient() throws SQLException {
		Vector<String> cannotDo = new Vector<String>();
		cannotDo.add("101");
		@SuppressWarnings("deprecation")
		boolean result = db.insertNewPatient("7984551", "UnitTestName", "UnitTestAddr", "78412",
				new Date(1990, 10, 10), cannotDo);
		assertTrue(result);
		// Test that it is there
		ResultSet countPatients = db.getConnection().createStatement()
				.executeQuery("SELECT COUNT(*) FROM patient WHERE health_care_no = 7984551");
		countPatients.first();
		assertEquals(1, countPatients.getInt(1));

		ResultSet countPrescriptions = db.getConnection().createStatement()
				.executeQuery("SELECT COUNT(*) FROM not_allowed WHERE health_care_no = 7984551");
		countPrescriptions.first();
		assertEquals(1, countPrescriptions.getInt(1));

		// Clean up
		db.getConnection().createStatement()
				.execute("DELETE FROM patient WHERE health_care_no = 7984551");
		db.getConnection().createStatement()
				.execute("DELETE FROM not_allowed WHERE health_care_no = 7984551");
	}

	@Test
	public void testInsertPrescription() throws SQLException {
		Identifier doctor = new Identifier("8412", "doc", 'd');
		Identifier patient = new Identifier("1533", "patient", 'p');
		Identifier test = new Identifier("101", "test", 't');

		boolean result = db.insertPrescription(doctor, patient, test);
		assertTrue(result);

		// Test that it is there
		ResultSet countTests = db.getConnection().createStatement()
				.executeQuery("SELECT COUNT(*) FROM test_record WHERE test_id = 1");
		countTests.first();
		assertEquals(1, countTests.getInt(1));

		// Clean up
		db.getConnection().createStatement().execute("DELETE FROM test_record WHERE test_id = 1");
	}

	@Test
	public void testPatientCanHaveTest() throws SQLException {
		Identifier patient = new Identifier("1533", "patient", 'p');
		Identifier test = new Identifier("101", "test", 't');

		boolean result = db.patientCanHaveTest(patient, test);
		assertTrue(result);
	}
}
