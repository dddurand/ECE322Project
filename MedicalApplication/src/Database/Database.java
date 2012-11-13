package Database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;

import Identifiers.Identifier;
import Identifiers.PatientIdentifier;
import Identifiers.Search1Identifier;
import Identifiers.Search2Identifier;
import Identifiers.Search3Identifier;
import Identifiers.SearchIdentifier;
import Identifiers.TestResults;

public class Database {

	private String url;
	private String driverName;
	private String userName;
	private String password;
	private String tablePrex;

	private boolean connectionStatus = false;
	private Connection connection;

	public Database() {

	}

	public Database(String url, String driverName, String userName, String password,
			String tablePrex) {
		this.url = url;
		this.driverName = driverName;
		this.userName = userName;
		this.password = password;
		this.tablePrex = tablePrex;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setTablePrex(String tablePrex) {
		this.tablePrex = tablePrex;
	}

	public boolean connectToDatabase() throws InstantiationException, IllegalAccessException {
		if (connectionStatus == true) {
			closeDatabase();
		}

		@SuppressWarnings("rawtypes")
		Class drvClass = null;
		try {
			drvClass = Class.forName(driverName);
		} catch (ClassNotFoundException e1) {
			System.out.println("Issue with driver @ drvclass");
			e1.printStackTrace();
			connectionStatus = false;
			return false;
		}
		try {
			DriverManager.registerDriver((Driver) drvClass.newInstance());
			connection = DriverManager.getConnection(url, userName, password);
		} catch (SQLException e) {
			System.out.println("Unable to connect to database");
			connectionStatus = false;
			return false;
		}

		connectionStatus = true;
		return connectionStatus;
	}

	public void closeDatabase() {
		try {
			connection.close();
			connectionStatus = false;
		} catch (SQLException e) {
			System.out.println("Error closing database");
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public boolean insertNewPatient(String HCN, String Name, String Address, String Phone,
			Date Birthdate, Vector<String> CannotDo) {
		try {
			// patient(health_care_no, name, address,birth_day, phone)
			String InsertRowSql = "INSERT INTO patient VALUES(?,?,?,?,?)";

			PreparedStatement pstmt = connection.prepareStatement(InsertRowSql);
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
					Statement stmt = connection.createStatement();
					InsertRowSql = "INSERT INTO not_allowed VALUES(" + HCN + "," + CannotDo.get(i)
							+ ")";
					stmt.executeUpdate(InsertRowSql);
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

	public boolean insertPrescription(Identifier doctor, Identifier patient, Identifier test) {
		try {
			String testId = getUniqueTestRecordId();
			String typeId = test.getId();
			String HealthCareNo = patient.getId();
			String doctorId = doctor.getId();
			java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());

			String InsertRowSql = "INSERT INTO test_record VALUES(?,?,?,?,NULL,NULL,?,NULL)";

			PreparedStatement pstmt = connection.prepareStatement(InsertRowSql);
			pstmt.setString(1, testId);
			pstmt.setString(2, typeId);
			pstmt.setString(3, HealthCareNo);
			pstmt.setString(4, doctorId);
			pstmt.setDate(5, sqlDate);

			pstmt.executeUpdate();
			pstmt.close();
			return true;
		} catch (Exception e) {
			System.out.println("Error Inserting Prescription");
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * Function that checks if patient can have test performed.
	 */
	public boolean patientCanHaveTest(Identifier patient, Identifier test) {
		try {
			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rset = stmt.executeQuery("select health_care_no from " + tablePrex
					+ "not_allowed where health_care_no =" + patient.getId() + " and (type_id = "
					+ test.getId() + ")");
			return !rset.last();
		} catch (Exception e) {
			System.out.println("Exeception @ patient test ");
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * Check whether application is connected to DB or not.
	 */
	public boolean isConnected() {
		return connectionStatus;
	}

	public String getUniqueTestRecordId() {
		try {
			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rset = stmt.executeQuery("select test_id from " + tablePrex + "test_record");

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
	public Vector<Identifier> partialNameQuery(String partialQuery, char mode) {
		try {
			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rset;

			switch (mode) {
			case 'p':
				rset = stmt.executeQuery("select health_care_no, name from " + tablePrex
						+ "patient where (name like '%" + partialQuery
						+ "%' OR health_care_no like '%" + partialQuery + "%')");
				break;
			case 'd':
				rset = stmt.executeQuery("select d.employee_no, p.name from " + tablePrex
						+ "patient p, " + tablePrex
						+ "doctor d where d.health_care_no = p.health_care_no and (p.name like '%"
						+ partialQuery + "%' OR d.employee_no like '%" + partialQuery + "%') ");
				break;
			case 't':
				rset = stmt.executeQuery("select type_id, test_name from " + tablePrex
						+ "test_type where test_name like '%" + partialQuery + "%'");
				break;
			case 'l':
				rset = stmt.executeQuery("select phone, lab_name from " + tablePrex
						+ "medical_lab where lab_name like '%" + partialQuery + "%'");
				break;
			default:
				System.out.println("Error @ Parital Name Query -- Invalid Mode");
				return null;
			}

			Vector<Identifier> results = new Vector<Identifier>();

			while (rset.next()) {
				results.addElement(new Identifier(rset.getString(1), rset.getString(2), mode));
			}
			stmt.close();
			return results;
		} catch (SQLException e) {
			System.out.println("Query Failed");
			return null;
		}
	}

	// UPDATED BY DUSTIN NOV 7 -- Not Complete -- Change to TestResult Class
	public Vector<TestResults> TestRecordSearch(String PatientId, String doctorId, String testID,
			String RecordID) {
		try {
			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rset;

			System.out.println("PID: " + PatientId + "\nDID: " + doctorId + "\nTID: " + testID
					+ "\nRID: " + RecordID);

			String query = "select DISTINCT r.test_id, r.patient_no, p.name, d.employee_no, p2.name, "
					+ "t.type_id, t.test_name, r.medical_lab, r.prescribe_date from "
					+ tablePrex
					+ "test_record r,"
					+ tablePrex
					+ "test_type t, "
					+ ""
					+ tablePrex
					+ "patient p, "
					+ tablePrex
					+ "doctor d, "
					+ tablePrex
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
			 * "select DISTINCT r.test_id, r.patient_no, p.name, d.employee_no, p2.name, " +
			 * "t.type_id, t.test_name, r.medical_lab, r.prescribe_date from " + Database.TablePrex
			 * + "test_record r," + Database.TablePrex + "test_type t, " + "" + Database.TablePrex +
			 * "patient p, " + Database.TablePrex + "doctor d, " + Database.TablePrex +
			 * "patient p2 where (r.test_id = '" + RecordID + "' OR r.patient_no = '" + PatientId +
			 * "' OR r.employee_no = '" + doctorId + "' OR r.type_id = '" + testID + "') " +
			 * "and r.type_id = t.type_id and p.health_care_no = r.patient_no and p2.health_care_no = d.health_care_no "
			 * + "and d.employee_no = r.employee_no ");
			 */
			Vector<TestResults> results = new Vector<TestResults>();

			while (rset.next()) {
				results.addElement(new TestResults(rset.getString(1), rset.getString(2), rset
						.getString(3), rset.getString(4), rset.getString(5), rset.getString(6),
						rset.getString(7), rset.getString(8), rset.getDate(9)));
			}
			stmt.close();
			return results;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Test results query failed");
			return null;
		}
	}

	public Vector<PatientIdentifier> patientQuery(String patientNameOrId) {
		try {

			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rset;

			try {
				Integer.parseInt(patientNameOrId);
				rset = stmt
						.executeQuery("select health_care_no, name,address,birth_day,phone from "
								+ tablePrex + "patient where health_care_no ='" + patientNameOrId
								+ "'");
			} catch (NumberFormatException nFE) {
				rset = stmt
						.executeQuery("select health_care_no, name,address,birth_day,phone from "
								+ tablePrex + "patient where name like '%" + patientNameOrId + "%'");
			}

			Vector<PatientIdentifier> results = new Vector<PatientIdentifier>();

			while (rset.next()) {
				results.addElement(new PatientIdentifier(rset.getString("health_care_no"), rset
						.getString("name"), rset.getString("address"), rset.getDate(4), rset
						.getString("phone")));
			}
			stmt.close();
			return results;
		} catch (SQLException e) {
			System.out.println("Statement could not be created, may have to set debug = false");
			return null;
		}
	}

	public Vector<SearchIdentifier> searchPane(int mode, Vector<Object> searchItems) {
		try {
			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rset;

			switch (mode) {
			/*
			 * searchItems[0] = patient name searchItems[1] = test name
			 */
			case 1: {
				String query = "SELECT p.name, t.test_name, r.test_date, r.result FROM "
						+ tablePrex
						+ "patient p, "
						+ tablePrex
						+ "test_type t, "
						+ tablePrex
						+ "test_record r WHERE "
						+ "r.type_id = t.type_id AND r.patient_no = p.health_care_no AND (t.test_name LIKE '%"
						+ searchItems.get(1) + "%' AND p.name LIKE '%" + searchItems.get(0) + "%')";

				rset = stmt.executeQuery(query);

				Vector<SearchIdentifier> results = new Vector<SearchIdentifier>();

				while (rset.next()) {
					results.addElement(new Search1Identifier(rset.getString("name"), rset
							.getString("test_name"), rset.getDate("test_date"), rset
							.getString("result")));
				}
				return results;
			}

			/*
			 * searchItems[0] = doctor name or Employee ID searchItems[1] = start date
			 * searchItems[2] = end date
			 */
			case 2: {
				java.sql.Date startDate = new java.sql.Date(
						((java.util.Date) searchItems.get(1)).getTime());
				java.sql.Date endDate = new java.sql.Date(
						((java.util.Date) searchItems.get(2)).getTime());

				String query = "SELECT p.name, t.test_name, r.prescribe_date FROM "
						+ tablePrex
						+ "patient p, "
						+ tablePrex
						+ "patient p2, "
						+ tablePrex
						+ "doctor d, "
						+ tablePrex
						+ "test_type t, "
						+ tablePrex
						+ "test_record r WHERE "
						+ "r.type_id = t.type_id AND r.patient_no = p.health_care_no AND d.employee_no = r.employee_no AND d.health_care_no = p2.health_care_no "
						+ "AND (p2.name LIKE '%" + searchItems.get(0)
						+ "%' OR d.employee_no LIKE '%" + searchItems.get(0).toString()
						+ "%') AND r.prescribe_date >= to_date('" + startDate.toString()
						+ "', 'YYYY-MM-DD') " + "AND r.prescribe_date <= to_date('"
						+ endDate.toString() + "', 'YYYY-MM-DD')";

				rset = stmt.executeQuery(query);

				Vector<SearchIdentifier> results = new Vector<SearchIdentifier>();

				while (rset.next()) {
					results.addElement(new Search2Identifier(rset.getString("name"), rset
							.getString("test_name"), rset.getDate("prescribe_date")));
				}
				return results;
			}

			/*
			 * searchItems[0] = test name
			 */
			case 3: {
				try {
					rset = stmt.executeQuery("DROP VIEW medical_risk");
				} catch (Exception f) {
					// Continue;
				}

				String query = "CREATE VIEW medical_risk (medical_type,\"abnormal rate\", \"alarming age\") AS "
						+ "SELECT t.type,  r.ab_rate, min(t.age) "
						+ "FROM  (SELECT  t1.type_id type,count(distinct t2.patient_no)/count(distinct t1.patient_no) ab_rate "
						+ "FROM     "
						+ tablePrex
						+ "test_record t1, "
						+ tablePrex
						+ "test_record t2 "
						+ "WHERE    t1.type_id = t2.type_id AND t2.result <> 'normal' AND t1.result IS NOT NULL "
						+ "GROUP BY t1.type_id "
						+ ") r,"
						+ "(SELECT t1.type_id type, p.age, "
						+ "COUNT(DISTINCT t2.patient_no) / COUNT(DISTINCT t3.patient_no) n_ab_rate "
						+ "FROM   ( SELECT  DISTINCT trunc(months_between(sysdate,birth_day)/12) age "
						+ "FROM "
						+ tablePrex
						+ "patient "
						+ ") p, "
						+ ""
						+ tablePrex
						+ "test_record t1, "
						+ tablePrex
						+ "test_record t2, "
						+ tablePrex
						+ "patient p2, "
						+ ""
						+ tablePrex
						+ "test_record t3, "
						+ tablePrex
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
						+ tablePrex
						+ "patient p, "
						+ "medical_risk m "
						+ "WHERE  trunc(months_between(sysdate,birth_day)/12) >= m.\"alarming age\" "
						+ "AND m.medical_type LIKE '%" + searchItems.get(0) + "%' "
						+ "AND p.health_care_no NOT IN (SELECT patient_no " + "FROM   " + tablePrex
						+ "test_record t " + "WHERE  m.medical_type = t.type_id" + ")";

				rset = stmt.executeQuery(query2);

				Vector<SearchIdentifier> results = new Vector<SearchIdentifier>();

				while (rset.next()) {
					results.addElement(new Search3Identifier(rset.getString("name"), rset
							.getString("address"), rset.getString("phone")));
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

	public boolean canConduct(String Lab, String Test) {

		try {
			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rset = stmt.executeQuery("select * from " + tablePrex
					+ "can_conduct where lab_name ='" + Lab + "' and type_id = '" + Test + "'");

			boolean RowsExist = rset.last();

			return RowsExist;
		} catch (Exception e) {
			System.out.println("Exeception @ CanConduct ");
			e.printStackTrace();
			return false;
		}

	}

	public boolean patientUnique(String Health_care_no) {
		try {
			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rset = stmt.executeQuery("select * from " + tablePrex
					+ "patient where health_care_no =" + Health_care_no);
			return !rset.last();
		} catch (Exception e) {
			System.out.println("Exeception @ UniquePatient test ");
			e.printStackTrace();
			return false;
		}
	}

	public Vector<String> getTests() {
		try {
			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rset = stmt.executeQuery("select test_name from " + tablePrex + "test_type");

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
