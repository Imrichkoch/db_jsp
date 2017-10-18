package info;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConn {
	private static final String DB_URL = "jdbc:postgresql://localhost:5432/person";
	private static final String JDBC_DRIVER = "org.postgresql.Driver";
	private static final String USER = "postgres";
	private static final String PWD = "imro";
	private final static String person = "person";
	private final static String schema = "Person";
	static Connection conn = null;
	static Statement stmt = null;
	static DatabaseMetaData databaseDate = null;
	static ResultSet databaseResults = null;

	public static void main(String[] args) {

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PWD);
			stmt = conn.createStatement();

			if (findTable(databaseResults, databaseDate)) {
				String sql = "CREATE TABLE person.person (name varchar, email varchar, age numeric)";
				stmt.executeUpdate(sql);
			}

		} catch (ClassNotFoundException e) {
			System.err.println("Trieda PostgreSQL Drivera nebola najdena.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("Nastala chyba pri pripajani k databaze.");
			e.printStackTrace();
		}

		// uzatvorenie spojenia s DB
		try {
			conn.close();
		} catch (SQLException e) {
			System.err.println("Pri zatvarani spojenia s DB prislo k chybe.");
			e.printStackTrace();
		}

		System.out.println("Program prebehol v poriadku.");
	}

	public static boolean findTable(ResultSet rs, DatabaseMetaData dbm) throws SQLException {
		boolean find = false;

		rs = dbm.getTables(null, schema, person, null);
		if (rs.next()) {
			find = true;
		}
		return find;

	}

	public void updateDB(Person person) {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PWD);

			stmt = conn.createStatement();

			String sql = "INSERT INTO person.person (name, email, age) VALUES ('" + person.getName() + "','"
					+ person.getEmail() + "'," + person.getAge() + ")";
			stmt.executeUpdate(sql);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
