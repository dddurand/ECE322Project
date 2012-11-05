package Database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import Identifiers.PatientIdentifier;
import Identifiers.Search1Identifier;
import Identifiers.Search2Identifier;
import Identifiers.Search3Identifier;
import Identifiers.TestResults;
import Identifiers.identifier;

public class Database {

	// Dustin Added
	private static String url = "jdbc:mysql://mysql17.000webhost.com/a8095807_ece322";
	private static String driverName = "com.mysql.jdbc.Driver";
	private static String userName = "a8095807_ece322";
	private static String pass = "Bewareofbugs1";
	private static Connection DConnection;
	private static boolean connectionStatus = false;
	private static String TablePrex = "";

	public static void closeDatabase() {
		try {
			Database.DConnection.close();
			Database.connectionStatus = false;
		} catch (SQLException e) {
			System.out.println("Error closing database");
			e.printStackTrace();
		}

	}

	// Dustin Added
	public static boolean connectToDatabase() {

		Class drvClass = null;
		try {
			drvClass = Class.forName(Database.driverName);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			System.out.println("Issue with oracle driver @ drvclass");
			e1.printStackTrace();
			Database.connectionStatus = false;
		}
		try {
			DriverManager.registerDriver((Driver) drvClass.newInstance());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to connect to database");
			Database.connectionStatus = false;
		}
		Connection m_con = null;
		try {
			m_con = DriverManager.getConnection(Database.url,
					Database.userName, Database.pass);
		} catch (SQLException e) {
			System.out.println("Unable to connect to database");
			e.printStackTrace();
			Database.connectionStatus = false;
		}

		if (m_con == null) {
			Database.connectionStatus = false;
		} else {
			Database.connectionStatus = true;
		}

		Database.DConnection = m_con;

		return Database.connectionStatus;

	}

	// Dustin Added
	public static void setUserName(String username) {
		Database.userName = username;
	}

	public static void setTablePrefix(String Prefix) {

		if (Prefix.equals("") || Prefix == null) {
			Database.TablePrex = "";
		} else
			Database.TablePrex = Prefix + ".";
	}

	// Dustin Added
	public static void setUserpass(String password) {
		Database.pass = password;
	}

	public static void setUserUrl(String url) {

		Database.url = url;
	}

	public static Connection getConnection() {

		return Database.DConnection;
	}

	public static boolean insertNewPatient(String HCN, String Name,
			String Address, String Phone, Date Birthdate,
			Vector<String> CannotDo) {

		try {

			// patient(health_care_no, name, address,birth_day, phone)
			String InsertRowSql = "INSERT INTO patient VALUES(?,?,?,?,?)";

			PreparedStatement pstmt = Database.DConnection
					.prepareStatement(InsertRowSql);
			pstmt.setString(1, HCN);
			pstmt.setString(2, Name);
			pstmt.setString(3, Address);
			pstmt.setDate(4, (java.sql.Date) Birthdate);
			pstmt.setString(5, Phone);

			pstmt.executeUpdate();
			pstmt.close();

			// not_allowed(health_care_no,type_id)
			if (CannotDo.size() > 0) {

				for (int i = 0; i < CannotDo.size(); i++) {
					Statement stmt = Database.getConnection().createStatement();
					InsertRowSql = "INSERT INTO not_allowed VALUES(" + HCN
							+ "," + CannotDo.get(i) + ")";
					int rows = stmt.executeUpdate(InsertRowSql);
					stmt.close();
				}
				InsertRowSql += ")";

			}
			return true;

		} catch (Exception e) {

			System.out.println("Error Inserting Prescription");
			e.printStackTrace();
			return false;
		}

	}

	public static void insertPrescription(identifier doctor,
			identifier patient, identifier test) {

		try {

			String testId = getUniqueTestRecordId();
			String typeId = test.getId();
			String HealthCareNo = patient.getId();
			String doctorId = doctor.getId();
			java.sql.Date sqlDate = new java.sql.Date(new java.util.Date()
					.getTime());

			String InsertRowSql = "INSERT INTO test_record VALUES(?,?,?,?,NULL,NULL,?,NULL)";

			PreparedStatement pstmt = Database.DConnection
					.prepareStatement(InsertRowSql);
			pstmt.setString(1, testId);
			pstmt.setString(2, typeId);
			pstmt.setString(3, HealthCareNo);
			pstmt.setString(4, doctorId);
			pstmt.setDate(5, sqlDate);

			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {

			System.out.println("Error Inserting Prescription");
			e.printStackTrace();
		}

	}

	/*
	 * Function that checks if patient can have test performed.
	 */
	public static boolean patientCanHaveTest(identifier patient, identifier test) {

		try {
			Statement stmt = Database.DConnection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rset = stmt
					.executeQuery("select health_care_no from "
							+ Database.TablePrex
							+ "not_allowed where health_care_no ="
							+ patient.getId() + " and (type_id = "
							+ test.getId() + ")");

			boolean RowsExist = rset.last();

			return !RowsExist;
		} catch (Exception e) {
			System.out.println("Exeception @ patient test ");
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * Check whether application is connected to DB or not.
	 */
	public static boolean isConnected() {
		return Database.connectionStatus;
	}

	// Code Dump - Will clean up later

	public static String getUniqueTestRecordId() {
		try {
			Statement stmt = Database.DConnection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rset = stmt.executeQuery("select test_id from "
					+ Database.TablePrex + "test_record");

			String testId = null;
			boolean unique = true;

			rset.last();
			int rowsize = rset.getRow() + 1;
			rset.beforeFirst();

			for (int i = 0; testId == null; i++) {
				unique = true;
				for (int j = 1; unique && j < rowsize; j++) {
					rset.absolute(j);

					if (rset.getInt(1) == i) {
						unique = false;
						break;
					}
				}
				if (unique)
					testId = (new Integer(i)).toString();

			}

			stmt.close();

			return testId;

		} catch (Exception e) { // Dump - Will clean up later

			System.out.println("Queries Failed");
			return null;
		}

	}

	// UPDATED BY DUSTIN NOV 7
	public static Vector<identifier> partialNameQuery(String partialQuery,
			char mode) {
		try {
			Statement stmt = Database.DConnection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rset;

			switch (mode) {
			case 'p':
				rset = stmt.executeQuery("select health_care_no, name from "
						+ Database.TablePrex + "patient where (name like '%"
						+ partialQuery + "%' OR health_care_no like '%"
						+ partialQuery + "%')");
				break;
			case 'd':
				rset = stmt
						.executeQuery("select d.employee_no, p.name from "
								+ Database.TablePrex
								+ "patient p, "
								+ Database.TablePrex
								+ "doctor d where d.health_care_no = p.health_care_no and (p.name like '%"
								+ partialQuery + "%' OR d.employee_no like '%"
								+ partialQuery + "%') ");
				break;
			case 't':
				rset = stmt.executeQuery("select type_id, test_name from "
						+ Database.TablePrex
						+ "test_type where test_name like '%" + partialQuery
						+ "%'");
				break;
			case 'l':
				rset = stmt.executeQuery("select phone, lab_name from "
						+ Database.TablePrex
						+ "medical_lab where lab_name like '%" + partialQuery
						+ "%'");
				break;

			default:
				System.out
						.println("Error @ Parital Name Query -- Invalid Mode");
				return null;

			}

			Vector<identifier> results = new Vector<identifier>();

			int colNum = rset.getMetaData().getColumnCount();

			while (rset.next()) {

				results.addElement(new identifier(rset.getString(1), rset
						.getString(2), mode));

			}

			stmt.close();

			return results;
		}

		catch (Exception e) {
			System.out.println("Query Failed");
			return null;
		}

	}

	// UPDATED BY DUSTIN NOV 7 -- Not Complete -- Change to TestResult Class
	public static Vector<TestResults> TestRecordSearch(String PatientId,
			String doctorId, String testID, String RecordID) {
		try {
			Statement stmt = Database.DConnection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rset;

			System.out.println("PID: " + PatientId + "\nDID: " + doctorId
					+ "\nTID: " + testID + "\nRID: " + RecordID);

			String query = "select DISTINCT r.test_id, r.patient_no, p.name, d.employee_no, p2.name, "
					+ "t.type_id, t.test_name, r.medical_lab, r.prescribe_date from "
					+ Database.TablePrex
					+ "test_record r,"
					+ Database.TablePrex
					+ "test_type t, "
					+ ""
					+ Database.TablePrex
					+ "patient p, "
					+ Database.TablePrex
					+ "doctor d, "
					+ Database.TablePrex
					+ "patient p2 where (r.type_id = t.type_id and p.health_care_no = r.patient_no and p2.health_care_no = d.health_care_no "
					+ "and d.employee_no = r.employee_no ";

			if (!RecordID.equals("")) {
				query = query + " AND r.test_id = '" + RecordID + "' ";

			}

			if (!PatientId.equals("")) {
				query = query + " AND r.patient_no = '" + PatientId + "' ";

			}

			if (!testID.equals("")) {
				query = query + " AND r.type_id = '" + testID + "' ";

			}

			if (!doctorId.equals("")) {
				query = query + " AND r.employee_no = '" + doctorId + "' ";

			}

			query = query + ")";

			rset = stmt.executeQuery(query);

			/*
			 * rset = stmt.executeQuery(
			 * "select DISTINCT r.test_id, r.patient_no, p.name, d.employee_no, p2.name, "
			 * + "t.type_id, t.test_name, r.medical_lab, r.prescribe_date from "
			 * + Database.TablePrex + "test_record r," + Database.TablePrex +
			 * "test_type t, " + "" + Database.TablePrex + "patient p, " +
			 * Database.TablePrex + "doctor d, " + Database.TablePrex +
			 * "patient p2 where (r.test_id = '" + RecordID +
			 * "' OR r.patient_no = '" + PatientId + "' OR r.employee_no = '" +
			 * doctorId + "' OR r.type_id = '" + testID + "') " +
			 * "and r.type_id = t.type_id and p.health_care_no = r.patient_no and p2.health_care_no = d.health_care_no "
			 * + "and d.employee_no = r.employee_no ");
			 */
			Vector<TestResults> results = new Vector<TestResults>();

			int colNum = rset.getMetaData().getColumnCount();

			while (rset.next()) {

				results.addElement(new TestResults(rset.getString(1), rset
						.getString(2), rset.getString(3), rset.getString(4),
						rset.getString(5), rset.getString(6),
						rset.getString(7), rset.getString(8), rset.getDate(9)));

			}

			stmt.close();

			return results;
		}

		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Test results query failed");
			return null;
		}

	}

	public static Vector<PatientIdentifier> patientQuery(String patientNameOrId) {
		try {

			Statement stmt = Database.DConnection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rset;

			try {
				Integer.parseInt(patientNameOrId);
				rset = stmt
						.executeQuery("select health_care_no, name,address,birth_day,phone from "
								+ Database.TablePrex
								+ "patient where health_care_no ='"
								+ patientNameOrId + "'");
			} catch (NumberFormatException nFE) {
				rset = stmt
						.executeQuery("select health_care_no, name,address,birth_day,phone from "
								+ Database.TablePrex
								+ "patient where name like '%"
								+ patientNameOrId + "%'");
			}

			Vector<PatientIdentifier> results = new Vector<PatientIdentifier>();

			// int colNum = rset.getMetaData().getColumnCount();

			while (rset.next()) {

				results.addElement(new PatientIdentifier(rset
						.getString("health_care_no"), rset.getString("name"),
						rset.getString("address"), rset.getDate(4), rset
								.getString("phone")));

			}

			stmt.close();

			return results;
		}

		catch (Exception e) {
			System.out
					.println("Statement could not be created, may have to set debug = false");
			return null;
		}

	}

	public static Vector searchPane(int mode, Vector searchItems) {
		try {
			Statement stmt = Database.DConnection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rset;

			switch (mode) {
			/*
			 * searchItems[0] = patient name searchItems[1] = test name
			 */
			case 1: {
				String query = "SELECT p.name, t.test_name, r.test_date, r.result FROM "
						+ Database.TablePrex
						+ "patient p, "
						+ Database.TablePrex
						+ "test_type t, "
						+ Database.TablePrex
						+ "test_record r WHERE "
						+ "r.type_id = t.type_id AND r.patient_no = p.health_care_no AND (t.test_name LIKE '%"
						+ searchItems.get(1)
						+ "%' AND p.name LIKE '%"
						+ searchItems.get(0) + "%')";

				rset = stmt.executeQuery(query);

				Vector<Search1Identifier> results = new Vector<Search1Identifier>();

				while (rset.next()) {

					results
							.addElement(new Search1Identifier(rset
									.getString("name"), rset
									.getString("test_name"), rset
									.getDate("test_date"), rset
									.getString("result")));

				}

				return results;
			}

				/*
				 * searchItems[0] = doctor name or Employee ID searchItems[1] =
				 * start date searchItems[2] = end date
				 */
			case 2: {
				java.sql.Date startDate = new java.sql.Date(
						((java.util.Date) searchItems.get(1)).getTime());
				java.sql.Date endDate = new java.sql.Date(
						((java.util.Date) searchItems.get(2)).getTime());

				String query = "SELECT p.name, t.test_name, r.prescribe_date FROM "
						+ Database.TablePrex
						+ "patient p, "
						+ Database.TablePrex
						+ "patient p2, "
						+ Database.TablePrex
						+ "doctor d, "
						+ Database.TablePrex
						+ "test_type t, "
						+ Database.TablePrex
						+ "test_record r WHERE "
						+ "r.type_id = t.type_id AND r.patient_no = p.health_care_no AND d.employee_no = r.employee_no AND d.health_care_no = p2.health_care_no "
						+ "AND (p2.name LIKE '%"
						+ searchItems.get(0)
						+ "%' OR d.employee_no LIKE '%"
						+ searchItems.get(0).toString()
						+ "%') AND r.prescribe_date >= to_date('"
						+ startDate.toString()
						+ "', 'YYYY-MM-DD') "
						+ "AND r.prescribe_date <= to_date('"
						+ endDate.toString() + "', 'YYYY-MM-DD')";

				rset = stmt.executeQuery(query);

				Vector<Search2Identifier> results = new Vector<Search2Identifier>();

				while (rset.next()) {

					results.addElement(new Search2Identifier(rset
							.getString("name"), rset.getString("test_name"),
							rset.getDate("prescribe_date")));

				}

				return results;
			}

				/*
				 * searchItems[0] = test name
				 */
			case 3: {
				try{
				rset = stmt.executeQuery("DROP VIEW medical_risk");
				} catch (Exception f){
					//Continue;
				}
				
				String query = "CREATE VIEW medical_risk (medical_type,\"abnormal rate\", \"alarming age\") AS "
						+ "SELECT t.type,  r.ab_rate, min(t.age) "
						+ "FROM  (SELECT  t1.type_id type,count(distinct t2.patient_no)/count(distinct t1.patient_no) ab_rate "
						+ "FROM     "
						+ Database.TablePrex
						+ "test_record t1, "
						+ Database.TablePrex
						+ "test_record t2 "
						+ "WHERE    t1.type_id = t2.type_id AND t2.result <> 'normal' AND t1.result IS NOT NULL "
						+ "GROUP BY t1.type_id "
						+ ") r,"
						+ "(SELECT t1.type_id type, p.age, "
						+ "COUNT(DISTINCT t2.patient_no) / COUNT(DISTINCT t3.patient_no) n_ab_rate "
						+ "FROM   ( SELECT  DISTINCT trunc(months_between(sysdate,birth_day)/12) age "
						+ "FROM "
						+ Database.TablePrex
						+ "patient "
						+ ") p, "
						+ ""
						+ Database.TablePrex
						+ "test_record t1, "
						+ Database.TablePrex
						+ "test_record t2, "
						+ Database.TablePrex
						+ "patient p2, "
						+ ""
						+ Database.TablePrex
						+ "test_record t3, "
						+ Database.TablePrex
						+ "patient p3 "
						+ "WHERE  t1.type_id = t2.type_id AND "
						+ "t1.type_id = t3.type_id AND "
						+ "t2.result<>'normal' AND t1.result IS NOT NULL AND "
						+ "p2.health_care_no = t2.patient_no AND "
						+ "trunc(months_between(sysdate,p2.birth_day)/12)> age AND "
						+ "p3.health_care_no = t3.patient_no AND "
						+ "trunc(months_between(sysdate, p3.birth_day)/12) >= age "
						+ "GROUP BY t1.type_id, p.age "
						+ ") t "
						+ "WHERE  t.type = r.type  AND t.n_ab_rate >= 2 * r.ab_rate "
						+ "GROUP  BY t.type, r.ab_rate";

				rset = stmt.executeQuery(query);

				String query2 = "SELECT name, address, phone "
						+ "FROM   "
						+ Database.TablePrex
						+ "patient p, "
						+ "medical_risk m "
						+ "WHERE  trunc(months_between(sysdate,birth_day)/12) >= m.\"alarming age\" "
						+ "AND m.medical_type LIKE '%" + searchItems.get(0)
						+ "%' "
						+ "AND p.health_care_no NOT IN (SELECT patient_no "
						+ "FROM   " + Database.TablePrex + "test_record t "
						+ "WHERE  m.medical_type = t.type_id" + ")";

				rset = stmt.executeQuery(query2);

				Vector<Search3Identifier> results = new Vector<Search3Identifier>();

				while (rset.next()) {

					results.addElement(new Search3Identifier(rset
							.getString("name"), rset.getString("address"), rset
							.getString("phone")));

				}

				return results;
			}
			}

			stmt.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		return null;
	}

	public static boolean CanConduct(String Lab, String Test) {

		try {
			Statement stmt = Database.DConnection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rset = stmt.executeQuery("select * from "
					+ Database.TablePrex + "can_conduct where lab_name ='"
					+ Lab + "' and type_id = '" + Test + "'");

			boolean RowsExist = rset.last();

			return RowsExist;
		} catch (Exception e) {
			System.out.println("Exeception @ CanConduct ");
			e.printStackTrace();
			return false;
		}

	}

	public static boolean PatientUnique(String Health_care_no) {

		try {
			Statement stmt = Database.DConnection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rset = stmt.executeQuery("select * from "
					+ Database.TablePrex + "patient where health_care_no ="
					+ Health_care_no);

			boolean RowsExist = rset.last();

			return !RowsExist;
		} catch (Exception e) {
			System.out.println("Exeception @ UniquePatient test ");
			e.printStackTrace();
			return false;
		}

	}

	public static Vector<String> getTests() {
		try {
			Statement stmt = Database.DConnection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rset = stmt.executeQuery("select test_name from "
					+ Database.TablePrex + "test_type");

			Vector<String> results = new Vector<String>();

			while (rset.next()) {
				results.addElement(rset.getString("test_name"));
			}
			
			return results;

		} catch (Exception e) {
			System.out.println("Exeception @ getTests");
			e.printStackTrace();
			return null;
		}
	}

}
