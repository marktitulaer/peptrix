package pharmaceuticals.nl.peptrix.gui;

import java.sql.*;
import javax.swing.table.*;

import pharmaceuticals.nl.peptrix.JDBC;

public class ResultSetTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private ResultSet resultSet;

	private ResultSetMetaData metaData;

	private int numberOfRows;

	// public ResultSetTableModel(JDBC jdbcconnection, String query) throws
	// SQLException {
	// jdbcconnection.selectdata(query);
	// this.resultSet = jdbcconnection.rs;
	// this.metaData = jdbcconnection.metaData;
	// this.numberOfRows = jdbcconnection.rowCnt;
	// fireTableStructureChanged();
	// }

	public ResultSetTableModel(JDBC jdbcconnection) throws SQLException {
		// jdbcconnection.selectdata(query);
		this.resultSet = jdbcconnection.rs;
		this.metaData = jdbcconnection.metaData;
		this.numberOfRows = jdbcconnection.rowCnt;
		fireTableStructureChanged();
	}

	public void displayresults(JDBC jdbcconnection) {
		// jdbcconnection.selectdata(query);
		this.resultSet = jdbcconnection.rs;
		this.metaData = jdbcconnection.metaData;
		this.numberOfRows = jdbcconnection.rowCnt;
		fireTableStructureChanged();
	}

	@Override
	public Class<?> getColumnClass(int column) throws IllegalStateException {
		try {
			String className = metaData.getColumnClassName(column + 1);
			if (className.equals("java.sql.Time")) {
				className = "java.lang.String";
			}
			return Class.forName(className);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return Object.class;
	}

	// @Override
	public int getColumnCount() throws IllegalStateException {
		try {
			return metaData.getColumnCount();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return 0;
	}

	@Override
	public String getColumnName(int column) throws IllegalStateException {
		try {
			return metaData.getColumnName(column + 1);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return "";
	}

	// @Override
	public int getRowCount() throws IllegalStateException {
		return numberOfRows;
	}

	// @Override
	public Object getValueAt(int row, int column) throws IllegalStateException {
		try {
			resultSet.absolute(row + 1);
			return resultSet.getObject(column + 1);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return "";
	}
}
