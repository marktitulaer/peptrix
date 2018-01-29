package pharmaceuticals.nl.peptrix.database;

import java.sql.*;

import java.util.*;

import javax.swing.JOptionPane;

import pharmaceuticals.nl.peptrix.Controller;

public class JDBC {

	public Connection con = null;

	Statement stmt;

	public ResultSet rs;

	ResultSet rs2;

	Properties properties;

	public ResultSetMetaData metaData;

	String[][] odata;

	String[] clmHeaders;

	int[] batchrows;

	String drivertype;

	int clmCnt;

	public int rowCnt;

	boolean debugmode = true;

	public JDBC(Controller cc) {
		properties = new Properties();
		properties.put("user", cc.jdbc_user);
		properties.put("password", cc.jdbc_password);
		createcon();
	}

	public void resetconnection() {
		try {
			con.close();
		} catch (Exception e) {
			;
		}
		try {
			createcon();
		} catch (Exception e) {
			;
		}
	}

	public void createcon() {
		try {
			drivertype = "com.mysql.jdbc.Driver";
			Driver driver = (Driver) Class.forName(drivertype).newInstance();
			con = driver.connect("jdbc:mysql:///proteomics", properties);
		} catch (Exception e) {
			if (debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void selectdata(String query) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (con.isClosed()) {
				System.out.println("con.isClosed");
				createcon();
			}
			con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(query);
			metaData = rs.getMetaData();
			rs.last();
			rowCnt = rs.getRow();
		} catch (Exception e) {
			if (debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public int update_table(String strquery) throws SQLException {
		con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		stmt = con.createStatement();
		int rows = -1;
		rows = stmt.executeUpdate(strquery);
		return rows;
	}

	public String[][] returnData(String strquery) throws SQLException {
		if (rs2 != null) {
			rs2.close();
		}
		int clmCnt = -1;
		con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		stmt = con.createStatement();
		rs2 = stmt.executeQuery(strquery);
		clmCnt = rs2.getMetaData().getColumnCount();
		rs2.last();
		rowCnt = rs2.getRow();
		odata = new String[rowCnt][clmCnt];
		int row = 0;
		rs2.first();
		try {
			if (rowCnt > 0) {
				for (int i = 1; i <= clmCnt; i++) {
					odata[row][i - 1] = rs2.getString(i);
				}
			}
		} catch (Exception e) {
			if (debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, (this.getClass().getName() + "  " + e.getMessage()), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		row++;
		while (rs2.next()) {
			for (int i = 1; i <= clmCnt; i++) {
				odata[row][i - 1] = rs2.getString(i);
			}
			row++;
		}
		return odata;
	}

	public String[] returnHeaders() throws SQLException {
		clmCnt = rs2.getMetaData().getColumnCount();
		clmHeaders = new String[clmCnt];
		for (int i = 1; i <= clmCnt; i++) {
			clmHeaders[i - 1] = rs2.getMetaData().getColumnName(i);
		}
		return clmHeaders;
	}

	// BATCH PROCESS
	public void init_batch() throws SQLException {
		con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		stmt = con.createStatement();
	}

	public void add_update_batch(String strquery) throws SQLException {
		stmt.addBatch(strquery);
	}

	public void execute_batch() throws SQLException {
		batchrows = stmt.executeBatch();
	}
	// END BATCH PROCESS

}
